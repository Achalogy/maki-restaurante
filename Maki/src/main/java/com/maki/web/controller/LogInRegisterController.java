package com.maki.web.controller;

import com.maki.web.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/LogIn")
public class LogInRegisterController {
    @GetMapping("/log-in")
    public String mostrarMenuEnTarjetas() {
        return "log-in";
    }
    
}