package com.maki.web.controller;

import com.maki.web.dtos.ClientDTO;
import com.maki.web.dtos.ClientMapper;
import com.maki.web.entities.Client;
import com.maki.web.entities.UserEntity;
import com.maki.web.exception.EntityNotFoundException;
import com.maki.web.security.CustomUserDetailService;
import com.maki.web.service.ClientService;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * Controlador para gestionar las operaciones relacionadas con clientes.
 *
 * <p>Endpoints disponibles: - GET / - Obtener todos los clientes - GET /{id} - Obtener cliente por
 * ID - GET /me - Obtener perfil del cliente autenticado - POST / - Crear nuevo cliente (registro) -
 * POST /log-in - Autenticar cliente - POST /{id} - Actualizar cliente - DELETE /{id} - Eliminar
 * cliente
 */
@RestController
@RequestMapping("/api/v1/client")
public class ClientController {

    private final ClientService clientService;

    private final CustomUserDetailService userDetailService;

    /**
     * Constructor para inyección de dependencias.
     *
     * @param clientService Servicio para operaciones de cliente
     * @param userDetailService Servicio para gestionar entidades de usuario
     * @param clientMapper Mapper para convertir entre entidades y DTOs
     */
    public ClientController(
            ClientService clientService, CustomUserDetailService userDetailService) {
        this.clientService = clientService;
        this.userDetailService = userDetailService;
    }

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<ClientDTO> getAllClients() {
        return clientService.selectAll().stream()
                .map(ClientMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getClientById(@PathVariable Long id) {
        try {
            ClientDTO dto = ClientMapper.INSTANCE.toDTO(clientService.selectById(id));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Endpoint seguro: devuelve el cliente autenticado leyendo su email del JWT. NO usa el id del
     * localStorage — el backend extrae el usuario del token.
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMyProfile() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String email = auth.getName();
            Client client = clientService.findByEmail(email);
            return new ResponseEntity<>(ClientMapper.INSTANCE.toDTO(client), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("Cliente no encontrado", HttpStatus.NOT_FOUND);
        }
    }

    // ===================== UPDATE =====================
    @PostMapping("/{id}")
    public ResponseEntity<?> updateClient(
            @PathVariable Long id, @RequestBody(required = false) Client data) {
        try {
            Client updateData = clientService.selectById(id);
            if (data.getName() != null) updateData.setName(data.getName());
            if (data.getSurname() != null) updateData.setSurname(data.getSurname());
            if (data.getEmail() != null) updateData.setEmail(data.getEmail());
            if (data.getPhone() != null) updateData.setPhone(data.getPhone());
            if (data.getAddress() != null) updateData.setAddress(data.getAddress());

            ClientDTO dto = ClientMapper.INSTANCE.toDTO(clientService.update(updateData));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteClient(@PathVariable Long id) {
        try {
            clientService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== CREATE (SIGN UP) =====================
    @PostMapping("")
    public ResponseEntity<?> createClient(@RequestBody(required = false) Client data) {
        try {
            // 1. Verifica que el email no exista ya
            if (clientService.existsByEmail(data.getEmail())) {
                return new ResponseEntity<>("Email ya registrado", HttpStatus.BAD_REQUEST);
            }

            // 2. Guarda el cliente
            Client savedClient = clientService.insert(data);

            // 3. Crea el UserEntity en la tabla users (para autenticación JWT)
            UserEntity userEntity = userDetailService.clientToUserEntity(savedClient);
            savedClient.setUser(userEntity);
            clientService.update(savedClient);

            return new ResponseEntity<>(ClientMapper.INSTANCE.toDTO(savedClient), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== LOGIN =====================
    @PostMapping("/log-in")
    public ResponseEntity<?> loginClient(@RequestBody(required = false) Client data) {
        try {
            ClientDTO dto =
                    ClientMapper.INSTANCE.toDTO(
                            clientService.verifyCredentials(data.getEmail(), data.getPassword()));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
