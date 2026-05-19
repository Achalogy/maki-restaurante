package com.maki.web.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class PurchaseOrderDTO {
    private Long id;
    private LocalDateTime creation_date;
    private LocalDateTime delivery_date;
    private String status;

    // Solo IDs y nombres básicos — sin anidar objetos completos
    private Long clientId;
    private String clientName;

    private Long deliveryId;
    private String deliveryName;

    private Long operatorId;
    private String operatorName;
}
