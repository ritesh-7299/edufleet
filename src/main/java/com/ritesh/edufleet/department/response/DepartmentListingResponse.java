package com.ritesh.edufleet.department.response;

import com.ritesh.edufleet.department.entity.Department;
import lombok.Getter;

@Getter
public class DepartmentListingResponse {
    private Long id;
    private String name;

    public DepartmentListingResponse(Department department) {
        id = department.getId();
        name = department.getName();
    }
}
