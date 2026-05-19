package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import com.maki.web.security.JWTGenerator;
import com.maki.web.entities.UserEntity;
import com.maki.web.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTGenerator jwtGenerator;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/log-in")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);
            String role = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .findFirst().orElse("UNKNOWN");

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("role", role);
            response.put("username", username);

            Optional<UserEntity> userOpt = userRepository.findByUsername(username);
            if(userOpt.isPresent()) {
                response.put("id", userOpt.get().getId());
            }

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
        }
    }
}
