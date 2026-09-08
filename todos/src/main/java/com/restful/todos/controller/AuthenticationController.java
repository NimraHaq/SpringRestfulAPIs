package com.restful.todos.controller;

import com.restful.todos.request.AuthenticationRequest;
import com.restful.todos.request.RegisterRequest;
import com.restful.todos.response.AuthenticationResponse;
import com.restful.todos.service.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication REST api endpoint.", description = "Endpoints for user authentication and authorization")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @Operation(summary = "Register a new user", description = "Registers a new user with the provided details.")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/register")
    public void register(@Valid @RequestBody RegisterRequest request){
        authenticationService.register(request);
    }

    @Operation(summary = "Authenticate a user", description = "Authenticates a user with the provided credentials and returns a JWT token.")
    @ResponseStatus(HttpStatus.OK)
    @PostMapping("/login")
    public AuthenticationResponse login(@Valid @RequestBody AuthenticationRequest authenticationRequest){
        return authenticationService.login(authenticationRequest);
    }
}
