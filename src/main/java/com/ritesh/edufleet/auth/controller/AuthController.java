package com.ritesh.edufleet.auth.controller;

import com.ritesh.edufleet.auth.dto.LoginDto;
import com.ritesh.edufleet.auth.dto.SignupRequest;
import com.ritesh.edufleet.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    AuthController(AuthService authService) {
        this.authService = authService;
    }

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
}
