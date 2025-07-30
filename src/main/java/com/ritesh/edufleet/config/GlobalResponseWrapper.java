package com.ritesh.edufleet.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ritesh.edufleet.exception.BadRequestException;
import com.ritesh.edufleet.system.ApiResponse;
import com.ritesh.edufleet.system.ErrorResponse;
import com.ritesh.edufleet.system.annotation.SkipApiResponseWrapping;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@Slf4j
@RestControllerAdvice()
public class GlobalResponseWrapper implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        return !returnType.hasMethodAnnotation(SkipApiResponseWrapping.class)
                && !returnType.getParameterType().equals(byte[].class);
    }


    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType, Class<? extends HttpMessageConverter<?>> selectedConverterType, ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse<?> || body instanceof ErrorResponse) {
            return body;
        }

        if (body instanceof String) {
            try {
                response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
                ObjectMapper mapper = new ObjectMapper();
                return mapper.writeValueAsString(ApiResponse.success(body, "Request successful"));
            } catch (JsonProcessingException e) {
                throw new BadRequestException("Bed request");
            }
        }
        return ApiResponse.success(body, "Request successful");
    }
}
