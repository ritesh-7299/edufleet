package com.ritesh.edufleet.system;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaginationDto {
    @NotNull
    private int page = 0;

    @NotNull
    private int size = 10;
}
