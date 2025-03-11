package com.trendy.login.signup;

public class SuccessResponse {
    private String message;
    private String status;

    public SuccessResponse(String message, String status) {
        this.message = message;
        this.status = status;
    }

    // Getters 및 Setters
    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setStatus(String status) {
        this.status = status;
    }
} 