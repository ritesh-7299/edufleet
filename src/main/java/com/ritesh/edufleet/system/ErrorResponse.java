package com.ritesh.edufleet.system;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ErrorResponse {
    private String message;
    private String timestamp;
    private String path;
    private int status;

    public ErrorResponse(String message, String path, int status) {
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
        this.path = path;
        this.status = status;
    }
}
