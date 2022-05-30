package com.nabin.jwtonly.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-30
 */
@Data
@AllArgsConstructor
public class ResponseDto {
    private String message;
    private Object data;
}
