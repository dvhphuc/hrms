package com.hrms.usermanagement.dto;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@Data
@Getter
@Setter
@NoArgsConstructor
public class UserDto {
    public UserDto(Integer userId, String userName) {
        this.userId = userId;
        this.userName = userName;

    }
    private Integer userId;
    private String userName;
    private Boolean status;
    private Date createdAt;
}