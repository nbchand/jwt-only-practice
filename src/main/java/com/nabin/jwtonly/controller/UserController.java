package com.nabin.jwtonly.controller;

import com.nabin.jwtonly.dto.UserDto;
import com.nabin.jwtonly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/sign-up")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }
}
