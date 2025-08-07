package com.ritesh.edufleet.department.entity;

import com.ritesh.edufleet.model.BaseEntity;
import com.ritesh.edufleet.student.entity.Student;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "departments")
public class Department extends BaseEntity {
    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "departmentId",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Student> students;
}
