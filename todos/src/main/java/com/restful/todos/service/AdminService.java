package com.restful.todos.service;

import com.restful.todos.entity.User;
import com.restful.todos.response.UserResponse;

import java.util.List;

public interface AdminService {

    List<UserResponse> getallUsers();
    UserResponse promoteToAdmin(long userId);
    void deleteNonAdminUser(long id);

}
