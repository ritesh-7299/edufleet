package com.ritesh.edufleet.department.controller;

import com.ritesh.edufleet.department.dto.CreateDepartmentDto;
import com.ritesh.edufleet.department.response.DepartmentListingResponse;
import com.ritesh.edufleet.department.service.DepartmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/department")
public class DepartmentController {
    private final DepartmentService departmentService;

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping()
    public String create(@Valid @RequestBody CreateDepartmentDto dto) {
        return departmentService.create(dto);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping
    public List<DepartmentListingResponse> findAll() {
        return departmentService.findAll();
    }
}
