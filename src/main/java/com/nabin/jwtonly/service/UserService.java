package com.nabin.jwtonly.service;

import com.nabin.jwtonly.dto.UserDto;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
public interface UserService {
    UserDto createUser(UserDto userDto);
}
