package com.sendkite.teatapp.user.controller;

import com.sendkite.teatapp.user.domain.User;
import com.sendkite.teatapp.user.domain.UserCreate;
import com.sendkite.teatapp.user.controller.response.UserResponse;
import com.sendkite.teatapp.user.infrastructure.UserEntity;
import com.sendkite.teatapp.user.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "유저(users)")
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserCreateController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody UserCreate userCreate) {
        User user = userService.create(userCreate);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(UserResponse.from(user));
    }

}
