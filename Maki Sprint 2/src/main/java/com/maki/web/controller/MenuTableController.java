package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.maki.web.service.MenuService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MenuTableController {

    @Autowired
    MenuService menuService;

    @GetMapping("/MenuTable")
    public String mostrarMenuEnTarjetas(Model model) {
        model.addAttribute("menu", menuService.getMenu());
        return "menu-table";
    }

}
