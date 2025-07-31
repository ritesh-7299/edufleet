package com.ritesh.edufleet.auth.service;

import com.ritesh.edufleet.auth.dto.LoginDto;
import com.ritesh.edufleet.auth.dto.SignupRequest;
import com.ritesh.edufleet.auth.entity.User;
import com.ritesh.edufleet.auth.repository.UserRepository;
import com.ritesh.edufleet.exception.BadRequestException;
import com.ritesh.edufleet.role.dto.UserListResponseDto;
import com.ritesh.edufleet.role.entity.Role;
import com.ritesh.edufleet.role.service.RoleService;
import com.ritesh.edufleet.student.dto.CreateStudentDto;
import com.ritesh.edufleet.system.JwtUtils;
import com.ritesh.edufleet.system.PaginationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RoleService roleService;


    /**
     * Function to create a user account in system
     */
    public String signup(SignupRequest signupRequest) {
        Role role = roleService.getRoleById((signupRequest.getRoleId()));
        if (role == null) {
            throw new BadRequestException("Invalid role_id");
        }

        Optional<User> userByUsernameAndEmail = getUserByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail());
        if (userByUsernameAndEmail.isPresent()) {
            throw new BadRequestException("User already exist");
        }

        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setRole(role);
        userRepository.save(user);
        return jwtUtils.generateToken(user.getEmail());
    }

    /**
     * Function to log in a user
     */
    public String login(LoginDto loginDto) {
        try {
            Authentication auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
            );

            if (auth.isAuthenticated()) {
                return jwtUtils.generateToken(loginDto.getEmail());
            } else {
                throw new BadRequestException("Invalid Credentials");
            }
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid Credentials");
        }
    }

    /**
     * Function to get the existing user by username or email
     */
    private Optional<User> getUserByUsernameOrEmail(String username, String email) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            user = userRepository.findByEmail(email);
        }
        return user;
    }

    /**
     * Function to get users listing pagination wise
     */
    public Page<UserListResponseDto> getUsers(PaginationDto paginationDto) {
        Pageable pageable = PageRequest.of(paginationDto.getPage(), paginationDto.getSize());

        return userRepository.findAll(pageable).map(UserListResponseDto::new);
    }

    /**
     * Function to create a new student login in the system
     *
     * @return boolean
     */
    public User createStudent(CreateStudentDto createStudentDto, String password) {
        Optional<User> userByUsernameOrEmail = getUserByUsernameOrEmail(
                createStudentDto.getName(),
                createStudentDto.getEmail()
        );

        if (userByUsernameOrEmail.isPresent()) {
            throw new BadRequestException("User already exist");
        }

        User user = new User();
        user.setUsername(createStudentDto.getName());
        user.setEmail(createStudentDto.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        Role role = roleService.getRoleByName("student");
        if (role == null) {
            throw new BadRequestException("No role found");
        }
        user.setRole(role);
        userRepository.save(user);
        return user;
    }
}
