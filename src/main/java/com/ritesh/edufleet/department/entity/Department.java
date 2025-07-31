package com.ritesh.edufleet.department.entity;

import com.ritesh.edufleet.model.BaseEntity;
import com.ritesh.edufleet.student.entity.Student;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
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
    private String name;

    @OneToMany(mappedBy = "departmentId",
            fetch = FetchType.LAZY,
            orphanRemoval = true
    )
    private List<Student> students;
}
