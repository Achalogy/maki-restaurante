package com.maki.web.controller;

import com.maki.web.entities.Delivery;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.DeliveryService;

import java.lang.foreign.Linker.Option;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * DeliveryController
 * Expone los endpoints REST para la gestión de domiciliarios.
 * Base path: /api/v1/delivery
 */
@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    @Autowired
    private DeliveryService deliveryService;

    // ===================== GET ALL =====================

    /** Retorna la lista completa de domiciliarios */
    @GetMapping("")
    public List<Delivery> getAllDeliveries(@RequestParam(required = false) Optional<Long> active) {
        if(active.isPresent() && active.get() == 1) {
            return deliveryService.selectAllActive();
        }
        
        return deliveryService.selectAll();
    }

    // ===================== GET BY ID =====================

    /** Retorna un domiciliario por su ID */
    @GetMapping("/{id}")
    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(deliveryService.selectById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ===================== CREATE =====================

    /** Crea un nuevo domiciliario */
    @PostMapping("")
    public ResponseEntity<Delivery> createDelivery(@RequestBody(required = false) Delivery data) {
        try {
            return new ResponseEntity<>(deliveryService.insert(data), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== UPDATE =====================

    /**
     * Actualiza un domiciliario existente.
     * Solo actualiza los campos que vienen en el body (patch parcial).
     * Útil para el toggle de disponibilidad (solo envía available).
     */
    @PostMapping("/{id}")
    public ResponseEntity<Delivery> updateDelivery(
            @PathVariable Long id,
            @RequestBody(required = false) Delivery data) {
        try {
            Delivery existing = deliveryService.selectById(id);

            // Actualizar solo los campos presentes en el request
            if (data.getName() != null)
                existing.setName(data.getName());
            if (data.getPhone() != null)
                existing.setPhone(data.getPhone());
            if (data.getNational_id() != null)
                existing.setNational_id(data.getNational_id());

            // available es primitivo boolean, siempre se actualiza si llega en el body
            existing.setAvailable(data.isAvailable());

            return new ResponseEntity<>(deliveryService.update(existing), HttpStatus.OK);

        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE =====================

    /** Elimina un domiciliario por su ID */
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteDelivery(@PathVariable Long id) {
        try {
            deliveryService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
