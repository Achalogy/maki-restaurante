package com.maki.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.Operator;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.OperatorService;

@RestController
@RequestMapping("/api/v1/operator")

public class OperatorController {

    @Autowired
    private OperatorService OperatorService;

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<Operator> getAllOperators() {
        return OperatorService.selectAll();
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<Operator> getOperatorById(@PathVariable Long id) {
        try {
            Operator Operator = OperatorService.selectById(id);
            if (Operator == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(Operator, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ===================== CREATE =====================
    @PostMapping("")
    public ResponseEntity<Operator> saveOperator(@RequestBody Operator Operator) {
        try {
            // El servicio insert suele manejar el guardado o actualización
            return new ResponseEntity<>(OperatorService.insert(Operator), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== Update =====================
    @PostMapping("/{id}")
    public ResponseEntity<Operator> updateOperator(@PathVariable Long id,
            @RequestBody(required = false) Operator data) {
        try {
            // Buscamos el operador existente por ID
            Operator updateData = OperatorService.selectById(id);

            // Validamos y actualizamos solo los campos presentes en el request
            if (data.getName() != null)
                updateData.setName(data.getName());
            if (data.getUsername() != null)
                updateData.setUsername(data.getUsername());
            if (data.getPassword() != null)
                updateData.setPassword(data.getPassword());

            // Guardamos los cambios usando el servicio
            return new ResponseEntity<>(OperatorService.update(updateData), HttpStatus.OK);

        } catch (Exception e) {
            // Si no se encuentra el registro
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Error genérico de solicitud
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOperator(@PathVariable Long id) {
        try {
            OperatorService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/log-in")
    public ResponseEntity<Operator> loginOperator(@RequestBody(required = false) Operator data) {
        try {
            return new ResponseEntity<>(OperatorService.verifyCredentials(
                    data.getUsername(),
                    data.getPassword()), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}