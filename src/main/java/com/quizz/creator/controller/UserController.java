package com.quizz.creator.controller;

import com.quizz.creator.pojo.User;
import com.quizz.creator.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/api/register")
    public ResponseEntity<?> register(@Valid @RequestBody User user) {
        return userService.getByEmail(user.getEmail())
                .map(u -> ResponseEntity.badRequest().build())
                .orElseGet(() -> {
                    userService.create(user);

                    return ResponseEntity.ok().build();
                });
    }

}