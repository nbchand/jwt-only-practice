package com.nabin.jwtonly.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabin.jwtonly.constant.SecurityConstant;
import com.nabin.jwtonly.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    //The attemptAuthentication function runs when the user tries to log in to our application.
    //It reads the credentials, creates a user POJO from them, and then checks the credentials to authenticate.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User credential = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credential.getUserName(),
                            credential.getPassword(),
                            //the empty list represents the authorities (roles),
                            //and we leave it as is since we do not have any roles in our application yet.
                            new ArrayList<>()
                    )
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    //If the authentication is successful, the successfulAuthentication method runs.
    //The parameters of this method are passed by Spring Security behind the scenes.
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws IOException, ServletException {
        //creates JWT token
        String token = JWT.create()
                //adds username to the token
                .withSubject(((User)authResult.getPrincipal()).getUserName())
                //adds expiration time to the token
                .withExpiresAt(new Date(System.currentTimeMillis()+ SecurityConstant.EXPIRATION_TIME))
                //first secret key is hashed using HS256 algorithm
                //then the token is signed using the hashed secret key
                .sign(Algorithm.HMAC256(SecurityConstant.SECRET.getBytes()));

        //adds token to the response
        String body = ((User) authResult.getPrincipal()).getUserName() + " " + token;
        response.getWriter().write(body);
        response.getWriter().flush();
    }
}
