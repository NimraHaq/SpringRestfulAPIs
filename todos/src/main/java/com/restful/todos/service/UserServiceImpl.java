package com.restful.todos.service;

import com.restful.todos.entity.Authority;
import com.restful.todos.entity.User;
import com.restful.todos.repository.UserRepository;
import com.restful.todos.response.UserResponse;
import com.restful.todos.utils.FindAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
    }

    @Override
    public UserResponse getUserInfo() {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        return new UserResponse(user.getId(), user.getFirstName()+ " " +user.getLastName(), user.getEmail(),
                user.getAuthorities().stream().map(auth -> (Authority) auth).toList());
    }

    @Override
    public void deleteUser() {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        if(isLastAdmin(user)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Last admin cannot delete itself.");
        }
        userRepository.delete(user);

    }

    private boolean isLastAdmin(User user){
        boolean isAdmin = user.getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equalsIgnoreCase("ROLE_ADMIN"));

        if(isAdmin){
            return userRepository.countAdminUsers() <= 1;
        }
        return false;
    }

}
