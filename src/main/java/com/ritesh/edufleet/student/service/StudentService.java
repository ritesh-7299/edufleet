package com.ritesh.edufleet.student.service;

import com.ritesh.edufleet.auth.entity.User;
import com.ritesh.edufleet.auth.service.AuthService;
import com.ritesh.edufleet.department.entity.Department;
import com.ritesh.edufleet.department.service.DepartmentService;
import com.ritesh.edufleet.exception.ResourceNotFoundException;
import com.ritesh.edufleet.student.dto.CreateStudentDto;
import com.ritesh.edufleet.student.entity.Student;
import com.ritesh.edufleet.student.repository.StudentRepository;
import com.ritesh.edufleet.student.response.StudentListResponseDto;
import com.ritesh.edufleet.system.DateParser;
import com.ritesh.edufleet.system.PaginationDto;
import com.ritesh.edufleet.system.PasswordGenerator;
import com.ritesh.edufleet.system.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentService {
    private final StudentRepository studentRepository;
    private final AuthService authService;
    private final EmailService emailService;
    private final PasswordGenerator passwordGenerator;
    private final DepartmentService departmentService;
    private final DateParser dateParser;

    /**
     * Function to create a student into database
     *
     * @param createStudentDto
     */
    @Transactional
    public String create(CreateStudentDto createStudentDto) {
        Date birthDate = dateParser.parseDate(createStudentDto.getBirthDate());
        Department department = departmentService.getDepartmentById(createStudentDto.getDepartmentId()).orElseThrow(
                () -> new ResourceNotFoundException("Department not found")
        );
        Student student = new Student();
        student.setName(createStudentDto.getName());
        student.setBirthDate(birthDate);
        student.setFirstName(createStudentDto.getFirstName());
        student.setLastName(createStudentDto.getLastName());
        student.setDepartmentId(department);
        String password = passwordGenerator.generatePassword(8);
        User user = authService.createStudent(createStudentDto, password);
        if (user != null) {
            student.setUserId(user);
            studentRepository.save(student);
            log.info("Student login created");

            emailService.sendSimpleMail(
                    createStudentDto.getEmail(),
                    "Enrolled in edufleet system",
                    "Congratulations you account has been created and now you can login to edufleet.\n" +
                            "your credentials are as follow:\n" +
                            "email: " + createStudentDto.getEmail() + "\n" +
                            "Password: " + password
            );
        }

        return "Student created";
    }

    /**
     * Function to get all students page wise
     *
     * @param paginationDto
     * @return
     */
    public Page<StudentListResponseDto> getAll(PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSize());
        return studentRepository.findAll(pageable).map(StudentListResponseDto::new);
    }

    /**
     * Function to delete a student
     *
     * @param id
     * @return
     */
    public String delete(long id) {
        Student s = studentRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Student not found")
        );
        studentRepository.delete(s);
        return "Student deleted";
    }
}
