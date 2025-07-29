package com.ritesh.edufleet.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("role_id")
    private long roleId;
}
