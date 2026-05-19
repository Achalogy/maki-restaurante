package com.maki.web.controller;

import com.maki.web.entities.Client;
import com.maki.web.entities.UserEntity;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.ClientService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.maki.web.security.CustomUserDetailService;

@RestController
@RequestMapping("/api/v1/client")

public class ClientController {

    @Autowired
    private ClientService clienteService;

    @Autowired
    private CustomUserDetailService customUserDetailService;

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
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody(required = false) Client data) {
        try {
            Client updateData = clienteService.selectById(id);

            if (data.getName() != null)
                updateData.setName(data.getName());
            if (data.getSurname() != null)
                updateData.setSurname(data.getSurname());
            if (data.getEmail() != null)
                updateData.setEmail(data.getEmail());
            if (data.getPassword() != null)
                updateData.setPassword(data.getPassword());
            if (data.getPhone() != null)
                updateData.setPhone(data.getPhone());
            if (data.getAddress() != null)
                updateData.setAddress(data.getAddress());

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
    public ResponseEntity<Client> createClient(@RequestBody(required = false) Client data) {

        if(clienteService.existsByEmail(data.getEmail())) {
          return new ResponseEntity<Client>(data,HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = customUserDetailService.ClientToUserEntity(data);
        data.setUser(userEntity);
        Client newClient = clienteService.insert(data);

        if(newClient == null)
          return new ResponseEntity<Client>(newClient, HttpStatus.BAD_REQUEST);

        return new ResponseEntity<Client>(data, HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<Client> loginClient(@RequestBody(required = false) Client data) {
        try {
            return new ResponseEntity<>(clienteService.verifyCredentials(
                    data.getEmail(),
                    data.getPassword()), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
