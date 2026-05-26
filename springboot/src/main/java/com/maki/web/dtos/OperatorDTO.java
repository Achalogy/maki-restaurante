package com.maki.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OperatorDTO {
    private Long id;
    private String name;
    private String username;
    // NO incluye password
}
