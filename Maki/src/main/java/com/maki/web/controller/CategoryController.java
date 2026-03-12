package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.maki.web.entities.Categoria;
import com.maki.web.service.CategoriaService;

@Controller
@RequestMapping("/category")
public class CategoryController {
  
    @Autowired
    private CategoriaService categoriaService;

    // ===================== ADD CATEGORY =====================

    @PostMapping("/create")
    public String addCategoria(@RequestParam String nombre) {

        categoriaService.insert(new Categoria(nombre));
        return "redirect:/plate/crud";
    }

    // ===================== DELETE CATEGORY =====================

    @PostMapping("/delete/{id}")
    public String deleteCategoria(@PathVariable Long id) {

        categoriaService.deleteByID(id);
        return "redirect:/plate/crud";
    }
}
