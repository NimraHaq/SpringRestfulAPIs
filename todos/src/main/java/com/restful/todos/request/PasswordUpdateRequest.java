package com.restful.todos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PasswordUpdateRequest {
    @NotBlank(message = "Password is required")
    @Size(min =6, max = 12, message = "Password must be between 6 and 12 characters")
    private String currentPassword;
    @NotBlank(message = "Password is required")
    @Size(min =6, max = 12, message = "Password must be between 6 and 12 characters")
    private String newPassword;
    @NotBlank(message = "Password is required")
    @Size(min =6, max = 12, message = "Password must be between 6 and 12 characters")
    private String confirmedNewPassword;

    public PasswordUpdateRequest(String currentPassword, String newPassword, String confirmedNewPassword) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmedNewPassword = confirmedNewPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmedNewPassword() {
        return confirmedNewPassword;
    }

    public void setConfirmedNewPassword(String confirmedNewPassword) {
        this.confirmedNewPassword = confirmedNewPassword;
    }
}
