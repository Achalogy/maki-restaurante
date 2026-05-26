package com.maki.web.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO 4: Respuesta del login. Devuelve el JWT y el rol para que Angular sepa a qué página
 * redirigir. NO devuelve el id — Angular usará /me para obtener datos del usuario.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponseDto {
    private String token;
    private String role; // CLIENT, OPERATOR, ADMIN
    private String username; // email o username para mostrarlo en pantalla
}
