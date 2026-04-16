package com.maki.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.Category;
import com.maki.web.entities.Plate;
import com.maki.web.service.CategoriaService;
import com.maki.web.service.PlatoService;

@RestController
@RequestMapping("/api/v1/plate")

public class PlatosController {

    @Autowired
    private PlatoService platoService;

    @Autowired
    private CategoriaService categoryService;

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<Plate> getAllPlates() {
        return platoService.selectAll();
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<Plate> getPlateById(@PathVariable Long id) {
        try {
            Plate plato = platoService.selectById(id);
            if (plato == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(plato, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ===================== CREATE / EDIT PLATE (UPSERT) =====================
    @PostMapping("")
    public ResponseEntity<Plate> savePlate(@RequestBody Plate plato, @RequestParam Long categoryId) {
        try {
            Category category = categoryService.selectById(categoryId);
            if (category == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            plato.setCategory(category);
            // El servicio usa .insert() que internamente gestiona si es nuevo o update
            return new ResponseEntity<>(platoService.insert(plato), HttpStatus.OK);
        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== UPDATE PLATE =====================
    @PostMapping("/{id}")
    public ResponseEntity<Plate> updatePlate(@PathVariable Long id, @RequestBody(required = false) Plate data,
            @RequestParam(required = false) Long categoryId) {
        try {
            // 1. Buscamos el plato existente
            Plate updateData = platoService.selectById(id);
            if (updateData == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // 2. Actualizamos campos básicos si vienen en el body
            if (data.getName() != null)
                updateData.setName(data.getName());
            
            updateData.setPrice(data.getPrice());
            updateData.setAvailable(data.isAvailable());

            if (data.getDescription() != null)
                updateData.setDescription(data.getDescription());
            if (data.getUrlImage() != null)
                updateData.setUrlImage(data.getUrlImage());

            // 3. Actualizamos la categoría solo si se envía un nuevo categoryId
            if (categoryId != null) {
                Category category = categoryService.selectById(categoryId);
                if (category != null) {
                    updateData.setCategory(category);
                }
            }

            // 4. Guardamos los cambios
            return new ResponseEntity<>(platoService.update(updateData), HttpStatus.OK);

        } catch (Exception e) {
            
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE PLATE =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePlate(@PathVariable Long id) {
        try {
            platoService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== ASSIGN/UPDATE CATEGORY =====================
    @PostMapping("/{platoId}/category/{categoryId}")
    public ResponseEntity<Boolean> updatePlateCategory(
            @PathVariable Long platoId,
            @PathVariable Long categoryId) {
        try {
            Category category = categoryService.selectById(categoryId);
            if (category == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            platoService.cambiarCategoria(category, platoId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}