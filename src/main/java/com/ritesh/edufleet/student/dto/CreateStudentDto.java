package com.ritesh.edufleet.student.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateStudentDto {
    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;
}
