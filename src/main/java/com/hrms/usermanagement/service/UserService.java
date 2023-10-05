package com.hrms.usermanagement.service;

import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.RoleRepository;
import com.hrms.usermanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private ModelMapper modelMapper;

    @Bean
    public void setupMapper() {
        modelMapper = new ModelMapper();
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> {
                    mapper.map(src -> src.getRole().getRoleId(), UserDto::setRole);
                    mapper.map(src -> src.getEmployee().getFullname(), UserDto::setName);
                    mapper.map(User::getIsEnabled, UserDto::setStatus);
                });
    }

    public List<UserDto> getAll() {
        var sort = Sort.by(Sort.Direction.DESC, "createdAt");
        var users = userRepository.findAll(sort);

        return users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public Page<UserDto> getAll(Pageable pageable) {
        Pageable sortedByCreatedAtDesc = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("createdAt").descending());
        return userRepository.findAll(sortedByCreatedAtDesc).map(u -> modelMapper.map(u, UserDto.class));

    }

    public Page<UserDto> getAllByFilter(Specification spec, Pageable pageable) {
        Pageable sortedByCreatedAtDesc = PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        Sort.by("createdAt").descending());
        return userRepository.findAll(spec, sortedByCreatedAtDesc).map(u -> modelMapper.map(u, UserDto.class));

    }


    public void updateUser(String username, String roleName, boolean isEnable) {
        var user = userRepository.findByUsername(username);
        var role = roleRepository.findRoleByName(roleName.toUpperCase());
        user.setIsEnabled(isEnable);
        user.setRole(role);
        userRepository.save(user);
    }

    public Page<UserDto> getUserByStatus(@Param("status") Boolean status,
                                         PageRequest pageRequest)
    {
        return userRepository.findAllByIsEnabled(status, pageRequest)
                .map(user -> modelMapper.map(user, UserDto.class));
    }

    public Page<UserDto> filter(Specification<User> spec, Pageable pageable) {
        return userRepository.findAll(spec, pageable)
                .map(user -> modelMapper.map(user, UserDto.class));
    }

    public Page<UserDto> getUsersByRoleNameAndStatus(@Param("roleName") String roleName,
                                                     @Param("status") Boolean status,
                                                     PageRequest pageRequest)
    {
        return userRepository.findAllByRoleNameAndIsEnabled(roleName, status, pageRequest)
                .map(user -> modelMapper.map(user, UserDto.class));
    }
}
