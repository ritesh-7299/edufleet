package com.ritesh.edufleet.filter;

import com.ritesh.edufleet.auth.service.UserServiceImpl;
import com.ritesh.edufleet.system.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@AllArgsConstructor
@Slf4j
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;
    private final UserServiceImpl userService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        log.warn("Auth header 1::" + authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            String token = authHeader.substring(7);
            String email = jwtUtils.extractUsername(token);

            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                log.warn("Auth emailqqqqqqqqq::" + email);
                UserDetails userDetails = userService.loadUserByUsername(email);
                log.warn("Auth userDetails::" + userDetails.getUsername());
                if (jwtUtils.validateToken(token)) {
                    UsernamePasswordAuthenticationToken aToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                    aToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(aToken);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
