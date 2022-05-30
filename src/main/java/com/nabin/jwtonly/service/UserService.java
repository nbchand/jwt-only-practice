package com.nabin.jwtonly.service;

import com.nabin.jwtonly.dto.UserDto;

import java.util.List;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto findByUsernameAndPassword(String username, String password);
    UserDto findByUsername(String username);
    List<UserDto> findAll();
}
