package com.maki.web.controller;

import com.maki.web.dtos.AdditionalDTO;
import com.maki.web.dtos.MakiMapper;
import com.maki.web.entities.Additional;
import com.maki.web.entities.AdditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.AdditionalCategoryService;
import com.maki.web.service.AdditionalService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api/v1/additional")
@RestController
public class AdditionalController {

    @Autowired private AdditionalService additionalService;

    @Autowired private AdditionalCategoryService additionalCategoryService;

    @Autowired private MakiMapper mapper;

    // GET todos — retorna DTOs (sin categorías anidadas)
    @GetMapping("")
    public List<AdditionalDTO> getAllAdicionales(@RequestParam(required = false) Long categoryId) {
        List<Additional> additionals;
        if (categoryId != null) {
            additionals =
                    additionalCategoryService.findByCategory_Id(categoryId).stream()
                            .map(AdditionalCategory::getAdditional)
                            .collect(Collectors.toList());
        } else {
            additionals = additionalService.selectAll();
        }
        return additionals.stream().map(mapper::toAdditionalDTO).collect(Collectors.toList());
    }

    // Las categorías siguen retornando la estructura completa (se necesita para la vista de
    // edición)
    @GetMapping("/categories")
    public List<AdditionalCategory> getAllAdditionalCategories(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) Long additionalId) {
        if (categoryId == null && additionalId == null)
            return additionalCategoryService.selectAll();
        else if (categoryId != null) return additionalCategoryService.findByCategory_Id(categoryId);
        else return additionalCategoryService.findByAdditional_Id(additionalId);
    }

    @PostMapping("")
    public ResponseEntity<AdditionalDTO> createAdditional(
            @RequestBody(required = false) Additional data) {
        try {
            return new ResponseEntity<>(
                    mapper.toAdditionalDTO(additionalService.insert(data)), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/categories")
    public List<AdditionalCategory> setCategories(
            @PathVariable Long id, @RequestBody List<Category> categories) {
        return additionalCategoryService.setCategories(id, categories);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAdditional(@PathVariable Long id) {
        try {
            additionalService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<AdditionalDTO> getAdditionalById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(
                    mapper.toAdditionalDTO(additionalService.selectById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<AdditionalDTO> updateAdicional(
            @PathVariable Long id, @RequestBody(required = false) Additional data) {
        try {
            Additional updateData = additionalService.selectById(id);
            if (data.getName() != null) updateData.setName(data.getName());
            updateData.setPrice(data.getPrice());
            return new ResponseEntity<>(
                    mapper.toAdditionalDTO(additionalService.update(updateData)), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
