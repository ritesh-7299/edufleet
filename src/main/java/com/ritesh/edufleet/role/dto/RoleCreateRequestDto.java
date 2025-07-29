package com.ritesh.edufleet.role.dto;

import com.ritesh.edufleet.enums.UserRoleEnum;
import com.ritesh.edufleet.system.validation.EnumValid;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RoleCreateRequestDto {
    @NotBlank
    @EnumValid(enumClass = UserRoleEnum.class, message = "Invalid role. Allowed values: STUDENT,TEACHER,STAFF,ADMIN")
    private String name;
}
