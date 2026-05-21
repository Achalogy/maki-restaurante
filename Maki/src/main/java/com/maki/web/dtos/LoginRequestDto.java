package com.maki.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO 5: Request del login unificado.
 * Recibe username (email u operador) y password.
 * Queda claro en la documentación qué se necesita para iniciar sesión.
 */
@Data
@NoArgsConstructor
public class LoginRequestDto {
    private String username;
    private String password;
}
