package com.hrms.usermanagement.service;

import com.hrms.usermanagement.dto.UpdateDto;
import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.Role;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.RoleRepository;
import com.hrms.usermanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.DestinationSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
        var sort = Sort.by(Sort.Direction.ASC, "createdAt");
        var users = userRepository.findAll(sort);

        return users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public List<UserDto> getAll(int pageNo, int pageSize) {
        var paging = PageRequest.of(pageNo, pageSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        var users = userRepository.findAll(paging);

        return users.stream()
                .map(u -> modelMapper.map(u, UserDto.class))
                .collect(Collectors.toList());
    }

    public void updateUser(String username, String roleName, boolean isEnable) {
        var user = userRepository.findByUsername(username);
        var role = roleRepository.findRoleByName(roleName.toUpperCase());
        user.setIsEnabled(isEnable);
        user.setRole(role);
        userRepository.save(user);
    }
}
