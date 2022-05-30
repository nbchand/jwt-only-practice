package com.nabin.jwtonly.controller;

import com.nabin.jwtonly.config.TokenHandler;
import com.nabin.jwtonly.dto.LoginResponse;
import com.nabin.jwtonly.dto.ResponseDto;
import com.nabin.jwtonly.dto.UserDto;
import com.nabin.jwtonly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostMapping("/sign-up")
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    /**
     * Custom access_token generation after successful login.
     */
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> loginUser(@RequestBody UserDto userDto) {
        UserDto userDto1 = userService.findByUsername(userDto.getUsername());
        if (userDto1 == null || !bCryptPasswordEncoder.matches(userDto.getPassword(), userDto1.getPassword())) {
            return ResponseEntity.badRequest().body(new ResponseDto("Login failed", null));
        }
        return ResponseEntity.ok().body(new ResponseDto("Login successful!", new LoginResponse("Access token generated", TokenHandler.generateToken(userDto.getUsername()))));
    }

    @GetMapping("/all")
    public ResponseEntity<ResponseDto> findAll() {
        return ResponseEntity.ok(new ResponseDto("Users list fetched", userService.findAll()));
    }
}
