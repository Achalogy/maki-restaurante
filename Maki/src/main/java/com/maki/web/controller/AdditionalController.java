package com.maki.web.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.maki.web.entities.Additional;
import com.maki.web.entities.AdditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.AdditionalCategoryService;
import com.maki.web.service.AdditionalService;

@RequestMapping("/api/v1/additional")
@RestController

public class AdditionalController {

    @Autowired
    private AdditionalService additionalService;
    @Autowired
    private AdditionalCategoryService additionalCategoryService;

    // Da todos los adicionales, pero podemos pedir solo los
    // que tengan un categoryId o un additionalId
    @GetMapping("")
    public List<Additional> getAllAdicionales(@RequestParam(required=false) Long categoryId) {
       if (categoryId != null) {
            // Filtrar por categoria
            List<AdditionalCategory> intermedias = additionalCategoryService.findByCategory_Id(categoryId);
            return intermedias.stream().map( i -> i.getAdditional()).collect(Collectors.toList());
        }

        // En este caso queremos todos los adicionales
        return additionalService.selectAll();
    }

    @GetMapping("/categories")
    public List<AdditionalCategory> getAllAdditionalCategories(@RequestParam(required=false) Long categoryId,
            @RequestParam(required = false) Long additionalId) {
        if(categoryId == null && additionalId == null) {
            return additionalCategoryService.selectAll();
        } else if( categoryId != null) {
            return additionalCategoryService.findByCategory_Id(categoryId);
        } else {
            return additionalCategoryService.findByAdditional_Id(additionalId);
        }
    }

    // ===================== ADD ADITIONAL =====================

    @PostMapping("")
    public ResponseEntity<Additional> createAdditional(@RequestBody(required = false) Additional data) {
        try {
            return new ResponseEntity<>(additionalService.insert(data), HttpStatus.OK);
        } catch(Exception e) {
            if(e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/categories")
    public List<AdditionalCategory> setCategories(@PathVariable Long id, @RequestBody List<Category> categories) {
        return additionalCategoryService.setCategories(id, categories);
    }


    // ===================== DELETE ADITIONAL =====================

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
    public ResponseEntity<Additional> getAdditionalById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(additionalService.selectById(id), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Additional> updateAdicional(@PathVariable Long id,
            @RequestBody(required = false) Additional data) {
        try {
            Additional updateData = additionalService.selectById(id);

            if (data.getName() != null) {
                updateData.setName(data.getName());
            }

            updateData.setPrice(data.getPrice());

            return new ResponseEntity<>(additionalService.update(updateData), HttpStatus.OK);

        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
