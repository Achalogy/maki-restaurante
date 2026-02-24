package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.maki.web.entities.Plato;
import com.maki.web.service.MenuService;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class SingleItemController {

    @Autowired
    MenuService menuService;


    @GetMapping("/MenuItem/{id}")
    public String mostrarMenuEnTabla(Model model, @PathVariable("id") Integer plateid) {
        Plato plato  = menuService.getById(plateid);
        model.addAttribute("plato", plato);
        return "single-item";
    }
}
