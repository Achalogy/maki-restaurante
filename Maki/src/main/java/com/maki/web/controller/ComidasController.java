package com.maki.web.controller;

import com.maki.web.service.categoriaService;
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
    private categoriaService categoriaService;

    // ===================== ADMIN TABLE =====================

    @GetMapping("/AdminTable")
    public String mostrarMenuEnTarjetas(Model model) {
        model.addAttribute("menu", platoService.selectAll());
        model.addAttribute("categorias", categoriaService.selectAll());
        return "menu-table";
    }

    // ===================== VIEW SINGLE =====================

    @GetMapping("/{id}")
    public String mostrarMenuEnTabla(Model model, @PathVariable("id") Integer plateid) {
        Plato plato = platoService.selectById(plateid);
        model.addAttribute("plato", plato);
        return "single-item";
    }

    // ===================== PUBLIC MENU =====================

    @GetMapping("/OurMenu")
    public String mostrarMenuPublico(Model model) {
        model.addAttribute("menu", platoService.selectAll());
        return "menu-cards";
    }

    // ===================== DELETE PLATE =====================

    @PostMapping("/deleteMenuItem/{id}")
    public String deletePlato(@PathVariable Integer id) {
        platoService.deleteByID(id);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== CREATE PLATE =====================

    @GetMapping("/addMenuItem")
    public String mostrarFormularioCrearPlato(Model model) {

        Categoria none = categoriaService.selectById(1); // get None safely here
        Plato plato = new Plato(null, "", 0.0, "", "", false, none);

        model.addAttribute("plato", plato);
        model.addAttribute("categorias", categoriaService.selectAll());

        return "crear-plato";
    }

    @PostMapping("/addMenuItem")
    public String agregarPlato(@ModelAttribute("plato") Plato plato, @RequestParam("categoriaId") Integer categoriaId) {
        Categoria categoria = categoriaService.selectById(categoriaId);
        plato.setCategoria(categoria);
        platoService.upsert(plato);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== EDIT PLATE =====================

    @GetMapping("/updateMenuItem/{id}")
    public String mostrarFormularioEditarPlato(@PathVariable("id") Integer id, Model model) {
        Plato plato = platoService.selectById(id);
        model.addAttribute("plato", plato);
        model.addAttribute("categorias", categoriaService.selectAll());
        return "crear-plato";
    }

    // ===================== UPDATE CATEGORY FROM DROPDOWN =====================

    @PostMapping("/updateCategoria")
    public String updateCategoria(
            @RequestParam Integer platoId,
            @RequestParam Integer categoriaId) {

        Categoria categoria = categoriaService.selectById(categoriaId);
        platoService.cambiarCategoria(categoria, platoId);
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== ADD CATEGORY =====================

    @PostMapping("/categorias/add")
    public String addCategoria(@RequestParam String nombre) {

        categoriaService.insert(new Categoria(null, nombre));
        return "redirect:/Comidas/AdminTable";
    }

    // ===================== DELETE CATEGORY =====================

    @PostMapping("/categorias/delete/{id}")
    public String deleteCategoria(@PathVariable Integer id) {

        categoriaService.deleteByID(id);
        return "redirect:/Comidas/AdminTable";
    }

}