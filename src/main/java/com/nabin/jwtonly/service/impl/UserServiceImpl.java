package com.nabin.jwtonly.service.impl;

import com.nabin.jwtonly.dto.UserDto;
import com.nabin.jwtonly.entity.User;
import com.nabin.jwtonly.repo.UserRepo;
import com.nabin.jwtonly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
@Service
@RequiredArgsConstructor
@Transactional(rollbackFor = Exception.class)
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserDto createUser(UserDto userDto) {
        User user = User.builder()
                .id(userDto.getId())
                .username(userDto.getUsername())
                .email(userDto.getEmail())
                .password(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .role(userDto.getRole())
                .build();
        return new UserDto(userRepo.save(user));
    }

    @Override
    public UserDto findByUsernameAndPassword(String username, String password) {
        User user = userRepo.findByUsernameAndPassword(username,password);
        if(user==null) {
            throw new RuntimeException("User not found");
        }
        return new UserDto(user);
    }

    @Override
    public UserDto findByUsername(String username) {
        return new UserDto(userRepo.findByUsername(username));
    }

    @Override
    public List<UserDto> findAll() {
        return UserDto.userToDtoList(userRepo.findAll());
    }
}
