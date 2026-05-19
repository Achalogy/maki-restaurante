package com.maki.web.controller;

import com.maki.web.dtos.ClientDTO;
import com.maki.web.dtos.MakiMapper;
import com.maki.web.entities.Client;
import com.maki.web.entities.UserEntity;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.service.ClientService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.*;

import com.maki.web.security.CustomUserDetailService;


@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    @Autowired
    private ClientService clienteService;

    @Autowired
    private CustomUserDetailService customUserDetailService;



    private MakiMapper mapper;

    // GET todos — retorna lista de DTOs (sin password)
    @GetMapping("")
    public List<ClientDTO> getAllClients() {
        return clienteService.selectAll()
                .stream()
                .map(mapper::toClientDTO)
                .collect(Collectors.toList());
    }

    // GET por id — retorna DTO
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(mapper.toClientDTO(clienteService.selectById(id)), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // UPDATE
    @PostMapping("/{id}")
    public ResponseEntity<ClientDTO> updateClient(@PathVariable Long id, @RequestBody(required = false) Client data) {
        try {
            Client updateData = clienteService.selectById(id);

            if (data.getName() != null) updateData.setName(data.getName());
            if (data.getSurname() != null) updateData.setSurname(data.getSurname());
            if (data.getEmail() != null) updateData.setEmail(data.getEmail());
            if (data.getPassword() != null) updateData.setPassword(data.getPassword());
            if (data.getPhone() != null) updateData.setPhone(data.getPhone());
            if (data.getAddress() != null) updateData.setAddress(data.getAddress());

            return new ResponseEntity<>(mapper.toClientDTO(clienteService.update(updateData)), HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        try {
            clienteService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // CREATE — recibe Client completo pero retorna DTO (sin password)
    @PostMapping("")
    public ResponseEntity<ClientDTO> createClient(@RequestBody(required = false) Client client) {

        if(clienteService.existsByEmail(client.getEmail())) {
          return new ResponseEntity<ClientDTO>(mapper.toClientDTO(client),HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = customUserDetailService.ClientToUserEntity(client);
        client.setUser(userEntity);
        Client newClient = clienteService.insert(client);

        if(newClient == null)
          return new ResponseEntity<ClientDTO>(mapper.toClientDTO(newClient), HttpStatus.BAD_REQUEST);

        return new ResponseEntity<ClientDTO>(mapper.toClientDTO(client), HttpStatus.CREATED);
    }
}
