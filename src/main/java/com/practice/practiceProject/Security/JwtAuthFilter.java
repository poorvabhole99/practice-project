package com.practice.practiceProject.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * This class represents a JWT authentication filter that intercepts incoming requests
 * to validate JWT tokens and set up authentication if the token is valid.
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {
    private final JwtHelper jwtHelper;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    public JwtAuthFilter(JwtHelper jwtHelper,UserDetailsServiceImpl userDetailsServiceImpl){
        this.jwtHelper = jwtHelper;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }



    /**
     * Performs the filter logic to validate JWT tokens and set up authentication.
     *
     * @param request the HTTP servlet request
     * @param response the HTTP servlet response
     * @param filterChain the filter chain
     * @throws ServletException if an error occurs during the servlet operation
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;
        String username = null;

        // Extract token and username from the authorization header
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            token = authHeader.substring(7);
            username = jwtHelper.getUsernameFromToken(token);
        }

        // If username is not null and there's no existing authentication
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            // Load user details by username
            UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);

            // Validate token with user details
            if(jwtHelper.validateToken(token, userDetails)){
                // Create authentication token
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Set authentication in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }

        // Continue filter chain
        filterChain.doFilter(request, response);
    }
}
