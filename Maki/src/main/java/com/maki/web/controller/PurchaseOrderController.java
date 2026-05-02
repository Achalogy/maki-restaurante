package com.maki.web.controller;
import com.maki.web.entities.Delivery;
import com.maki.web.entities.PlateWithAdditionals;
import com.maki.web.entities.PurchaseOrder;

import java.util.List;
import java.util.Optional;

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

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<PurchaseOrder> getAllPurchaseOrders(@RequestParam(required = false) Optional<Long> notCompleted) {
        if(notCompleted.isPresent() && notCompleted.get() == 1) {
            System.out.println("AGUACATE");
            return purchaseOrderService.selectNotCompleted();
        }else {
            return purchaseOrderService.selectAll();
        }
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPurcharseOrderById(@PathVariable Long id) {
        try {
            PurchaseOrder purchaseOrder = purchaseOrderService.selectById(id);
            if (purchaseOrder == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(purchaseOrder, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    // ===================== CREATE / EDIT ORDER (UPSERT) =====================

    @Transactional
    @PostMapping("/client/{id}")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@PathVariable Long id, @RequestBody List<PlateWithAdditionals> plates) {
        try {
             PurchaseOrder order = purchaseOrderService.createPurchaseOrderFromcart(id, plates);

            return new ResponseEntity<>(order, HttpStatus.OK);
        }catch(Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== UPDATE PLATE =====================

    @PostMapping("/{id}")
    public ResponseEntity<PurchaseOrder> updatePurchaseOrder(@PathVariable Long id,
            @RequestBody(required= false) PurchaseOrder data) {
        try {
            PurchaseOrder updateData = purchaseOrderService.selectById(id);
            if(updateData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Delivery newDelivery = data.getDelivery();
            Delivery oldDelivery = updateData.getDelivery();

            if (newDelivery != null){
                newDelivery.setBusy(true);

                if (data.getStatus().equals("cancelled") || data.getStatus().equals("completed")) {
                    newDelivery.setBusy(false);
                    deliveryService.update(newDelivery);
                }

                if(oldDelivery != null) {
                    oldDelivery.setBusy(false);
                    deliveryService.update(oldDelivery);
                }

                updateData.setDelivery(
                    newDelivery
                );

                deliveryService.update(
                    newDelivery
                );
            }

            if(data.getStatus() != null) {
                if (!updateData.getStatus().equals("sent") && data.getStatus().equals("sent")) {
                    // Solo si cambia a enviado y no se esta actualizando ya el delivery
                    updateData.setDelivery(null);
                    if(oldDelivery != null) {
                        oldDelivery.setBusy(false);
                        deliveryService.update(oldDelivery);
                    }
                }

                updateData.setStatus(
                    data.getStatus()
                );
            }

            if(data.getDelivery_date() != null)
                updateData.setDelivery_date(
                    data.getDelivery_date()
                );

            return new ResponseEntity<>(
                purchaseOrderService.update(updateData),
                HttpStatus.OK
            );
        } catch (Exception e) {
            return new ResponseEntity<>(
                HttpStatus.BAD_REQUEST
            );
        }
    }

    // ===================== DELETE Order =====================
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
