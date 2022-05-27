package com.nabin.jwtonly.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

import static com.nabin.jwtonly.constant.SecurityConstant.*;

/**
 * This filter will check the existence and validity of the access token on the Authorization header.
 * We will specify which endpoints will be subject to this filter in our configuration class.
 *
 * @author Narendra
 * @version 1.0
 * @since 2022-05-27
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * The doFilterInternal method intercepts the requests then checks the Authorization header.
     * If the header is not present or doesn’t start with “BEARER”, it proceeds to the filter chain.
     * If the header is present, the getAuthentication() is invoked.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest req,
                                    HttpServletResponse res,
                                    FilterChain chain) throws IOException, ServletException {
        String header = req.getHeader(HEADER_STRING);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(req, res);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(req);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(req, res);
    }


    /**
     * <b>Reads the JWT from the Authorization header, and then uses JWT to validate the token</b>
     *
     * getAuthentication() verifies the JWT and if the token is valid, it returns an access token which Spring will use internally.
     * This new token is then saved to SecurityContext. We can also pass in Authorities to this token if we need for role-based authorization.
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);

        if (token == null) {
            return null;
        }
        // parse the token.
        String user = JWT.require(Algorithm.HMAC512(SECRET.getBytes()))
                .build()
                .verify(token.replace(TOKEN_PREFIX, ""))
                .getSubject();

        if (user == null) {
            return null;
        }

        // new arraylist means authorities
        return new UsernamePasswordAuthenticationToken(user, null, new ArrayList<>());
    }
}
