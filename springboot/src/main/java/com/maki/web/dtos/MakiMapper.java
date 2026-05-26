package com.maki.web.dtos;

import com.maki.web.entities.*;
import org.springframework.stereotype.Component;

/**
 * Clase utilitaria que convierte entidades JPA a DTOs. Evita exponer información sensible
 * (passwords, relaciones anidadas).
 */
@Component
public class MakiMapper {

    public PlateDTO toPlateDTO(Plate plate) {
        if (plate == null) return null;
        PlateDTO dto = new PlateDTO();
        dto.setId(plate.getId());
        dto.setName(plate.getName());
        dto.setPrice(plate.getPrice());
        dto.setDescription(plate.getDescription());
        dto.setUrlImage(plate.getUrlImage());
        dto.setAvailable(plate.isAvailable());
        if (plate.getCategory() != null) {
            dto.setCategoryName(plate.getCategory().getName());
        }
        return dto;
    }

    public ClientDTO toClientDTO(Client client) {
        if (client == null) return null;
        ClientDTO dto = new ClientDTO();
        dto.setId(client.getId());
        dto.setName(client.getName());
        dto.setSurname(client.getSurname());
        dto.setEmail(client.getEmail());
        dto.setPhone(client.getPhone());
        dto.setAddress(client.getAddress());
        // password se omite intencionalmente
        return dto;
    }

    public OperatorDTO toOperatorDTO(Operator operator) {
        if (operator == null) return null;
        OperatorDTO dto = new OperatorDTO();
        dto.setId(operator.getId());
        dto.setName(operator.getName());
        dto.setUsername(operator.getUsername());
        // password se omite intencionalmente
        return dto;
    }

    public PurchaseOrderDTO toPurchaseOrderDTO(PurchaseOrder order) {
        if (order == null) return null;
        PurchaseOrderDTO dto = new PurchaseOrderDTO();
        dto.setId(order.getId());
        dto.setCreation_date(order.getCreation_date());
        dto.setDelivery_date(order.getDelivery_date());
        dto.setStatus(order.getStatus());

        if (order.getClient() != null) {
            dto.setClientId(order.getClient().getId());
            dto.setClientName(order.getClient().getName() + " " + order.getClient().getSurname());
        }
        if (order.getDelivery() != null) {
            dto.setDeliveryId(order.getDelivery().getId());
            dto.setDeliveryName(order.getDelivery().getName());
        }
        if (order.getOperator() != null) {
            dto.setOperatorId(order.getOperator().getId());
            dto.setOperatorName(order.getOperator().getName());
        }
        return dto;
    }

    public AdditionalDTO toAdditionalDTO(Additional additional) {
        if (additional == null) return null;
        AdditionalDTO dto = new AdditionalDTO();
        dto.setId(additional.getId());
        dto.setName(additional.getName());
        dto.setPrice(additional.getPrice());
        return dto;
    }
}
