package com.hrms.usermanagement.service;

import com.hrms.employeemanagement.models.Role;
import com.hrms.employeemanagement.models.User;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.exception.UserExistException;
import com.hrms.usermanagement.exception.UserNotFoundException;
import com.hrms.usermanagement.repository.RoleRepository;
import com.hrms.usermanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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
                    mapper.map(User::getRoles, UserDto::setRoles);
                    mapper.map(src -> src.getEmployee().getFirstName(), UserDto::setName);
                    mapper.map(User::getIsEnabled, UserDto::setStatus);
                });
    }

    public Page<UserDto> getAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).map(u -> modelMapper.map(u, UserDto.class));
    }

    public Page<UserDto> getAllByFilter(String search, List<Integer> roles, Boolean status, Pageable pageable) {
        Specification<User> rolesFilter = Specification.where(null);
        Specification<User> statusFilter = Specification.where(null);
        Specification<User> searchFilter = Specification.where(null);
        if (roles != null) {
            for (Integer role : roles) {
                rolesFilter = rolesFilter.or((root, query, criteriaBuilder) ->
                        criteriaBuilder.equal(root.get("roles").get("roleId"), role));
            }
        }

        if (status != null) {
            statusFilter = statusFilter.and((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isEnabled"), status));
        }

        if (search != null) {
            searchFilter = searchFilter.and((root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("username"), "%" + search + "%"));
        }

        var filteredUsers = userRepository
                .findAll(Specification.where(rolesFilter).and(statusFilter).and(searchFilter))
                .stream().distinct().map(u -> modelMapper.map(u, UserDto.class)).toList();
        return new PageImpl<>(List.copyOf(filteredUsers), pageable, filteredUsers.size());
    }

    public UserDto getUser(Integer id) {
        var user = userRepository.findById(Long.valueOf(id)).orElseThrow();
        return modelMapper.map(user, UserDto.class);
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
        userRepository.save(user);
        return true;
    }

    public Boolean updateUsers(List<Integer> ids, Boolean status, List<Integer> roles) {
        ids.stream().forEach(id -> {
            var user = userRepository.findById(Long.valueOf(id)).orElseThrow();
            if (status != null) {
                user.setIsEnabled(status);
            }
            if (roles != null) {
                roles.forEach(roleId -> {
                    var role = roleRepository.findById(roleId).orElseThrow();
                    user.addRole(role);
                });
                roleRepository.findAll().forEach(role -> {
                    if (!roles.contains(role.getRoleId())) {
                        user.removeRole(role);
                    }
                });
            }
            userRepository.save(user);
        });
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
