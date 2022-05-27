package com.nabin.jwtonly.dto;

import com.nabin.jwtonly.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private String userName;
    private String email;
    private String password;

    public UserDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.email = user.getEmail();
        this.password = user.getPassword();
    }

    public static List<UserDto> userToDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(new UserDto(user));
        }
        return userDtoList;
    }
}
