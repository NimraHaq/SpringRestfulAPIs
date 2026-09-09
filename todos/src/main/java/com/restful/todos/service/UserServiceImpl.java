package com.restful.todos.service;

import com.restful.todos.entity.Authority;
import com.restful.todos.entity.User;
import com.restful.todos.repository.UserRepository;
import com.restful.todos.request.PasswordUpdateRequest;
import com.restful.todos.response.UserResponse;
import com.restful.todos.utils.FindAuthenticatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final FindAuthenticatedUser findAuthenticatedUser;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, FindAuthenticatedUser findAuthenticatedUser, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.findAuthenticatedUser = findAuthenticatedUser;
        this.passwordEncoder = passwordEncoder;
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

    @Override
    public void updatePassword(PasswordUpdateRequest passwordUpdateRequest) {
        User user = findAuthenticatedUser.getAuthenticatedUser();
        if (!isCurrentPasswordCorrect(passwordUpdateRequest.getCurrentPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect.");
        }
        if (!isNewPasswordValid(passwordUpdateRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New passwords do not match.");
        }
        if (areOldAndNewPasswordsSame(passwordUpdateRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "New password cannot be the same as the current password.");
        }

        user.setPassword(passwordEncoder.encode(passwordUpdateRequest.getNewPassword()));
        userRepository.save(user);
    }

    private boolean isCurrentPasswordCorrect(String currentPasswordEntered, String currentPasswordFromDB) {
        return passwordEncoder.matches(currentPasswordEntered, currentPasswordFromDB);
    }

    private boolean isNewPasswordValid(PasswordUpdateRequest passwordUpdateRequest) {
        return passwordUpdateRequest.getNewPassword().equals(passwordUpdateRequest.getConfirmedNewPassword());
    }

    private boolean areOldAndNewPasswordsSame(PasswordUpdateRequest passwordUpdateRequest) {
        return passwordUpdateRequest.getCurrentPassword().equals(passwordUpdateRequest.getNewPassword());
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
