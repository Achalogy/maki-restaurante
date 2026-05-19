package com.maki.web.controller;

import com.maki.web.entities.Operator;
import com.maki.web.entities.UserEntity;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.security.CustomUserDetailService;
import com.maki.web.security.JWTGenerator;
import com.maki.web.service.OperatorService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/operator")
public class OperatorController {

  @Autowired
  private OperatorService operatorService;
  
  @Autowired
  private CustomUserDetailService customUserDetailService;

  @Autowired
  AuthenticationManager authenticationManager;
  @Autowired
  JWTGenerator jwtGenerator;

  // ===================== GET ALL =====================
  @GetMapping("")
  public List<Operator> getAllOperators() {
    return operatorService.selectAll();
  }

  // ===================== GET BY ID =====================
  @GetMapping("/{id}")
  public ResponseEntity<Operator> getOperatorById(@PathVariable Long id) {
    try {
      Operator operator = operatorService.selectById(id);
      if (operator == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
      return new ResponseEntity<>(operator, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
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

  // ===================== UPDATE =====================
  @PostMapping("/{id}")
  public ResponseEntity<Operator> updateOperator(
    @PathVariable Long id,
    @RequestBody(required = false) Operator data
  ) {
    try {
      Operator updateData = operatorService.selectById(id);

      if (data.getName() != null) updateData.setName(data.getName());
      if (data.getUsername() != null) updateData.setUsername(
        data.getUsername()
      );
      if (data.getPassword() != null) updateData.setPassword(
        data.getPassword()
      );

      return new ResponseEntity<>(
        operatorService.update(updateData),
        HttpStatus.OK
      );
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

  // ===================== GET BY USERNAME =====================
  @GetMapping("/username/{username}")
  public ResponseEntity<Operator> getOperatorByUsername(
    @PathVariable String username
  ) {
    try {
      return new ResponseEntity<>(
        operatorService.selectByUsername(username),
        HttpStatus.OK
      );
    } catch (EntityNotFoundException e) {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // ===================== SEARCH =====================
  @GetMapping("/search")
  public ResponseEntity<List<Operator>> searchOperators(
    @RequestParam("term") String term
  ) {
    return new ResponseEntity<>(
      operatorService.searchByNameOrUsername(term),
      HttpStatus.OK
    );
  }

  // ===================== COUNT BY USERNAME =====================
  @GetMapping("/count/{username}")
  public ResponseEntity<Long> countOperatorsByUsername(
    @PathVariable String username
  ) {
    return new ResponseEntity<>(
      operatorService.countByUsername(username),
      HttpStatus.OK
    );
  }

  // ===================== LIST ORDERED =====================
  @GetMapping("/ordered")
  public ResponseEntity<List<Operator>> getOperatorsOrderedByName() {
    return new ResponseEntity<>(
      operatorService.selectAllOrderedByName(),
      HttpStatus.OK
    );
  }

    @PostMapping("/log-in")
    public ResponseEntity<String> loginClient(@RequestBody(required = false) Operator operator) {        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(operator.getUsername(), operator.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);

            return new ResponseEntity<String>(token, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<String>("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
        }

    }
}
