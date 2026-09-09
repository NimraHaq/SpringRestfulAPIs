package com.restful.todos.response;


public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private int priority;
    private boolean isComplete;

    public TodoResponse(Long id, String title, String description, int priority, boolean isComplete) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.isComplete = isComplete;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        this.isComplete = complete;
    }
}
