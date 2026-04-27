package com.maki.web.controller;
import com.maki.web.entities.PlateWithAdditionals;
import com.maki.web.entities.PurchaseOrder;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maki.web.service.PurchaseOrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/purchase-order")

public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderService.selectAll();
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

            if (data.getDelivery() != null)
                updateData.setDelivery(
                        data.getDelivery());

            if(data.getStatus() != null) {
                if (!updateData.getStatus().equals("sent") && data.getStatus().equals("sent")) {
                    // Solo si cambia a enviado y no se esta actualizando ya el delivery
                    updateData.setDelivery(null);
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
