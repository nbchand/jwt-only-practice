package com.nabin.jwtonly.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

/**
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
//We need authentication to make sure that the user is really who they claim to be.
//We will be using the classic username/password pair to accomplish this.

/**
 * This class is not required if we perform custom authentication.
 */
@RequiredArgsConstructor
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    private Gson g = new Gson();

    //The attemptAuthentication function runs when the user tries to log in to our application.
    //It reads the credentials, creates a user POJO from them, and then checks the credentials to authenticate.
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            User credential = new ObjectMapper()
                    .readValue(request.getInputStream(), User.class);

            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credential.getUsername(),
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

        User user = g.fromJson(g.toJson(authResult.getPrincipal()), User.class);
        //creates JWT token
        String token = TokenHandler.generateToken(user.getUsername());

        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
