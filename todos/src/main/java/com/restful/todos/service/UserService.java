package com.restful.todos.service;

import com.restful.todos.request.PasswordUpdateRequest;
import com.restful.todos.response.UserResponse;

public interface UserService {
    UserResponse getUserInfo();
    void deleteUser();
    void updatePassword(PasswordUpdateRequest passwordUpdateRequest);
}
