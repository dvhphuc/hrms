package com.hrms.global.mapper;

import com.hrms.usermanagement.dto.UserDto;
import com.hrms.usermanagement.model.User;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class HrmsMapper extends ModelMapper {
    @Bean
    void setup() {
        this.typeMap(User.class, UserDto.class).addMappings(
                mapper -> {
                    mapper.map(User::getUserId, UserDto::setUserId);
                    mapper.map(User::getUsername, UserDto::setUserName);
                    mapper.map(User::getIsEnabled, UserDto::setStatus);
                    mapper.map(User::getCreatedAt, UserDto::setCreatedAt);
                }
        );
    }
}