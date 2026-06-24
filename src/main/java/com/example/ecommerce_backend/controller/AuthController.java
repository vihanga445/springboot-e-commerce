package com.example.ecommerce_backend.controller;


import com.example.ecommerce_backend.dto.AuthRequest;
import com.example.ecommerce_backend.dto.AuthResponse;
import com.example.ecommerce_backend.dto.RegisterRequest;
import com.example.ecommerce_backend.dto.TokenRefreshRequest;
import com.example.ecommerce_backend.entity.RefreshToken;
import com.example.ecommerce_backend.entity.User;
import com.example.ecommerce_backend.security.JwtService;
import com.example.ecommerce_backend.service.RefreshTokenService;
import com.example.ecommerce_backend.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Ref;

@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtService jwtService;
    private final UserService userService;
    private final RefreshTokenService refreshTokenService;

    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, JwtService jwtService, UserService userService, RefreshTokenService refreshTokenService) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.jwtService = jwtService;
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;

    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterRequest request) {
        User user = userService.registerUser(
                request.getName(),
                request.getEmail(),
                request.getPassword()
        );

        return ResponseEntity.ok("User registered successfully! Please log in to continue.");
    }

//    @PostMapping("/login")
//    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
//
//        Authentication auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
//
//        authenticationManager.authenticate(auth); // check weather the username and password are matched
//
//        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
//
//        String token = jwtService.generateToken(userDetails);
//
//        return ResponseEntity.ok(new AuthResponse(token));
//    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        Authentication auth = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        authenticationManager.authenticate(auth);

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String accessToken = jwtService.generateToken(userDetails);

        // Generate the database-backed refresh token
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getUsername());

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken()));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@RequestBody TokenRefreshRequest request) {
        return refreshTokenService.findByToken(request.getRefreshToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getUser)
                .map(user -> {
                    // Generate a fresh new access token
                    UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
                    String newAccessToken = jwtService.generateToken(userDetails);
                    return ResponseEntity.ok(new AuthResponse(newAccessToken, request.getRefreshToken()));
                })
                .orElseThrow(() -> new RuntimeException("Refresh token is not in database!"));
    }



}
