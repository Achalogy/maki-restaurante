package com.maki.web.controller;

import com.maki.web.dtos.OperatorDTO;
import com.maki.web.dtos.OperatorMapper;
import com.maki.web.entities.Operator;
import com.maki.web.entities.UserEntity;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.security.CustomUserDetailService;
import com.maki.web.service.OperatorService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operator")
public class OperatorController {

    @Autowired
    private OperatorService operatorService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<OperatorDTO> getAllOperators() {
        return operatorService.selectAll()
                .stream()
                .map(OperatorMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<OperatorDTO> getOperatorById(@PathVariable Long id) {
        try {
            Operator operator = operatorService.selectById(id);
            if (operator == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(OperatorMapper.INSTANCE.toDTO(operator), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ===================== CREATE =====================
    @PostMapping("")
    public ResponseEntity<OperatorDTO> saveOperator(@RequestBody Operator operator) {
        try {
            // 1. Guarda el operador
            Operator savedOperator = operatorService.insert(operator);

            // 2. Crea UserEntity para autenticación JWT
            UserEntity userEntity = customUserDetailService.operatorToUserEntity(savedOperator);
            savedOperator.setUser(userEntity);
            operatorService.update(savedOperator);

            return new ResponseEntity<>(OperatorMapper.INSTANCE.toDTO(savedOperator), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== UPDATE =====================
    @PostMapping("/{id}")
    public ResponseEntity<OperatorDTO> updateOperator(
            @PathVariable Long id,
            @RequestBody(required = false) Operator data) {
        try {
            Operator updateData = operatorService.selectById(id);
            if (data.getName() != null)     updateData.setName(data.getName());
            if (data.getUsername() != null) updateData.setUsername(data.getUsername());
            if (data.getPassword() != null) updateData.setPassword(data.getPassword());

            return new ResponseEntity<>(
                    OperatorMapper.INSTANCE.toDTO(operatorService.update(updateData)),
                    HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteOperator(@PathVariable Long id) {
        try {
            operatorService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<OperatorDTO> getOperatorByUsername(@PathVariable String username) {
        try {
            return new ResponseEntity<>(
                    OperatorMapper.INSTANCE.toDTO(operatorService.selectByUsername(username)),
                    HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<OperatorDTO>> searchOperators(@RequestParam("term") String term) {
        List<OperatorDTO> result = operatorService.searchByNameOrUsername(term)
                .stream()
                .map(OperatorMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/count/{username}")
    public ResponseEntity<Long> countOperatorsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(operatorService.countByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<OperatorDTO>> getOperatorsOrderedByName() {
        List<OperatorDTO> result = operatorService.selectAllOrderedByName()
                .stream()
                .map(OperatorMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    // ===================== LOGIN =====================
    @PostMapping("/log-in")
    public ResponseEntity<OperatorDTO> loginOperator(@RequestBody(required = false) Operator data) {
        try {
            return new ResponseEntity<>(
                    OperatorMapper.INSTANCE.toDTO(
                            operatorService.verifyCredentials(data.getUsername(), data.getPassword())),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}