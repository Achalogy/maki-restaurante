package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maki.web.service.CategoriaService;
import com.maki.web.service.PlatoService;

import org.springframework.ui.Model;

@Controller
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private PlatoService platoService;

    @Autowired
    private CategoriaService categoriaService;


    // ===================== PUBLIC MENU =====================

    @GetMapping("")
    public String mostrarMenuPublico(Model model) {
        model.addAttribute("menu", platoService.selectAll());
        model.addAttribute("categorias", categoriaService.selectAll());
        return "pages/menu/menu";
    }
}
