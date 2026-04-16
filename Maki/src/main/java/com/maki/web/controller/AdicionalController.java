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

import com.maki.web.entities.Aditional;
import com.maki.web.entities.AditionalCategory;
import com.maki.web.entities.Category;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.AdicionalCategoriaService;
import com.maki.web.service.AdicionalService;

@RequestMapping("/api/v1/aditional")
@RestController

public class AdicionalController {
  
    @Autowired
    private AdicionalService adicionalService;
    @Autowired
    private AdicionalCategoriaService adicionalCategoryService;

    @GetMapping("")
    public List<Aditional> getAllAdicionales(@RequestParam(required=false) Long categoryId, 
            @RequestParam(required = false) Long aditionalId) {
        if(categoryId == null && aditionalId == null) {
            return adicionalService.selectAll();
        } else if (categoryId != null) {
            List<AditionalCategory> intermedias = adicionalCategoryService.findByCategory_Id(categoryId);
            return intermedias.stream().map( i -> i.getAditional()).collect(Collectors.toList());
        }else {
            List<AditionalCategory> intermedias = adicionalCategoryService.findByAditional_Id(aditionalId);
            return intermedias.stream().map( i -> i.getAditional()).collect(Collectors.toList());
        }

    }

    @GetMapping("/categories")
    public List<AditionalCategory> getAllAditionalCategories(@RequestParam(required=false) Long categoryId, 
            @RequestParam(required = false) Long aditionalId) {
        if(categoryId == null && aditionalId == null) {
            return adicionalCategoryService.selectAll();
        } else if( categoryId != null) {
            return adicionalCategoryService.findByCategory_Id(categoryId);
        } else {
            return adicionalCategoryService.findByAditional_Id(aditionalId);
        }
    }

    // ===================== ADD ADITIONAL =====================

    @PostMapping("")
    public ResponseEntity<Aditional> createAditional(@RequestBody(required = false) Aditional data) {
        try {
            return new ResponseEntity<>(adicionalService.insert(data), HttpStatus.OK);
        } catch(Exception e) {
            if(e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{id}/categories")
    public List<AditionalCategory> setCategories(@PathVariable Long id, @RequestBody List<Category> categories) {
        return adicionalCategoryService.setCategorias(id, categories);
    }
    

    // ===================== DELETE ADITIONAL =====================

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAditional(@PathVariable Long id) {
        try {
            adicionalService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Aditional> getAditionalById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(adicionalService.selectById(id), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Aditional> updateAdicional(@PathVariable Long id,
            @RequestBody(required = false) Aditional data) {
        try {
            Aditional updateData = adicionalService.selectById(id);

            if (data.getName() != null) {
                updateData.setName(data.getName());
            }

            updateData.setPrice(data.getPrice());

            return new ResponseEntity<>(adicionalService.update(updateData), HttpStatus.OK);

        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
