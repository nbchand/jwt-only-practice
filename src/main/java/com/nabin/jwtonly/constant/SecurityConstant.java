package com.nabin.jwtonly.constant;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
public class SecurityConstant {
    public static final String SECRET = "karnivore69";
    public static final long EXPIRATION_TIME = 900_000; // 15 minutes
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final String SIGN_UP_URL = "/user/sign-up";
}