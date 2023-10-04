package com.hrms.usermanagement.service;

import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.RoleRepository;
import com.hrms.usermanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.*;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
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
                    mapper.map(src -> src.getRole().getName(), UserDto::setRole);
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

    public Page<UserDto> getFilterOfRole(@Param("roleName") String roleName, Pageable pageable) {
        var sort = Sort.by(Sort.Direction.DESC, "createdAt");
        var userDtos = userRepository.findAll(PageRequest.of
                        (pageable.getPageNumber(), pageable.getPageSize(), sort))
                .map(u -> modelMapper.map(u, UserDto.class));
        return new PageImpl<>(userDtos.stream()
                .filter(u -> u.getRole().equals(roleName))
                .collect(Collectors.toList()));
    }

    public Page<UserDto> getFilterOfStatus(@Param("status") Boolean status, Pageable pageable) {
        var sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return userRepository.findAllByIsEnabled(status, PageRequest.of
                        (pageable.getPageNumber(), pageable.getPageSize(), sort))
                .map(u -> modelMapper.map(u, UserDto.class));
    }

    public Page<UserDto> getFilterOfRoleAndStatus(@Param("roleName") String role,
                                                  @Param("status") Boolean status,
                                                  Pageable pageable)
    {
        var sort = Sort.by(Sort.Direction.DESC, "createdAt");
        return userRepository.findAllByRoleAndIsEnabled(role, status, PageRequest.of
                        (pageable.getPageNumber(), pageable.getPageSize(), sort))
                .map(u -> modelMapper.map(u, UserDto.class));
    }


    public Page<UserDto> getAll(@Param("roleName") String roleName, @Param("status") Boolean status, Pageable pageable) {
        if (roleName == null && status == null) {
            return getAll(pageable);
        }
        if (roleName == null) {
            return getFilterOfStatus(status, pageable);
        }
        if (status == null) {
            return getFilterOfRole(roleName, pageable);
        }
        return getFilterOfRoleAndStatus(roleName, status, pageable);
    }

    public void updateUser(String username, String roleName, boolean isEnable) {
        var user = userRepository.findByUsername(username);
        var role = roleRepository.findRoleByName(roleName.toUpperCase());
        user.setIsEnabled(isEnable);
        user.setRole(role);
        userRepository.save(user);
    }
}
