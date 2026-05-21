package com.maki.web.controller;

import com.maki.web.dtos.AdministratorDTO;
import com.maki.web.dtos.AdministratorMapper;
import com.maki.web.entities.Administrator;
import com.maki.web.entities.UserEntity;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.security.CustomUserDetailService;
import com.maki.web.service.AdministratorService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
public class AdministratorController {

    @Autowired
    private AdministratorService AdministratorService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<AdministratorDTO> getAllAdministrators() {
        return AdministratorService.selectAll()
                .stream()
                .map(AdministratorMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<AdministratorDTO> getAdministratorById(@PathVariable Long id) {
        try {
            Administrator administrator = AdministratorService.selectById(id);
            if (administrator == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(AdministratorMapper.INSTANCE.toDTO(administrator), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ===================== CREATE =====================
    @PostMapping("")
    public ResponseEntity<AdministratorDTO> saveAdministrator(@RequestBody Administrator administrator) {
        try {
            // 1. Guarda el administrador
            Administrator savedAdmin = AdministratorService.insert(administrator);

            // 2. Crea UserEntity para autenticación JWT
            UserEntity userEntity = customUserDetailService.administratorToUserEntity(savedAdmin);
            savedAdmin.setUser(userEntity);
            AdministratorService.update(savedAdmin);

            return new ResponseEntity<>(AdministratorMapper.INSTANCE.toDTO(savedAdmin), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== UPDATE =====================
    @PostMapping("/{id}")
    public ResponseEntity<AdministratorDTO> updateAdministrator(
            @PathVariable Long id,
            @RequestBody(required = false) Administrator data) {
        try {
            Administrator updateData = AdministratorService.selectById(id);
            if (data.getName() != null)     updateData.setName(data.getName());
            if (data.getUsername() != null) updateData.setUsername(data.getUsername());
            if (data.getPassword() != null) updateData.setPassword(data.getPassword());

            return new ResponseEntity<>(
                    AdministratorMapper.INSTANCE.toDTO(AdministratorService.update(updateData)),
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
    public ResponseEntity<Boolean> deleteAdministrator(@PathVariable Long id) {
        try {
            AdministratorService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== LOGIN =====================
    @PostMapping("/log-in")
    public ResponseEntity<AdministratorDTO> loginAdministrator(
            @RequestBody(required = false) Administrator data) {
        try {
            return new ResponseEntity<>(
                    AdministratorMapper.INSTANCE.toDTO(
                            AdministratorService.verifyCredentials(data.getUsername(), data.getPassword())),
                    HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}