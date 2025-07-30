package com.ritesh.edufleet.auth.controller;

import com.ritesh.edufleet.auth.dto.LoginDto;
import com.ritesh.edufleet.auth.dto.SignupRequest;
import com.ritesh.edufleet.auth.service.AuthService;
import com.ritesh.edufleet.role.dto.UserListResponseDto;
import com.ritesh.edufleet.system.PaginationDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;


    /**
     * Api to register a new user
     *
     * @param signupRequest
     * @return
     */
    @PostMapping("/signup")
    public String signup(@Valid @RequestBody SignupRequest signupRequest) {
        return authService.signup(signupRequest);
    }

    /**
     * Api to login user
     *
     * @param loginDto
     * @return
     */
    @PostMapping("/login")
    public String login(@Valid @RequestBody LoginDto loginDto) {
        return authService.login(loginDto);
    }

    @GetMapping("/test")
    public String test() {
        return "TESTING OK";
    }

    /**
     * Api to get users listing pagination wise
     *
     * @param paginationDto
     * @return
     */
    @GetMapping("/users")
    public Page<UserListResponseDto> getUsers(@Valid @ModelAttribute PaginationDto paginationDto) {
        return authService.getUsers(paginationDto);
    }
}
