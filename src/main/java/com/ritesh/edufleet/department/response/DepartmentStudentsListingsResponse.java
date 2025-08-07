package com.ritesh.edufleet.department.response;

import com.ritesh.edufleet.department.entity.Department;
import com.ritesh.edufleet.student.entity.Student;
import lombok.Getter;

@Getter
public class DepartmentStudentsListingsResponse {
    private long id;
    private String departmentName;
    private String studentName;

    public DepartmentStudentsListingsResponse(Department department, Student student) {
        id = student.getId();
        departmentName = department.getName();
        studentName = student.getName();
    }
}
