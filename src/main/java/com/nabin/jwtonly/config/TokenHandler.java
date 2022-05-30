package com.nabin.jwtonly.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Date;

import static com.nabin.jwtonly.constant.SecurityConstant.*;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-30
 */
public class TokenHandler {

    //Generates token for given username
    public static String generateToken(String username) {
        return JWT.create()
                //adds username to the token
                .withSubject(username)
                //adds expiration time to the token
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                //first secret key is hashed using HS256 algorithm
                //then the token is signed using the hashed secret key
                .sign(Algorithm.HMAC512(SECRET.getBytes()));
    }

    //decrypts token and extracts username from it
    public static String getUsernameFromToken(String token) {
                //secret key is needed
        return JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                //if secret key is present verify the token
                .verify(token.replace(TOKEN_PREFIX,""))
                .getSubject();
    }
}
