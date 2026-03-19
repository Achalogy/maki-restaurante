package com.maki.web.controller;

import com.maki.web.service.AdicionalCategoriaService;
import com.maki.web.service.CategoriaService;
import com.maki.web.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.maki.web.entities.Categoria;
import com.maki.web.entities.Plato;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/plate")
public class PlatosController {

    @Autowired
    private PlatoService platoService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private AdicionalCategoriaService adicionalCategoriaService;

    // ===================== ADMIN TABLE =====================

    @GetMapping("/crud")
    public String mostrarMenuEnTarjetas(Model model) {
        model.addAttribute("menu", platoService.selectAll());
        model.addAttribute("categorias", categoriaService.selectAll());
        return "pages/plate/crud";
    }

    // ===================== VIEW SINGLE =====================

    @GetMapping("/{id}")
    public String mostrarMenuEnTabla(Model model, @PathVariable("id") Long plateid) {
        Plato plato = platoService.selectById(plateid);
        model.addAttribute("plato", plato);
        model.addAttribute("adicionales", adicionalCategoriaService.findByCategoria_Id(plato.getCategoria().getId()));
        return "pages/plate/plate";
    }

    // ===================== DELETE PLATE =====================

    @PostMapping("/delete/{id}")
    public String deletePlato(@PathVariable Long id) {
        platoService.deleteByID(id);
        return "redirect:/plate/crud";
    }

    // ===================== CREATE PLATE =====================

    @GetMapping("/create")
    public String mostrarFormularioCrearPlato(Model model) {
        model.addAttribute("plato", new Plato("", 0.0, "", "", false));
        model.addAttribute("categorias", categoriaService.selectAll());
        model.addAttribute("formAction", "/plate/create"); // 👈
        return "pages/plate/create";
    }

    @PostMapping("/create")
    public String agregarPlato(@ModelAttribute("plato") Plato plato, @RequestParam("categoriaId") Long categoriaId) {
        Categoria categoria = categoriaService.selectById(categoriaId);
        plato.setCategoria(categoria);
        platoService.insert(plato); // Funciona como un upsert
        return "redirect:/plate/crud";
    }

    // ===================== EDIT PLATE =====================

    @GetMapping("/edit/{id}")
    public String mostrarFormularioEditarPlato(@PathVariable("id") Long id, Model model) {
        model.addAttribute("plato", platoService.selectById(id));
        model.addAttribute("categorias", categoriaService.selectAll());
        model.addAttribute("formAction", "/plate/edit/" + id); // 👈
        return "pages/plate/edit";
    }

    @PostMapping("/edit/{id}")  // 👈 esto faltaba
    public String editarPlato(@PathVariable("id") Long id, @ModelAttribute("plato") Plato plato, @RequestParam("categoriaId") Long categoriaId) {
        plato.setId(id);
        Categoria categoria = categoriaService.selectById(categoriaId);
        plato.setCategoria(categoria);
        platoService.update(plato);
        return "redirect:/plate/crud";
    }

    // ===================== UPDATE CATEGORY FROM DROPDOWN =====================

    @PostMapping("/update_category")
    public String updateCategoria(
            @RequestParam Long platoId,
            @RequestParam Long categoriaId) {

        Categoria categoria = categoriaService.selectById(categoriaId);
        platoService.cambiarCategoria(categoria, platoId);
        return "redirect:/plate/crud";
    }
}