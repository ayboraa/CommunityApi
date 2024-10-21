package org.community.api.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.community.api.dto.AuthRequest;
import org.community.api.dto.AuthResponse;
import org.community.api.auth.JwtTokenUtil;
import org.community.api.common.Email;
import org.community.api.controller.exception.ResourceNotFoundException;
import org.community.api.dto.user.RegisterMemberDTO;
import org.community.api.service.MemberService;
import org.community.api.service.impl.UserDetailsImpl;
import org.community.api.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class AuthController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthRequest authRequest, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );

        UserDetailsImpl userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

        String jwt = jwtTokenUtil.generateToken(userDetails.getUsername(), userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toUnmodifiableList()));

        Cookie cookie = new Cookie("JWT", jwt);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(86400);
        cookie.setSecure(true);

        response.addCookie(cookie);

        return ResponseEntity.ok(new AuthResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<Map<String, Object>> registerUser(@RequestBody Map<String, String> userInfo) {
        String email = userInfo.get("email");
        String password = userInfo.get("password");
        String name = userInfo.get("name");
        String surname = userInfo.get("surname");

        Email mail = new Email(email);

        try {
            memberService.findMemberByEmail(mail);

            Map<String, Object> response = new HashMap<>();
            response.put("success", false);
            response.put("message", "User already exists");
            return ResponseEntity.badRequest().body(response);
        }catch (ResourceNotFoundException ex){
            memberService.registerMember(new RegisterMemberDTO(name, surname, mail, password));

            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("message", "User registered successfully");

            return ResponseEntity.ok(response);
        }

    }

}