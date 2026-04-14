package com.maki.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.Operator;
import com.maki.web.service.OperadorService;

@Controller
@RequestMapping("/api/v1/operator")
public class OperatorController {

    @Autowired
    private OperadorService OperatorService;

    // ===================== GET ALL =====================
    @GetMapping("")
    @ResponseBody
    public List<Operator> getAllOperators() {
        return OperatorService.selectAll();
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    @ResponseBody
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

    // ===================== CREATE / EDIT (UPSERT) =====================
    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Operator> saveOperator(@RequestBody Operator Operator) {
        try {
            // El servicio insert suele manejar el guardado o actualización
            return new ResponseEntity<>(OperatorService.insert(Operator), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteOperator(@PathVariable Long id) {
        try {
            OperatorService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}