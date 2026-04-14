package com.maki.web.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;
import com.maki.web.service.AdicionalCategoriaService;
import com.maki.web.service.CategoriaService;
import com.maki.web.service.PlatoService;

@Controller
@RequestMapping("/api/v1/plate")
public class PlatosController {

    @Autowired
    private PlatoService platoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private AdicionalCategoriaService adicionalCategoriaService;

    // ===================== GET ALL =====================
    @GetMapping("")
    @ResponseBody
    public List<Plato> getAllPlates() {
        return platoService.selectAll();
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Plato> getPlateById(@PathVariable Long id) {
        try {
            Plato plato = platoService.selectById(id);
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
    @ResponseBody
    public ResponseEntity<Plato> savePlate(@RequestBody Plato plato, @RequestParam Long categoriaId) {
        try {
            Categoria categoria = categoriaService.selectById(categoriaId);
            if (categoria == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            plato.setCategoria(categoria);
            // El servicio usa .insert() que internamente gestiona si es nuevo o update
            return new ResponseEntity<>(platoService.insert(plato), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE PLATE =====================
    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deletePlate(@PathVariable Long id) {
        try {
            platoService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== ASSIGN/UPDATE CATEGORY =====================
    @PostMapping("/{platoId}/category/{categoriaId}")
    @ResponseBody
    public ResponseEntity<Boolean> updatePlateCategory(
            @PathVariable Long platoId,
            @PathVariable Long categoriaId) {
        try {
            Categoria categoria = categoriaService.selectById(categoriaId);
            if (categoria == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            platoService.cambiarCategoria(categoria, platoId);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}