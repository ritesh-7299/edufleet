package com.ritesh.edufleet.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 10)
    private String username;

    @Email
    private String email;

    @NotBlank
    @Size(min = 6, max = 10)
    private String password;

    @NotNull
    private long roleId;
}
