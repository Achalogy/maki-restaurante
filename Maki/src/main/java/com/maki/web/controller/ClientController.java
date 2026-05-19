package com.maki.web.controller;

import com.maki.web.entities.Client;
import com.maki.web.entities.UserEntity;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.ClientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.maki.web.security.CustomUserDetailService;
import com.maki.web.security.JWTGenerator;

@RestController
@RequestMapping("/api/v1/client")

public class ClientController {

    @Autowired
    private ClientService clienteService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTGenerator jwtGenerator;

    @GetMapping("")
    public List<Client> getAllClients() {
        return clienteService.selectAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(clienteService.selectById(id), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody(required = false) Client client) {
        try {
            Client updateData = clienteService.selectById(id);

            if (client.getName() != null)
                updateData.setName(client.getName());
            if (client.getSurname() != null)
                updateData.setSurname(client.getSurname());
            if (client.getEmail() != null)
                updateData.setEmail(client.getEmail());
            if (client.getPassword() != null)
                updateData.setPassword(client.getPassword());
            if (client.getPhone() != null)
                updateData.setPhone(client.getPhone());
            if (client.getAddress() != null)
                updateData.setAddress(client.getAddress());

            return new ResponseEntity<>(clienteService.update(updateData), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        try {
            clienteService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public ResponseEntity<Client> createClient(@RequestBody(required = false) Client client) {

        if(clienteService.existsByEmail(client.getEmail())) {
          return new ResponseEntity<Client>(client,HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = customUserDetailService.ClientToUserEntity(client);
        client.setUser(userEntity);
        Client newClient = clienteService.insert(client);

        if(newClient == null)
          return new ResponseEntity<Client>(newClient, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Client>(client, HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<String> loginClient(@RequestBody(required = false) Client client) {        
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(client.getEmail(), client.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtGenerator.generateToken(authentication);
            
            return new ResponseEntity<String>(token, HttpStatus.OK);
        }catch(Exception e) {
            return new ResponseEntity<String>("Credenciales incorrectas", HttpStatus.BAD_REQUEST);
        }

    }

}
