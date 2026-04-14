package com.maki.web.controller;

import com.maki.web.entities.Cliente;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.ClienteService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@Controller
@RequestMapping("/api/v1/client")
public class ClientesController {

    @Autowired
    private ClienteService clienteService;

    @GetMapping("")
    @ResponseBody
    public List<Cliente> getAllClients() {
        return clienteService.selectAll();
    }

    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> getClientById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(clienteService.selectById(id), HttpStatus.OK);
        } catch(Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    @PostMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Cliente> updateClient(@PathVariable Long id, @RequestBody(required = false) Cliente data) {
        try {
            Cliente updateData = clienteService.selectById(id);

            if(data.getNombre() != null)
                updateData.setNombre(data.getNombre());
            if(data.getApellido() != null)
                updateData.setApellido(data.getApellido());
            if(data.getCorreo() != null)
                updateData.setCorreo(data.getCorreo());
            if(data.getContrasena() != null)
                updateData.setContrasena(data.getContrasena());
            if(data.getTelefono() != null)
                updateData.setTelefono(data.getTelefono());
            if(data.getDireccion() != null)
                updateData.setDireccion(data.getDireccion());

            return new ResponseEntity<>(clienteService.update(updateData), HttpStatus.OK);
        } catch(Exception e) {
            if(e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    @ResponseBody
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        try {
            clienteService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    @ResponseBody
    public ResponseEntity<Cliente> createClient(@RequestBody(required = false) Cliente data) {
        try {
            return new ResponseEntity<>(clienteService.insert(data), HttpStatus.OK);
        } catch(Exception e) {
            e.printStackTrace();
            if(e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
    @PostMapping("/log-in")
    @ResponseBody
    public ResponseEntity<Cliente> loginClient(@RequestBody(required = false) Cliente data) {
        try {
            return new ResponseEntity<>(clienteService.verificarCredenciales(
                data.getCorreo(),
                data.getContrasena()
            ), HttpStatus.OK);
        }catch(Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    
}