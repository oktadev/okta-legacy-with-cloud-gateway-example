package com.okta.example;

import com.okta.jwt.AccessTokenVerifier;
import com.okta.jwt.Jwt;
import com.okta.jwt.JwtVerificationException;
import com.okta.jwt.JwtVerifiers;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(urlPatterns = "*")
public class BearerTokenFilter implements Filter {

    public static final String ACCESS_TOKEN = "jwtAccessToken";
    private static final String ISSUER_KEY = "okta.oauth2.issuer";

    private AccessTokenVerifier tokenVerifier;

    public void init(FilterConfig filterConfig) throws ServletException {
        String issuer = System.getProperty(ISSUER_KEY, filterConfig.getInitParameter(ISSUER_KEY)); // <1>
        tokenVerifier = JwtVerifiers.accessTokenVerifierBuilder() // <2>
                .setIssuer(issuer)
                .build();
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String authHeader = request.getHeader("Authorization"); // <3>
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            request.getServletContext().log("Missing or invalid 'Authorization' header");
            respondWith401(response);
            return;
        }
        String token = authHeader.replaceFirst("^Bearer ", ""); // remove the prefix

        try {
            Jwt jwtAccessToken = tokenVerifier.decode(token); // <4>
            // invalid access tokens will throw an exception
            // add the access token as a request attribute
            request.setAttribute(ACCESS_TOKEN, jwtAccessToken); // <5>
            filterChain.doFilter(request, response); // <6>
        } catch (JwtVerificationException e) {
            request.getServletContext().log("Failed to parse access token", e);
            respondWith401(response);
        }
    }

    private void respondWith401(HttpServletResponse response) throws IOException { // <7>
        response.setStatus(401);
        response.setHeader("WWW-Authenticate","Bearer");
        response.getWriter().write("Authentication required");
    }

    public void destroy() {
        tokenVerifier = null;
    }
}
