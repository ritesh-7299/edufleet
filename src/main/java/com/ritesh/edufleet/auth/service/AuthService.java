package com.ritesh.edufleet.auth.service;

import com.ritesh.edufleet.auth.dto.LoginDto;
import com.ritesh.edufleet.auth.dto.SignupRequest;
import com.ritesh.edufleet.auth.entity.User;
import com.ritesh.edufleet.auth.repository.UserRepository;
import com.ritesh.edufleet.exception.BadRequestException;
import com.ritesh.edufleet.role.entity.Role;
import com.ritesh.edufleet.role.service.RoleService;
import com.ritesh.edufleet.system.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
     * Function to create an user account in system
     *
     * @param signupRequest
     * @return
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
     * Function to login a user
     *
     * @param loginDto
     * @return
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
