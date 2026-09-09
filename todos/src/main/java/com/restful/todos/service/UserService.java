package com.restful.todos.service;

import com.restful.todos.response.UserResponse;

public interface UserService {
    UserResponse getUserInfo();
    void deleteUser();
}
