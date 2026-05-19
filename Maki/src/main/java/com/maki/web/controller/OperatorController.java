package com.maki.web.controller;

import com.maki.web.dtos.MakiMapper;
import com.maki.web.dtos.OperatorDTO;
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
  private MakiMapper mapper;

  // ===================== GET BY ID =====================
  @GetMapping("/{id}")
  public ResponseEntity<OperatorDTO> getOperatorById(@PathVariable Long id) {
    try {
      Operator operator = operatorService.selectById(id);
      if (operator == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return new ResponseEntity<>(mapper.toOperatorDTO(operator), HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

    @GetMapping("")
    public List<OperatorDTO> getAllOperators() {
        return operatorService.selectAll()
                .stream()
                .map(mapper::toOperatorDTO)
                .collect(Collectors.toList());
    }

  // ===================== CREATE =====================
  @PostMapping("")
  public ResponseEntity<Operator> saveOperator(@RequestBody Operator operator) {
    if(operatorService.selectByUsername(operator.getUsername()) != null) {
      return new ResponseEntity<Operator>(operator, HttpStatus.BAD_REQUEST);
    }

    UserEntity userEntity = customUserDetailService.OperatorToUserEntity(operator);
    operator.setUser(userEntity);
    Operator newOperator = operatorService.insert(operator);

    if(newOperator == null)
      return new ResponseEntity<Operator>(newOperator, HttpStatus.BAD_REQUEST);

    return new ResponseEntity<Operator>(operator, HttpStatus.CREATED);
  }


    @PostMapping("/{id}")
    public ResponseEntity<OperatorDTO> updateOperator(@PathVariable Long id,
            @RequestBody(required = false) Operator data) {
        try {
            Operator updateData = operatorService.selectById(id);

            if (data.getName() != null) updateData.setName(data.getName());
            if (data.getUsername() != null) updateData.setUsername(data.getUsername());
            if (data.getPassword() != null) updateData.setPassword(data.getPassword());

            return new ResponseEntity<>(mapper.toOperatorDTO(operatorService.update(updateData)), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

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
            return new ResponseEntity<>(mapper.toOperatorDTO(operatorService.selectByUsername(username)), HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<OperatorDTO>> searchOperators(@RequestParam("term") String term) {
        return new ResponseEntity<>(
                operatorService.searchByNameOrUsername(term)
                        .stream().map(mapper::toOperatorDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }

    @GetMapping("/count/{username}")
    public ResponseEntity<Long> countOperatorsByUsername(@PathVariable String username) {
        return new ResponseEntity<>(operatorService.countByUsername(username), HttpStatus.OK);
    }

    @GetMapping("/ordered")
    public ResponseEntity<List<OperatorDTO>> getOperatorsOrderedByName() {
        return new ResponseEntity<>(
                operatorService.selectAllOrderedByName()
                        .stream().map(mapper::toOperatorDTO).collect(Collectors.toList()),
                HttpStatus.OK);
    }
}
