package com.hrms.usermanagement.service;

import com.hrms.employeemanagement.models.Role;
import com.hrms.employeemanagement.models.User;
import com.hrms.usermanagement.dto.SignupDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.repository.RoleRepository;
import com.hrms.usermanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

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
                    mapper.map(src -> src.getRole(), UserDto::setRole);
                    mapper.map(src -> src.getEmployee().getFirstName(), UserDto::setName);
                    mapper.map(User::getIsEnabled, UserDto::setStatus);
                });
    }

    public Page<UserDto> getAll(PageRequest pageRequest) {
        return userRepository.findAll(pageRequest).map(u -> modelMapper.map(u, UserDto.class));
    }

    public Page<UserDto> getAllByFilter(List<Integer> roles, List<Boolean> status, Pageable pageable) {
        Specification<User> rolesFilter = Specification.where(null);
        Specification<User> statusFilter = Specification.where(null);
        if (roles != null) {
            for (Integer role : roles) {
                rolesFilter = rolesFilter.or((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("role").get("roleId"), role));
            }
        }
        if (status != null) {
            for (Boolean s : status) {
                statusFilter = statusFilter.or((root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isEnabled"), s));
            }
        }
        return userRepository
                .findAll(rolesFilter.and(statusFilter), pageable)
                .map(u -> modelMapper.map(u, UserDto.class));
    }

    public UserDto getUser(String username) {
        return modelMapper.map(userRepository.findByUsername(username), UserDto.class);
    }

    public UserDto createUser(SignupDto signupDto) {
        var user = new User();
        user.setUsername(signupDto.getUsername());
        user.setPassword(passwordEncoder.encode(signupDto.getPassword()));
        user.setIsEnabled(false);
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public UserDto updateUser(Integer id, Boolean status, String role) {
        var user = userRepository.findById(Long.valueOf(id)).orElseThrow();
        user.setIsEnabled(status);
        user.setRole(roleRepository.findRoleByName(role));
        userRepository.save(user);
        return modelMapper.map(user, UserDto.class);
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
