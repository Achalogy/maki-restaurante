package com.maki.web.controller;

import com.maki.web.entities.Cliente;
import com.maki.web.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/LogIn")
public class LogInRegisterController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("/log-in")
    public String mostrarLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "log-in";
    }

    @PostMapping("/log-in")
    public String loggearCliente(@ModelAttribute("cliente") Cliente cliente, HttpSession session) {
        try {
            Cliente clienteSesion = clienteService.verificarCredenciales(cliente.getCorreo(), cliente.getContrasena());

            session.setAttribute("clienteSesion", clienteSesion);

            return "redirect:/LogIn/sesion";
        } catch(Exception e) {
            return "redirect:/LogIn/log-in";
        }
    }

    @GetMapping("/sesion")
    public String mostrarSesion(HttpSession session, Model model) {

        Cliente clienteSesion =
                (Cliente) session.getAttribute("clienteSesion");

        if(clienteSesion == null) {
            return "redirect:/LogIn/log-in";
        }

        model.addAttribute("cliente", clienteSesion);

        return "session"; // templates/session.html
    }
}