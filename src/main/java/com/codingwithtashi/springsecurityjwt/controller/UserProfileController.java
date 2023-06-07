package com.codingwithtashi.springsecurityjwt.controller;

import com.codingwithtashi.springsecurityjwt.model.User;
import com.codingwithtashi.springsecurityjwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user-profiles")
public class UserProfileController {

    @Autowired
    private UserRepository userProfileRepository;

    // Get all user profiles
    @GetMapping
    public List<User> getAllUserProfiles() {
        return userProfileRepository.findAll();
    }

    // Get a user profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserProfile(@PathVariable Long id) {
        User userProfile = userProfileRepository.findById(id).orElse(null);
        if (userProfile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(userProfile, HttpStatus.OK);
    }

    // Create a new user profile
    @PostMapping
    public ResponseEntity<User> createUserProfile(@RequestBody User userProfile) {
        User createdUserProfile = userProfileRepository.save(userProfile);
        return new ResponseEntity<>(createdUserProfile, HttpStatus.CREATED);
    }

    // Update a user profile by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUserProfile(@PathVariable Long id, @RequestBody User userProfile) {
        User existingUserProfile = userProfileRepository.findById(id).orElse(null);
        if (existingUserProfile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userProfile.setId(id);
        User updatedUserProfile = userProfileRepository.save(userProfile);
        return new ResponseEntity<>(updatedUserProfile, HttpStatus.OK);
    }

    // Delete a user profile by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUserProfile(@PathVariable Long id) {
        User userProfile = userProfileRepository.findById(id).orElse(null);
        if (userProfile == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userProfileRepository.delete(userProfile);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

