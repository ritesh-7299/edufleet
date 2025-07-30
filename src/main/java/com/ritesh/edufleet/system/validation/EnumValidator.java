package com.ritesh.edufleet.system.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Arrays;

public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private Enum<?>[] enumValues;

    @Override
    public void initialize(EnumValid annotation) {
        enumValues = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;
        return Arrays.stream(enumValues)
                .anyMatch(e -> e.name().equalsIgnoreCase(value.trim()));
    }
}
