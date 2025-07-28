package com.ritesh.edufleet.service;

import com.ritesh.edufleet.dto.LoginDto;
import com.ritesh.edufleet.dto.SignupRequest;
import com.ritesh.edufleet.entity.User;
import com.ritesh.edufleet.exception.BadRequestException;
import com.ritesh.edufleet.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }


    /**
     * Function to create an user account in system
     *
     * @param signupRequest
     * @return
     */
    public String signup(SignupRequest signupRequest) {
        Optional<User> userByUsernameAndEmail = getUserByUsernameOrEmail(signupRequest.getUsername(), signupRequest.getEmail());
        if (userByUsernameAndEmail.isPresent()) {
            throw new BadRequestException("User already exist");
        }
        User user = new User();
        user.setUsername(signupRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        user.setEmail(signupRequest.getEmail());
        user.setRoles("ROLE_USER");
        userRepository.save(user);
        return "User created!!";
    }

    /**
     * Function to login a user
     *
     * @param loginDto
     * @return
     */
    public String login(LoginDto loginDto) {
        try {
            log.warn("What is auth:::before");
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword())
            );
            return "Logged In";
        } catch (AuthenticationException e) {
            throw new BadRequestException("Invalid Credentials");
        }
    }

    /**
     * Function to get the existing user by username or email
     *
     * @param username
     * @param email
     * @return
     */
    private Optional<User> getUserByUsernameOrEmail(String username, String email) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            user = userRepository.findByEmail(email);
        }
        return user;
    }
}
