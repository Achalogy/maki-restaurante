package com.maki.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Cliente;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.CategoriaService;

@Controller
@RequestMapping("/api/v1/category")
@CrossOrigin("http://localhost:4200")
public class CategoryController {
  
    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("")
    @ResponseBody
    public List<Categoria> getAllCategories() {
        return categoriaService.selectAll();
    }

    // ===================== ADD CATEGORY =====================

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Categoria> createCategory(@RequestBody(required = false) Categoria data) {
        try {
            return new ResponseEntity<>(categoriaService.insert(data), HttpStatus.OK);
        } catch(Exception e) {
            if(e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE CATEGORY =====================

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteCategory(@PathVariable Long id) {
        try {
            categoriaService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Categoria> getCategoryById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(categoriaService.selectById(id), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
