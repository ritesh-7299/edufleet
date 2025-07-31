package com.ritesh.edufleet.department.service;

import com.ritesh.edufleet.department.dto.CreateDepartmentDto;
import com.ritesh.edufleet.department.entity.Department;
import com.ritesh.edufleet.department.repository.DepartmentRepository;
import com.ritesh.edufleet.department.response.DepartmentListingResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

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
}
