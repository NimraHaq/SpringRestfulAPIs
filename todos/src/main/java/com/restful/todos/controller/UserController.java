package com.restful.todos.controller;

import com.restful.todos.request.PasswordUpdateRequest;
import com.restful.todos.response.UserResponse;
import com.restful.todos.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "User REST Api Endpoints.", description = "User management operations")
@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @Operation(summary = "Get user information", description = "Retrieves the information of the currently authenticated user.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/info")
    public UserResponse getUserInfo(){
        return userService.getUserInfo();
    }

    @Operation(summary = "Delete user account", description = "Deletes the account of the currently authenticated user. This action is irreversible.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete")
     void deleteUser(){
        userService.deleteUser();
     }

     @Operation(summary = "Update user password", description = "Updates the password of the currently authenticated user. Requires the current password and the new password.")
     @ResponseStatus(HttpStatus.OK)
     @PutMapping("/password")
     void updatePassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        userService.updatePassword(passwordUpdateRequest);
     }

}
