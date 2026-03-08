package com.maki.web.controller;

import com.maki.web.service.CategoriaService;
import com.maki.web.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/Comidas")
public class ComidasController {

    @Autowired
    private PlatoService platoService;

    @Autowired
    private CategoriaService categoriaService;

    // ===================== ADMIN TABLE =====================

    @GetMapping("/AdminTable")
    public String mostrarMenuEnTarjetas(Model model) {
        model.addAttribute("menu", platoService.selectAll());
        model.addAttribute("categorias", categoriaService.selectAll());
        return "menu-table";
    }

    // ===================== VIEW SINGLE =====================

    @GetMapping("/{id}")
    public String mostrarMenuEnTabla(Model model, @PathVariable("id") Long plateid) {
        Plato plato = platoService.selectById(plateid);
        model.addAttribute("plato", plato);
        return "single-item";
    }

    // ===================== PUBLIC MENU =====================

    @GetMapping("/OurMenu")
    public String mostrarMenuPublico(Model model) {
        model.addAttribute("menu", platoService.selectAll());
        model.addAttribute("categorias", categoriaService.selectAll());
        return "menu-cards";
    }

    // ===================== DELETE PLATE =====================

    @PostMapping("/deleteMenuItem/{id}")
    public String deletePlato(@PathVariable Long id) {
        platoService.deleteByID(id);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== CREATE PLATE =====================

    @GetMapping("/addMenuItem")
    public String mostrarFormularioCrearPlato(Model model) {
        Plato plato = new Plato("", 0.0, "", "", false);

        model.addAttribute("plato", plato);
        model.addAttribute("categorias", categoriaService.selectAll());

        return "crear-plato";
    }

    @PostMapping("/addMenuItem")
    public String agregarPlato(@ModelAttribute("plato") Plato plato, @RequestParam("categoriaId") Long categoriaId) {
        Categoria categoria = categoriaService.selectById(categoriaId);
        plato.setCategoria(categoria);
        platoService.insert(plato); // Funciona como un upsert
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== EDIT PLATE =====================

    @GetMapping("/updateMenuItem/{id}")
    public String mostrarFormularioEditarPlato(@PathVariable("id") Long id, Model model) {
        Plato plato = platoService.selectById(id);
        model.addAttribute("plato", plato);
        model.addAttribute("categorias", categoriaService.selectAll());
        return "crear-plato";
    }

    // ===================== UPDATE CATEGORY FROM DROPDOWN =====================

    @PostMapping("/updateCategoria")
    public String updateCategoria(
            @RequestParam Long platoId,
            @RequestParam Long categoriaId) {

        Categoria categoria = categoriaService.selectById(categoriaId);
        platoService.cambiarCategoria(categoria, platoId);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== ADD CATEGORY =====================

    @PostMapping("/categorias/add")
    public String addCategoria(@RequestParam String nombre) {

        categoriaService.insert(new Categoria(nombre));
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== DELETE CATEGORY =====================

    @PostMapping("/categorias/delete/{id}")
    public String deleteCategoria(@PathVariable Long id) {

        categoriaService.deleteByID(id);
        return "redirect:/Comidas/AdminTable";
    }

}