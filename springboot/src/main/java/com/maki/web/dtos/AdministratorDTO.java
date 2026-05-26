package com.maki.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

/** DTO de Administrador — nunca expone la contraseña */
@Data
@NoArgsConstructor
public class AdministratorDTO {
    private Long id;
    private String name;
    private String username;
}
