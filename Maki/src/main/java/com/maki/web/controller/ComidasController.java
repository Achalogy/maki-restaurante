package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import com.maki.web.entities.Plato;
import com.maki.web.service.MenuService;

import org.springframework.ui.Model;



@Controller
@RequestMapping("/Comidas")
public class ComidasController {

    @Autowired
    MenuService menuService;

    @GetMapping("/AdminTable")
    public String mostrarMenuEnTarjetas(Model model) {
        model.addAttribute("menu", menuService.getMenu());
        return "menu-table";
    }

    @GetMapping("/{id}")
    public String mostrarMenuEnTabla(Model model, @PathVariable("id") Integer plateid) {
        Plato plato = menuService.getById(plateid);
        model.addAttribute("plato", plato);
        return "single-item";
    }


    @GetMapping("/OurMenu")
    public String mostrarMenuEnTabla(Model model) {
        model.addAttribute("menu", menuService.getMenu());
        return "menu-cards";
    }

    @PostMapping("/deleteMenuItem/{id}")
    public String deletePlato(@PathVariable Integer id) {
        menuService.deletePlato(id);
        return "redirect:/Comidas/AdminTable";
    }

    @GetMapping("/addMenuItem")
    public String mostrarFormularioCrearPlato(Model model) {
        Plato plato = new Plato(null,"", 0.0, "", "",false,"");
        model.addAttribute("plato", plato);
        return "crear-plato";
    }

    @PostMapping("/addMenuItem")
    public String agregarPlato(@ModelAttribute("plato") Plato plato) {
        menuService.addPlato(plato);
        return "redirect:/Comidas/AdminTable";
    }

    @GetMapping("/updateMenuItem/{id}")
    public String mostrarFormularioEditarPlato(@PathVariable("id") Integer id, Model model) {
        Plato plato = menuService.getById(id);
        model.addAttribute("plato", plato);
        return "crear-plato";
    }
    
}