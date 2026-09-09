package com.restful.todos.response;

import com.restful.todos.entity.Authority;

import java.util.List;

public class UserResponse {

    private Long Id;

    private String fullName;

    private String email;

    private List<Authority> authorities;


    public UserResponse(Long id, String fullName, String email, List<Authority> authorities) {
        Id = id;
        this.fullName = fullName;
        this.email = email;
        this.authorities = authorities;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Authority> getAuthorities() {
        return authorities;
    }

    public void setAuthorities(List<Authority> authorities) {
        this.authorities = authorities;
    }
}
