package com.restful.todos.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TodoRequest {

    @NotBlank(message = "Title is required")
    @Size(min = 3, max = 60, message = "Title must be at most 100 characters")
    private String title;

    @NotBlank(message = "Description is required")
    @Size(min = 3, max = 200, message = "Description must be at most 200 characters")
    private String description;

    @Min(value = 1, message = "Priority must be a positive integer")
    @Max(value = 5, message = "Priority must be between 1 and 5")
    private int priority;

    public TodoRequest() {
    }

    public TodoRequest(String title, String description, int priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
