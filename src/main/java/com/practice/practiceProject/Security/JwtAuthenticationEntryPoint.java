package com.practice.practiceProject.Security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 * Component class implementing the AuthenticationEntryPoint interface.
 * This class serves as the entry point for handling authentication failures in JWT (JSON Web Token) authentication.
 * It is responsible for customizing the behavior when an unauthenticated user tries to access protected resources.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    /**
     * Method called when an unauthenticated user attempts to access a protected resource.
     * It sets the HTTP status code to 401 (Unauthorized) and writes an error message to the response,
     * indicating that access is denied due to authentication failure.
     *
     * @param request       The HttpServletRequest representing the incoming request.
     * @param response      The HttpServletResponse representing the outgoing response.
     * @param authException The AuthenticationException indicating the reason for authentication failure.
     * @throws IOException  If an I/O error occurs while writing to the response.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
        throws IOException {
        // Set the HTTP status code to 401 (Unauthorized)
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        // Get a PrintWriter to write to the response
        PrintWriter writer = response.getWriter();

        // Write an error message to the response indicating access is denied due to authentication failure
        writer.println("Access Denied !! Message: " + authException.getMessage());
    }
}
