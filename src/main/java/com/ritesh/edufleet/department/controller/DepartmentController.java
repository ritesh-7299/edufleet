package com.ritesh.edufleet.department.controller;

import com.opencsv.exceptions.CsvValidationException;
import com.ritesh.edufleet.department.dto.CreateDepartmentDto;
import com.ritesh.edufleet.department.response.DepartmentListingResponse;
import com.ritesh.edufleet.department.response.DepartmentStudentsListingsResponse;
import com.ritesh.edufleet.department.service.DepartmentService;
import com.ritesh.edufleet.exception.BadRequestException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping("/students/{name}")
    public List<DepartmentStudentsListingsResponse> getAllStudentsByDepartment(@PathVariable String name) {
        return departmentService.getAllStudentsByDepartment(name);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping("/upload-csv")
    public String uploadCsv(@RequestParam("file") MultipartFile file) throws IOException, CsvValidationException {
        String contentType = file.getContentType();
        if (!"text/csv".equals(contentType) && !"application/vnd.ms-excel".equals(contentType)) {
            throw new BadRequestException("Invalid file type. Only CSV files are allowed.");
        }

        return departmentService.uploadCsv(file.getInputStream());
    }
}
