package com.ritesh.edufleet.auth.service;

import com.ritesh.edufleet.auth.entity.User;
import com.ritesh.edufleet.auth.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

@Service
@Slf4j
public class UserServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        log.warn("What email:::::::::::;" + email);
        User user = userRepository.findByEmail(email).orElseThrow(() -> {
            log.warn("USERNOTFOUNDDDDDDDDDDDDDDDDDDDDDDDDD");
            throw new UsernameNotFoundException("User not found");
        });

        log.warn("What is the role name?" + user.getRole().getName());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(user.getRole().getName().toString()))
        );

    }
}
