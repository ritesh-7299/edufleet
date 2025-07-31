package com.ritesh.edufleet.department.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateDepartmentDto {
    @NotBlank
    private String name;
}
