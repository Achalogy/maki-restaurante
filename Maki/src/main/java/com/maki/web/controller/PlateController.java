package com.maki.web.controller;

import com.maki.web.dtos.MakiMapper;
import com.maki.web.dtos.PlateDTO;
import com.maki.web.entities.Category;
import com.maki.web.entities.Plate;
import com.maki.web.service.CategoryService;
import com.maki.web.service.PlateService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plate")
public class PlateController {

    @Autowired
    private PlateService platoService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MakiMapper mapper;

    // GET todos — retorna lista de DTOs (solo info necesaria)
    @GetMapping("")
    public List<PlateDTO> getAllPlates() {
        return platoService.selectAll()
                .stream()
                .map(mapper::toPlateDTO)
                .collect(Collectors.toList());
    }

    // GET por id
    @GetMapping("/{id}")
    public ResponseEntity<PlateDTO> getPlateById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(mapper.toPlateDTO(platoService.selectById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // CREATE
    @PostMapping("")
    public ResponseEntity<PlateDTO> savePlate(@RequestBody Plate plato, @RequestParam Long categoryId) {
        try {
            Category category = categoryService.selectById(categoryId);
            if (category == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            plato.setCategory(category);
            return new ResponseEntity<>(mapper.toPlateDTO(platoService.insert(plato)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // UPDATE
    @PostMapping("/{id}")
    public ResponseEntity<PlateDTO> updatePlate(@PathVariable Long id,
            @RequestBody(required = false) Plate data,
            @RequestParam(required = false) Long categoryId) {
        try {
            Plate updateData = platoService.selectById(id);
            if (updateData == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            if (data.getName() != null) updateData.setName(data.getName());
            updateData.setPrice(data.getPrice());
            updateData.setAvailable(data.isAvailable());
            if (data.getDescription() != null) updateData.setDescription(data.getDescription());
            if (data.getUrlImage() != null) updateData.setUrlImage(data.getUrlImage());

            if (categoryId != null) {
                Category category = categoryService.selectById(categoryId);
                if (category != null) updateData.setCategory(category);
            }

            return new ResponseEntity<>(mapper.toPlateDTO(platoService.update(updateData)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePlate(@PathVariable Long id) {
        try {
            platoService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/{platoId}/category/{categoryId}")
    public ResponseEntity<Boolean> updatePlateCategory(@PathVariable Long platoId, @PathVariable Long categoryId) {
        try {
            Category category = categoryService.selectById(categoryId);
            if (category == null) return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            platoService.cambiarCategoria(category, platoId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}