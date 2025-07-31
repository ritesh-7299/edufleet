package com.ritesh.edufleet.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ritesh.edufleet.system.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException)
            throws IOException {

        if (response.isCommitted()) {
            log.warn("Response already committed. Cannot handle AccessDeniedException.");
            return;
        }

        log.warn("Access Denied: {}", accessDeniedException.getMessage());

        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setContentType("application/json");

        ErrorResponse err = new ErrorResponse(
                "Access Denied",
                request.getRequestURI(),
                HttpServletResponse.SC_FORBIDDEN
        );

        String json = objectMapper.writeValueAsString(err);
        response.getWriter().write(json);
        response.getWriter().flush();
    }
}