package com.restful.todos.service;

import com.restful.todos.request.AuthenticationRequest;
import com.restful.todos.request.RegisterRequest;
import com.restful.todos.response.AuthenticationResponse;

public interface AuthenticationService {
    void register(RegisterRequest request);
    AuthenticationResponse login(AuthenticationRequest request);
}
