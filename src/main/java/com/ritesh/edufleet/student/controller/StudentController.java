package com.ritesh.edufleet.student.controller;

import com.ritesh.edufleet.student.dto.CreateStudentDto;
import com.ritesh.edufleet.student.response.StudentListResponseDto;
import com.ritesh.edufleet.student.service.StudentService;
import com.ritesh.edufleet.system.PaginationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    /**
     * Api to create new student
     *
     * @param dto
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @PostMapping
    public String create(@Valid @RequestBody CreateStudentDto dto) {
        return studentService.create(dto);
    }

    /**
     * Function to get listing of students
     *
     * @param paginationDto
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @GetMapping
    public Page<StudentListResponseDto> getAll(@Valid @ModelAttribute PaginationDto paginationDto) {
        return studentService.getAll(paginationDto);
    }

    /**
     * Api to delete a student
     *
     * @param id
     * @return
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('STAFF')")
    @DeleteMapping("/{id}")
    public String delete(@PathVariable long id) {
        return studentService.delete(id);
    }

}
