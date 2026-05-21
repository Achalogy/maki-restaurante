package com.maki.web.controller;

import com.maki.web.repository.UserEntityRepository;
import com.maki.web.security.JwtGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Controlador de autenticación unificado.
 * Maneja el login para TODOS los tipos de usuario (client, operator, admin).
 * Devuelve un JWT con el rol incluido para que Angular redirija correctamente.
 */
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtGenerator jwtGenerator;

    @Autowired
    private UserEntityRepository userEntityRepository;

    /**
     * Login unificado — funciona para client, operator y admin.
     * Recibe: { "username": "...", "password": "..." }
     * Devuelve: { "token": "...", "role": "CLIENT|OPERATOR|ADMIN", "username": "..." }
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String username = credentials.get("username");
            String password = credentials.get("password");

            // Spring Security verifica las credenciales contra la tabla users
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(username, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Genera el JWT
            String token = jwtGenerator.generateToken(authentication);

            // Obtiene el rol del usuario autenticado
            String role = authentication.getAuthorities().stream()
                    .findFirst()
                    .map(a -> a.getAuthority())
                    .orElse("UNKNOWN");

            // Devuelve token + rol para que Angular redirija según el tipo de usuario
            return ResponseEntity.ok(Map.of(
                    "token", token,
                    "role", role,
                    "username", username
            ));

        } catch (Exception e) {
            return new ResponseEntity<>("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
        }
    }
}