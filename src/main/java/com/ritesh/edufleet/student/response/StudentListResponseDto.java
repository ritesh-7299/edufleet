package com.ritesh.edufleet.student.response;

import com.ritesh.edufleet.student.entity.Student;
import lombok.Getter;

@Getter
public class StudentListResponseDto {
    private long id;
    private String name;

    public StudentListResponseDto(Student student) {
        id = student.getId();
        name = student.getName();
    }
}
