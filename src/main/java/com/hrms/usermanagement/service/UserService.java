package com.hrms.usermanagement.service;

import com.hrms.employeemanagement.models.Employee;
import com.hrms.employeemanagement.models.Role;
import com.hrms.employeemanagement.models.User;
import com.hrms.employeemanagement.models.UserRole;
import com.hrms.employeemanagement.repositories.EmployeeRepository;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.exception.UserExistException;
import com.hrms.usermanagement.exception.UserNotFoundException;
import com.hrms.usermanagement.repository.RoleRepository;
import com.hrms.usermanagement.repository.UserRepository;
import com.hrms.usermanagement.repository.UserRoleRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    private ModelMapper modelMapper;

    @Bean
    public void setupMapper() {
        modelMapper = new ModelMapper();
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> {
                    mapper.map(User::getUserId, UserDto::setUserId);
                    mapper.map(User::getUsername, UserDto::setUsername);
                    mapper.map(User::getIsEnabled, UserDto::setStatus);
                });
    }

    public Page<UserDto> getAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).map(u -> modelMapper.map(u, UserDto.class));
    }

    public Page<UserDto> getAllByFilter(String search, List<Integer> roles, Boolean status, Pageable pageable) {
        Specification<User> statusFilter = Specification.where(null);
        Specification<User> searchFilter = Specification.where(null);

        if (status != null) {
            statusFilter = statusFilter.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isEnabled"), status));
        }

        if (search != null) {
            searchFilter = searchFilter.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + search + "%"));
        }

        var filteredUsers = userRepository
                .findAll(Specification.where(statusFilter).and(searchFilter))
                .stream().distinct()
                .map(u -> {
                    var userDto = modelMapper.map(u, UserDto.class);
                    var rolesList = userRoleRepository.findAllByUserUserId(u.getUserId()).stream().map(r -> r.getRole()).toList();
                    userDto.setRoles(Set.copyOf(rolesList));
                    return userDto;
                })
                .filter( userDto -> roles
                        .stream()
                        .anyMatch(roleId -> userDto.getRoles().stream().anyMatch(role -> role.getRoleId().equals(roleId)))
                ).toList();

        return new PageImpl<>(List.copyOf(filteredUsers), pageable, filteredUsers.size());
    }

    public UserDto getUser(Integer id) {
        var user = userRepository.findById(id.longValue()).orElseThrow();
        var roles = userRoleRepository.findAllByUserUserId(id).stream().map(r -> r.getRole()).toList();
        UserDto userDto = modelMapper.map(user, UserDto.class);
        userDto.setRoles(Set.copyOf(roles));
        return userDto;
    }

    public Boolean createUser(SignupDto signupDto) throws UserExistException {
        if (userRepository.findByUsername(signupDto.getUsername()) != null) {
            throw new UserExistException("User already exists");
        }
        var user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setIsEnabled(false);
        user.setCreatedAt(Date.valueOf(LocalDate.now()));

        var employee = new Employee();
        employee.setUser(user);
        user.setEmployee(employee);

        employeeRepository.save(employee);
        userRepository.save(user);
        return true;
    }

    @Transactional
    public Boolean updateUsers(List<Integer> userIds, Boolean status, List<Integer> roleIds) {
        Specification<UserRole> deleteNotInRoles = Specification.where(null);
        deleteNotInRoles = deleteNotInRoles.and((root, query, criteriaBuilder) ->
                root.get("user").get("userId").in(userIds)
        );
        deleteNotInRoles = deleteNotInRoles.and((root, query, criteriaBuilder) ->
                criteriaBuilder.not(root.get("role").get("roleId").in(roleIds))
        );
        userRoleRepository.delete(deleteNotInRoles);

        for (var userId : userIds) {
            for (var roleId : roleIds) {
                Specification<UserRole> userRoleSpecification = Specification.where(null);
                userRoleSpecification = userRoleSpecification.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("user").get("userId"), userId)
                );
                userRoleSpecification = userRoleSpecification.and((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("role").get("roleId"), roleId)
                );
                var userRole = userRoleRepository.findOne(userRoleSpecification).orElse(null);
                if (userRole == null) {
                    userRoleRepository.addRoleIdUserId(userId, roleId);
                }
            }
            userRepository.updateStatus(userId, status);
        }
        return true;
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    public Boolean updateUsernamePassword(Integer userId, String username, String password) throws UserNotFoundException {
        var user = userRepository.findById(Long.valueOf(userId)).orElseThrow();
        if (user == null) {
            throw new UserNotFoundException("User not found");
        }
        if (password == null) {
            throw new IllegalArgumentException("Password cannot be null");
        }
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);
        return true;
    }
}
