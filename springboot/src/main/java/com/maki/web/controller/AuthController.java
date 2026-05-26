package com.maki.web.controller;

import com.maki.web.repository.UserEntityRepository;
import com.maki.web.security.JWTGenerator;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * CORRECCIÓN: La clase se llama JWTGenerator (no JwtGenerator). Se corrige el import y la
 * referencia.
 *
 * <p>Ubicación: src/main/java/com/maki/web/controller/AuthController.java
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;

    @Autowired private JWTGenerator jwtGenerator;

    @Autowired private UserEntityRepository userEntityRepository;

    /**
     * Login unificado para CLIENT, OPERATOR y ADMIN. Recibe: { "username": "...", "password": "..."
     * } Devuelve: { "token": "...", "role": "CLIENT|OPERATOR|ADMIN", "username": "..." }
     *
     * <p>El frontend guarda el token y llama a /me para saber quién es el usuario. NO devuelve el
     * id — eso lo resuelve /client/me o /operator/me con el JWT.
     */
    @PostMapping("/log-in")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            Authentication authentication =
                    authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(username, password));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);

            String role =
                    authentication.getAuthorities().stream()
                            .findFirst()
                            .map(a -> a.getAuthority())
                            .orElse("UNKNOWN");

            return ResponseEntity.ok(Map.of("token", token, "role", role, "username", username));
        } catch (Exception e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
        }
    }
}
