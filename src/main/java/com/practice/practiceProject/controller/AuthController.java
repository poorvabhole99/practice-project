package com.practice.practiceProject.controller;

import com.practice.practiceProject.Security.JwtHelper;
import com.practice.practiceProject.Security.UserDetailsServiceImpl;
import com.practice.practiceProject.entities.JwtRequest;
import com.practice.practiceProject.entities.JwtResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private JwtHelper jwtHelper;

    @Autowired
    private UserDetailsService userDetailsServiceImpl;

    @Autowired
    private AuthenticationManager authenticationManager;
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest jwtRequest){
        authenticateUser(jwtRequest.getEmailId(), jwtRequest.getPassword());
        final UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(jwtRequest.getEmailId());
        final String token = jwtHelper.generateToken(userDetails);
        final JwtResponse jwtResponse = JwtResponse.builder().token(token).username(jwtRequest.getEmailId()).build();
        return new ResponseEntity<>(jwtResponse, HttpStatus.OK);
    }

    private void authenticateUser(String emailId, String password){
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(emailId, password);
        try {
            authenticationManager.authenticate(authenticationToken);
        }catch (BadCredentialsException badCredentialsException){
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
