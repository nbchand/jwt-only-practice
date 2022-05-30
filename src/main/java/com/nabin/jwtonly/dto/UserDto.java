package com.nabin.jwtonly.dto;

import com.nabin.jwtonly.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Integer id;
    private String username;
    private String email;
    private String password;
    private String role;

    public UserDto(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.role = user.getRole();
    }

    public static List<UserDto> userToDtoList(List<User> userList) {
        return userList.stream().map(user -> new UserDto(user)).collect(Collectors.toList());
    }
}
