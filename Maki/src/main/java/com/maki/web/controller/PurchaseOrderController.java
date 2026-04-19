package com.maki.web.controller;
import com.maki.web.entities.PurchaseOrder;
import com.maki.web.entities.Plate;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.OrderDetails;
import com.maki.web.service.CategoryService;
import com.maki.web.service.ClientService;
import com.maki.web.service.PlateService;
import com.maki.web.service.PurchaseOrderService;

@RestController
@RequestMapping("/api/v1/purchase-order")

public class PurchaseOrderController {
     @Autowired
    private PurchaseOrderService purchaseOrderService;

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<PurchaseOrder> getAllPlates() {
        return purchaseOrderService.selectAll();
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getPlateById(@PathVariable Long id) {
        try {
            PurchaseOrder plato = purchaseOrderService.selectById(id);
            if (plato == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(plato, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ===================== CREATE / EDIT ORDER (UPSERT) ===================== do this two pls, this goes with OrderDetails and i do n o t know if its even placed here
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
