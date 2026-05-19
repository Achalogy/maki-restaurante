package com.maki.web.controller;

import com.maki.web.dtos.MakiMapper;
import com.maki.web.dtos.PurchaseOrderDTO;
import com.maki.web.entities.Delivery;
import com.maki.web.entities.PlateWithAdditionals;
import com.maki.web.entities.PurchaseOrder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maki.web.service.DeliveryService;
import com.maki.web.service.PurchaseOrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/purchase-order")
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private DeliveryService deliveryService;

    @Autowired
    private MakiMapper mapper;

    // GET todos — retorna lista de DTOs (sin objetos anidados pesados)
    @GetMapping("")
    public List<PurchaseOrderDTO> getAllPurchaseOrders(@RequestParam(required = false) Optional<Long> notCompleted) {
        List<PurchaseOrder> orders;
        if (notCompleted.isPresent() && notCompleted.get() == 1) {
            orders = purchaseOrderService.selectNotCompleted();
        } else {
            orders = purchaseOrderService.selectAll();
        }
        return orders.stream().map(mapper::toPurchaseOrderDTO).collect(Collectors.toList());
    }

    // GET por id — retorna la entidad completa para la vista de detalle
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurcharseOrderById(@PathVariable Long id) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.selectById(id);
            if (purchaseOrder == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Transactional
    @PostMapping("/client/{id}")
    public ResponseEntity<PurchaseOrderDTO> createPurchaseOrder(@PathVariable Long id,
            @RequestBody List<PlateWithAdditionals> plates) {
        try {
            PurchaseOrder order = purchaseOrderService.createPurchaseOrderFromcart(id, plates);
            return new ResponseEntity<>(mapper.toPurchaseOrderDTO(order), HttpStatus.OK);
        } catch (Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<PurchaseOrderDTO> updatePurchaseOrder(@PathVariable Long id,
            @RequestBody(required = false) PurchaseOrder data) {
        try {
            PurchaseOrder updateData = purchaseOrderService.selectById(id);
            if (updateData == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            Delivery newDelivery = data.getDelivery();
            Delivery oldDelivery = updateData.getDelivery();

            if (newDelivery != null) {
                newDelivery.setBusy(true);
                if (data.getStatus().equals("cancelled") || data.getStatus().equals("completed")) {
                    newDelivery.setBusy(false);
                    deliveryService.update(newDelivery);
                }
                if (oldDelivery != null) {
                    oldDelivery.setBusy(false);
                    deliveryService.update(oldDelivery);
                }
                updateData.setDelivery(newDelivery);
                deliveryService.update(newDelivery);
            }

            if (data.getStatus() != null) {
                if (!updateData.getStatus().equals("sent") && data.getStatus().equals("sent")) {
                    updateData.setDelivery(null);
                    if (oldDelivery != null) {
                        oldDelivery.setBusy(false);
                        deliveryService.update(oldDelivery);
                    }
                }
                updateData.setStatus(data.getStatus());
            }

            if (data.getDelivery_date() != null) updateData.setDelivery_date(data.getDelivery_date());

            return new ResponseEntity<>(mapper.toPurchaseOrderDTO(purchaseOrderService.update(updateData)),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOrder(@PathVariable Long id) {
        try {
            purchaseOrderService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}