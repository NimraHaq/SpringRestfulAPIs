package com.restful.todos.service;

import com.restful.todos.entity.Authority;
import com.restful.todos.entity.User;
import com.restful.todos.repository.UserRepository;
import com.restful.todos.request.AuthenticationRequest;
import com.restful.todos.request.RegisterRequest;
import com.restful.todos.response.AuthenticationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class AuthenticationServiceImpl implements AuthenticationService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Autowired
    public AuthenticationServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager,
                                     JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    @Transactional
    public void register(RegisterRequest request) {
        if (isEmailTaken(request.getEmail())) {
            throw new RuntimeException("Email is already taken");
        }
        User user = buildUser(request);
        userRepository.save(user);
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest request) {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        User user = userRepository.findUserByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String token = jwtService.generateToken(new HashMap<>(), user);
        return new AuthenticationResponse(token);
    }

    private boolean isEmailTaken(String email){
        return userRepository.findUserByEmail(email).isPresent();
    }

    private User buildUser(RegisterRequest request){
        User user = new User();
        user.setId(null);
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setAuthorities(initialAuthorities());
        return user;
    }

    private List<Authority> initialAuthorities(){
        boolean isFirstUser = userRepository.count() == 0;
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        if(isFirstUser){
            authorities.add(new Authority("ROLE_ADMIN"));
        }
        return authorities;
    }
}
