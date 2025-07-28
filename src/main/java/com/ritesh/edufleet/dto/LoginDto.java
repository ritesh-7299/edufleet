package com.ritesh.edufleet.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginDto {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
