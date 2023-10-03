package com.hrms.usermanagement.service;

import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.User;
import com.hrms.usermanagement.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.DestinationSetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.ui.ModelMap;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @Bean
    public void setupMapper() {
        modelMapper.typeMap(User.class, UserDto.class)
                .addMappings(mapper -> {
                   mapper.map(src -> src.getRole().getName(), UserDto::setRole);
                   mapper.map(src -> src.getEmployee().getFullname(), UserDto::setName);
                   mapper.map(User::isEnabled, UserDto::setStatus);
                });
    }

    public List<UserDto> getAll() {
        Sort sort = Sort.by(Sort.Direction.ASC, "createdAt");
        List<UserDto> usersDto = new ArrayList<>();

        List<User> users = userRepository.findAll(sort);
        users.forEach(u -> {
            usersDto.add(modelMapper.map(u, UserDto.class));
        });
        return usersDto;
    }
}
