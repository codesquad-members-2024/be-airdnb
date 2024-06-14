package com.example.airdnb.controller.user;

import com.example.airdnb.dto.user.UserCreateRequest;
import com.example.airdnb.dto.user.UserResponse;
import com.example.airdnb.service.user.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public Long createUser(@RequestBody @Valid UserCreateRequest userCreateRequest) {
        return userService.join(userCreateRequest);
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable Long userId) {
        return userService.findById(userId);
    }

}
