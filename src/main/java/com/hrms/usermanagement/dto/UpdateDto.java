package com.hrms.usermanagement.dto;

import lombok.Data;

@Data
public class UpdateDto {
    String roleName;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Boolean getEnable() {
        return isEnable;
    }

    public void setEnable(Boolean enable) {
        isEnable = enable;
    }

    Boolean isEnable;
}
