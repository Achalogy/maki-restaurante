package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maki.web.entities.Plato;
import com.maki.web.service.MenuService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/Comidas")
public class Comidas {

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
}