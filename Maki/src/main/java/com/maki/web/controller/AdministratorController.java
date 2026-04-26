package com.maki.web.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.maki.web.entities.Administrator;
import com.maki.web.service.AdministratorService;
import com.maki.web.exception.EntityNotFoundException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
@RestController
@RequestMapping("/api/v1/admin")
public class AdministratorController {
    @Autowired
    private AdministratorService AdministratorService;

    // ===================== GET ALL =====================
    @GetMapping("")
    public List<Administrator> getAllAdministrators() {
        return AdministratorService.selectAll();
    }

    // ===================== GET BY ID =====================
    @GetMapping("/{id}")
    public ResponseEntity<Administrator> getAdministratorById(@PathVariable Long id) {
        try {
            Administrator Administrator = AdministratorService.selectById(id);
            if (Administrator == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            return new ResponseEntity<>(Administrator, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // ===================== CREATE =====================
    @PostMapping("")
    public ResponseEntity<Administrator> saveAdministrator(@RequestBody Administrator Administrator) {
        try {
            // El servicio insert suele manejar el guardado o actualización
            return new ResponseEntity<>(AdministratorService.insert(Administrator), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== Update =====================
    @PostMapping("/{id}")
    public ResponseEntity<Administrator> updateAdministrator(@PathVariable Long id,
            @RequestBody(required = false) Administrator data) {
        try {
            // Buscamos el administrador existente por ID
            Administrator updateData = AdministratorService.selectById(id);

            // Validamos y actualizamos solo los campos presentes en el request
            if (data.getName() != null)
                updateData.setName(data.getName());
            if (data.getUsername() != null)
                updateData.setUsername(data.getUsername());
            if (data.getPassword() != null)
                updateData.setPassword(data.getPassword());

            // Guardamos los cambios usando el servicio
            return new ResponseEntity<>(AdministratorService.update(updateData), HttpStatus.OK);

        } catch (Exception e) {
            // Si no se encuentra el registro
            if (e instanceof EntityNotFoundException) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Error genérico de solicitud
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // ===================== DELETE =====================
    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteAdministrator(@PathVariable Long id) {
        try {
            AdministratorService.deleteByID(id);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/log-in")
    public ResponseEntity<Administrator> loginAdministrator(@RequestBody(required = false) Administrator data) {
        try {
            return new ResponseEntity<>(AdministratorService.verifyCredentials(
                    data.getUsername(),
                    data.getPassword()), HttpStatus.OK);
        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
