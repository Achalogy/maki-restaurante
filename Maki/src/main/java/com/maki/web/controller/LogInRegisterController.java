package com.maki.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.maki.web.service.MenuService;

@Controller
@RequestMapping("/LogIn")
public class LogInRegisterController {
    
    @Autowired
    MenuService menuService;

    @GetMapping("/log-in")
    public String mostrarMenuEnTarjetas() {
        return "log-in";
    }
    
}
