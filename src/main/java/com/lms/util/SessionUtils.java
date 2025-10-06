package com.lms.util;

import jakarta.servlet.Servlet;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

public class SessionUtils {
    public static String checkLoggedInEmployee(HttpServletRequest httpRequest, HttpServletResponse httpResponse)
            throws IOException, ServletException {

        HttpSession httpSession = httpRequest.getSession(false);

        if (httpSession == null || httpSession.getAttribute("employeeID") == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.getWriter().print("{\"error\": \"Unauthorized access. Please login again.\"}");
            return null;
        }

        long lastAccessed = httpSession.getLastAccessedTime();
        //httpResponse.getWriter().println("Session last accessed at: " + lastAccessed);

        return (String)httpSession.getAttribute("employeeID");

    }
}
