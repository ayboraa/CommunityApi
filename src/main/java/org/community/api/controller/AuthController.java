package org.community.api.controller;

import org.community.api.auth.AuthRequest;
import org.community.api.auth.AuthResponse;
import org.community.api.auth.JwtTokenUtil;
import org.community.api.service.impl.UserDetailsImpl;
import org.community.api.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest) {
        // Authenticate the user
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        // Load user details
        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        // Generate JWT
        String jwt = jwtTokenUtil.generateToken(userDetails.getUsername());

        // Return the JWT
        return ResponseEntity.ok(new AuthResponse(jwt));
    }
}