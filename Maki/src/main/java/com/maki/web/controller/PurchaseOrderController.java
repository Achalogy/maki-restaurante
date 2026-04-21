package com.maki.web.controller;
import com.maki.web.entities.Additional;
import com.maki.web.entities.AdditionalOrderDetails;
import com.maki.web.entities.OrderDetails;
import com.maki.web.entities.Plate;
import com.maki.web.entities.PurchaseOrder;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maki.web.service.AdditionalOrderDetailsService;
import com.maki.web.service.ClientService;
import com.maki.web.service.OrderDetailsService;
import com.maki.web.service.PurchaseOrderService;

import jakarta.transaction.Transactional;

@RestController
@RequestMapping("/api/v1/purchase-order")

public class PurchaseOrderController {
    @Autowired
    private PurchaseOrderService purchaseOrderService;
    @Autowired
    private OrderDetailsService orderDetailsService;
    @Autowired
    private AdditionalOrderDetailsService additionalOrderDetailsService;
    @Autowired
    private ClientService clientService;

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

    public static class PlateWithAdditionals {
        public OrderDetails detail;
        public List<Additional> additionals;

        // IMPORTANTE: Necesitas un constructor vacío para Jackson
        public PlateWithAdditionals() {
        }
    }

    @Transactional
    @PostMapping("/client/{id}")
    public ResponseEntity<PurchaseOrder> createPurchaseOrder(@PathVariable Long id, @RequestBody List<PlateWithAdditionals> plates) {
        try {
             PurchaseOrder order = purchaseOrderService.insert(
                    new PurchaseOrder(
                            clientService.selectById(id)));

            for (PlateWithAdditionals plate : plates) {
                OrderDetails detail = orderDetailsService.insert(
                        new OrderDetails(
                                order,
                                plate.detail.getPlate(),
                                plate.detail.getQuantity()));

                for (Additional additional : plate.additionals) {
                    additionalOrderDetailsService
                            .insert(
                                    new AdditionalOrderDetails(
                                            detail,
                                            additional));

                }
            }

            return new ResponseEntity<>(order, HttpStatus.OK);
        }catch(Exception err) {
            err.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== UPDATE PLATE =====================


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
