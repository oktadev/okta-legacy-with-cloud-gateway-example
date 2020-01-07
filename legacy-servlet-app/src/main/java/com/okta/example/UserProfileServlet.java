package com.okta.example;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "UserProfile", urlPatterns = {"/", "/profile"})
public class UserProfileServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setAttribute("email", "jill.coder@example.com"); // faking an existing service
        Map<String, String> attributes = new HashMap<String, String>();
        attributes.put("sub", "jill.coder@example.com");                   // user attributes loaded from a
        request.setAttribute("userAttributes", attributes);

        request.getRequestDispatcher("/WEB-INF/user-profile.jsp").forward(request, response);
    }
}