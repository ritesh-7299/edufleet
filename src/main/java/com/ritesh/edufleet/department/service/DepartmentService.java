package com.ritesh.edufleet.department.service;

import com.opencsv.exceptions.CsvValidationException;
import com.ritesh.edufleet.auth.entity.User;
import com.ritesh.edufleet.department.dto.CreateDepartmentDto;
import com.ritesh.edufleet.department.entity.Department;
import com.ritesh.edufleet.department.repository.DepartmentRepository;
import com.ritesh.edufleet.department.response.DepartmentListingResponse;
import com.ritesh.edufleet.department.response.DepartmentStudentsListingsResponse;
import com.ritesh.edufleet.role.entity.Role;
import com.ritesh.edufleet.student.entity.Student;
import com.ritesh.edufleet.system.service.CsvService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final EntityManager entityManager;
    private final CsvService csvService;

    public String create(CreateDepartmentDto dto) {
        Department d = new Department();
        d.setName(dto.getName());
        departmentRepository.save(d);
        return "Department created";
    }

    public List<DepartmentListingResponse> findAll() {
        return departmentRepository
                .findAll()
                .stream()
                .map(DepartmentListingResponse::new)
                .toList();
    }

    public Optional<Department> getDepartmentById(Long id) {
        return departmentRepository.findById(id);
    }


    public List<DepartmentStudentsListingsResponse> getAllStudentsByDepartment(String dName) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<DepartmentStudentsListingsResponse> query = cb.createQuery(DepartmentStudentsListingsResponse.class);

        Root<Student> student = query.from(Student.class);
        Join<Student, Department> department = student.join("departmentId");
        Join<User, Student> user = student.join("userId");
        Join<Role, User> role = user.join("role");

        List<Predicate> predicatesList = new ArrayList<>();
//        predicatesList.add(cb.equal(cb.lower(department.get("name")), dName.toLowerCase()));
        predicatesList.add(cb.equal(cb.lower(role.get("name")), dName.toLowerCase()));

        query.select(cb.construct(
                DepartmentStudentsListingsResponse.class,
                department,
                student
        )).where(predicatesList.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }

    public String uploadCsv(InputStream inputStream) throws CsvValidationException, IOException {
        List<Department> departments = csvService.parseCsv(inputStream);
        departmentRepository.saveAll(departments);
        return "OK";

    }
}
