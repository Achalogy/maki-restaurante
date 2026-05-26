package com.maki.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClientDTO {
    private Long id;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String address;
    // NO incluye password — nunca se envía al frontend
}
