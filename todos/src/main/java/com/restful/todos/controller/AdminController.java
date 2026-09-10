package com.restful.todos.controller;

import com.restful.todos.response.UserResponse;
import com.restful.todos.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin REST Api Endpoints.", description = "Admin management operations")
@RequestMapping("/api/admin")
@RestController
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @Operation(summary = "Get all users", description = "Retrieves a list of all users in the system. This endpoint is intended for administrative use.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/users")
    public List<UserResponse> getAllUsers(){
        return adminService.getallUsers();
    }

    @Operation(summary = "Promote user to admin", description = "Promotes a user to the admin role. This endpoint is intended for administrative use.")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{userId}/promote")
    public UserResponse promoteToAdmin(@PathVariable @Min(1) long userId){
        return adminService.promoteToAdmin(userId);
    }

    @Operation(summary = "Delete non-admin user", description = "Deletes a user from the system if they are not an admin. This endpoint is intended for administrative use.")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    public void deleteNonAdminUser(@PathVariable @Min(1) long id){
        adminService.deleteNonAdminUser(id);
    }


}
