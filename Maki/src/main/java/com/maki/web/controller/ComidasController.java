package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;
import com.maki.web.repository.CategoriasRepository;
import com.maki.web.service.MenuService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/Comidas")
public class ComidasController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private CategoriasRepository categoriasRepository;

    // ===================== ADMIN TABLE =====================

    @GetMapping("/AdminTable")
    public String mostrarMenuEnTarjetas(Model model) {
        model.addAttribute("menu", menuService.getMenu());
        model.addAttribute("categorias", categoriasRepository.getAll());
        return "menu-table";
    }

    // ===================== VIEW SINGLE =====================

    @GetMapping("/{id}")
    public String mostrarMenuEnTabla(Model model, @PathVariable("id") Integer plateid) {
        Plato plato = menuService.getById(plateid);
        model.addAttribute("plato", plato);
        return "single-item";
    }

    // ===================== PUBLIC MENU =====================

    @GetMapping("/OurMenu")
    public String mostrarMenuPublico(Model model) {
        model.addAttribute("menu", menuService.getMenu());
        return "menu-cards";
    }

    // ===================== DELETE PLATE =====================

    @PostMapping("/deleteMenuItem/{id}")
    public String deletePlato(@PathVariable Integer id) {
        menuService.deletePlato(id);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== CREATE PLATE =====================

    @GetMapping("/addMenuItem")
    public String mostrarFormularioCrearPlato(Model model) {

        Categoria none = categoriasRepository.getById(1); // get None safely here
        Plato plato = new Plato(null, "", 0.0, "", "", false, none);

        model.addAttribute("plato", plato);
        model.addAttribute("categorias", categoriasRepository.getAll());

        return "crear-plato";
    }

    @PostMapping("/addMenuItem")
    public String agregarPlato(@ModelAttribute("plato") Plato plato) {
        menuService.addPlato(plato);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== EDIT PLATE =====================

    @GetMapping("/updateMenuItem/{id}")
    public String mostrarFormularioEditarPlato(@PathVariable("id") Integer id, Model model) {
        Plato plato = menuService.getById(id);
        model.addAttribute("plato", plato);
        model.addAttribute("categorias", categoriasRepository.getAll());
        return "crear-plato";
    }

    // ===================== UPDATE CATEGORY FROM DROPDOWN =====================

    @PostMapping("/updateCategoria")
    public String updateCategoria(
            @RequestParam Integer platoId,
            @RequestParam Integer categoriaId) {

        menuService.updateCategoria(platoId, categoriaId);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== ADD CATEGORY =====================

    @PostMapping("/categorias/add")
    public String addCategoria(@RequestParam String nombre) {

        categoriasRepository.addCategoria(new Categoria(null, nombre));
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== DELETE CATEGORY =====================

    @PostMapping("/categorias/delete/{id}")
    public String deleteCategoria(@PathVariable Integer id) {

        categoriasRepository.deleteCategoria(id);
        return "redirect:/Comidas/AdminTable";
    }

}