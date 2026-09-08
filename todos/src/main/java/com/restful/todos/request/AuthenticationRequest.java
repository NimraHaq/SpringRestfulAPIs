package com.restful.todos.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthenticationRequest {

    @NotBlank(message = "Email is required")
    @Email(message = "Email is not valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min =6, max = 12, message = "Password must be between 6 and 12 characters")
    private String password;

    public AuthenticationRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
