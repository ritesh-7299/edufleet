package com.ritesh.edufleet.controller;

import com.ritesh.edufleet.dto.LoginDto;
import com.ritesh.edufleet.dto.SignupRequest;
import com.ritesh.edufleet.service.AuthService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
