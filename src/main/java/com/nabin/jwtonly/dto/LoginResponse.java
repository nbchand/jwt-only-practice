package com.nabin.jwtonly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-30
 */
@Data
@AllArgsConstructor
public class LoginResponse {
    private String message;
    private String token;
}
