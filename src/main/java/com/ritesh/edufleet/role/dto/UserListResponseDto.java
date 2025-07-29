package com.ritesh.edufleet.role.dto;

import com.ritesh.edufleet.auth.entity.User;
import lombok.Getter;

@Getter
public class UserListResponseDto {
    private String username;
    private String email;
    private String role;

    public UserListResponseDto(User user) {
        username = user.getUsername();
        email = user.getEmail();
        role = user.getRole().getName().toString();
    }

}
