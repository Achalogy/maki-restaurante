package com.maki.web.controller;

import com.maki.web.entities.Cliente;
import com.maki.web.exception.EntityConstraintException;
import com.maki.web.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/client")
public class ClientesController {

    @Autowired
    private ClienteService clienteService;

    // ============= LADO DEL CLIENTE =============

    @GetMapping("/log-in")
    public String mostrarLogin(Model model) {
        model.addAttribute("cliente", new Cliente());
        return "client/log-in";
    }

    @PostMapping("/log-in")
    public String loggearCliente(@ModelAttribute("cliente") Cliente cliente) {
        try {
            Cliente clienteSesion = clienteService.verificarCredenciales(cliente.getCorreo(), cliente.getContrasena());

            return "redirect:/client/session/" + clienteSesion.getId();
        } catch(Exception e) {
            return "redirect:/client/log-in";
        }
    }

    @GetMapping("/session/{id}")
    public String getMethodName(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.selectById(id);

        model.addAttribute("cliente", cliente);
        return "client/session";
    }
    

    @GetMapping("/sign-up")
    public String registrarCliente(Model model) {

        Cliente cliente = new Cliente();

        model.addAttribute("cliente", cliente);

        return "client/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUp(@ModelAttribute("cliente") Cliente cliente) {
        try {
            cliente.setId(null);
            cliente = clienteService.insert(cliente);
            return "redirect:/client/session/" + cliente.getId();
        } catch(Exception e) {
            if(e instanceof EntityConstraintException) {
                return "redirect:/client/sign-up?msg=\"" + e.getMessage() + "\"";
            }
            return "redirect:/client/sign-up?msg=\"Error desconocido\"";
        }
    }
    
    // ============= LADO DEL ADMINISTRADOR =============

    /**
     * Muestra la lista completa de clientes en el panel CRUD
     */
    @GetMapping("/crud")
    public String listarClientes(Model model) {
        // Obtenemos todos los clientes del service
        model.addAttribute("clientes", clienteService.selectAll());
        return "client/crud"; 
    }

    /**
     * Elimina un cliente por su ID
     */
    @PostMapping("/delete/{id}")
    public String eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.deleteByID(id);
            return "redirect:/client/crud?success=deleted";
        } catch (Exception e) {
            return "redirect:/client/crud?error=nodelete";
        }
    }

    /**
     * Muestra el formulario de edición para un cliente existente
     */
    @GetMapping("/edit/{id}")
    public String mostrarFormularioEditar(@PathVariable Long id, Model model) {
        try {
            Cliente cliente = clienteService.selectById(id);
            model.addAttribute("cliente", cliente);
            return "client/edit";
        } catch (Exception e) {
            return "redirect:/client/crud";
        }
    }

    /**
     * Procesa la actualización de un cliente
     */
    @PostMapping("/update")
    public String actualizarCliente(@ModelAttribute("cliente") Cliente cliente) {
        try {
            clienteService.update(cliente); 
            return "redirect:/client/crud?success=updated";
        } catch (Exception e) {
            return "redirect:/client/crud?error=updatefailed";
        }
    }

    // ============= GESTIÓN DE PERFIL PROPIO =============

    /**
     * Muestra el perfil del usuario loggeado para edición
     */
    @GetMapping("/profile/{id}")
    public String verPerfilPropio(@PathVariable Long id, Model model) {
        Cliente cliente = clienteService.selectById(id);
        model.addAttribute("cliente", cliente);
        return "client/profile";
    }

    @PostMapping("/profile/delete")
    public String eliminarClienteDesdePerfil(@ModelAttribute("cliente") Cliente cliente) {
        try {
            System.out.println(cliente);
            clienteService.deleteByID(cliente.getId());
            return "redirect:/?success=deleted";
        } catch (Exception e) {
            return "redirect:/?error=nodelete";
        }
    }

    /**
     * Procesa la actualización desde el perfil del usuario y lo devuelve a su sesión
     */
    @PostMapping("/profile/update")
    public String actualizarPerfilPropio(@ModelAttribute("cliente") Cliente cliente) {
        try {
            // Obtenemos los datos actuales para no perder campos que no están en el form (como la contraseña)
            Cliente clienteActual = clienteService.selectById(cliente.getId());

            // Actualizamos solo los campos permitidos
            clienteActual.setNombre(cliente.getNombre());
            clienteActual.setApellido(cliente.getApellido());
            clienteActual.setTelefono(cliente.getTelefono());
            clienteActual.setDireccion(cliente.getDireccion());

            clienteService.update(clienteActual);

            return "redirect:/client/session/" + cliente.getId() + "?success=profileUpdated";
        } catch (Exception e) {
            System.out.println(e);
            return "redirect:/client/profile/" + cliente.getId() + "?error=failed";
        }
    }
}