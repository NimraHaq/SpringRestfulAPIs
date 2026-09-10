package com.restful.todos.service;

import com.restful.todos.entity.Authority;
import com.restful.todos.entity.User;
import com.restful.todos.repository.UserRepository;
import com.restful.todos.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class AdminServiceImpl implements AdminService {

    private final UserRepository userRepository;

    @Autowired
    public AdminServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserResponse> getallUsers() {
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
                .map(this::mapEntityToResponse).toList();
    }

    @Override
    @Transactional
    public UserResponse promoteToAdmin(long userId) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty() || user.get().getAuthorities().stream().anyMatch(
                auth-> "ROLE_ADMIN".equalsIgnoreCase(auth.getAuthority()))){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found or already an admin.");
        }
        List<Authority> authorities = new ArrayList<>();
        authorities.add(new Authority("ROLE_EMPLOYEE"));
        authorities.add(new Authority("ROLE_ADMIN"));
        user.get().setAuthorities(authorities);
        return mapEntityToResponse(userRepository.save(user.get()));
    }

    @Override
    @Transactional
    public void deleteNonAdminUser(long id) {
        Optional<User> user = userRepository.findById(id);
        if(user.isEmpty() || user.get().getAuthorities().stream().anyMatch(
                auth-> "ROLE_ADMIN".equalsIgnoreCase(auth.getAuthority()))){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found or an admin.");
        }
        userRepository.delete(user.get());
    }

    private UserResponse mapEntityToResponse(User user) {
        return new UserResponse(user.getId(), user.getFirstName() + " " + user.getLastName(), user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList());
    }
}
