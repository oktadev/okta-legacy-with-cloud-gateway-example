package com.okta.example;

import com.okta.jwt.Jwt;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "UserProfile", urlPatterns = {"/", "/profile"})
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Jwt accessToken = (Jwt) request.getAttribute(BearerTokenFilter.ACCESS_TOKEN); // <1>
        request.setAttribute("email", accessToken.getClaims().get("sub")); // <2>
        request.setAttribute("userAttributes", accessToken.getClaims()); // <3>

        request.getRequestDispatcher("/WEB-INF/user-profile.jsp").forward(request, response);
    }
}