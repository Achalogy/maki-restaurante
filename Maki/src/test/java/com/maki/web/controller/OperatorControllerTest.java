package com.maki.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maki.web.entities.Operator;
import com.maki.web.service.OperatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Pruebas de integración del OperatorController.
 *
 * Se usa @WebMvcTest para levantar únicamente la capa web del controlador,
 * sin arrancar toda la aplicación. Esto hace las pruebas más rápidas.
 *
 * Se usa @MockBean para simular el OperatorService, ya que lo que queremos
 * probar es el comportamiento del controlador, no del servicio.
 *
 * Cubrimos 1 prueba de cada tipo HTTP: GET all, GET by id, POST crear,
 * POST actualizar, DELETE, y POST login.
 */
@WebMvcTest(OperatorController.class)
// Esto arregla los tests
@Import(com.maki.web.security.SecurityConfig.class)
public class OperatorControllerTest {

    // MockMvc es la herramienta que simula las peticiones HTTP
    @Autowired
    private MockMvc mockMvc;

    // Mockeamos el servicio para no depender de la base de datos
    @MockBean
    private OperatorService operatorService;

    // ObjectMapper convierte objetos Java a JSON para enviarlo en el body
    @Autowired
    private ObjectMapper objectMapper;

    // =====================================================================
    // PRUEBA 1 — GET /api/v1/operator
    // Verifica que el endpoint retorna la lista de operadores con status 200
    // =====================================================================
    @Test
    public void operatorController_getAllOperators_returnsList() throws Exception {

        // Arrange: creamos operadores de prueba quemados
        Operator op1 = new Operator("Carlos Gomez", "carlos.gomez", "123456");
        Operator op2 = new Operator("Maria Lopez", "maria.lopez", "123456");

        // Cuando el servicio llame a selectAll(), retorna nuestra lista quemada
        when(operatorService.selectAll()).thenReturn(List.of(op1, op2));

        // Act: realizamos la petición GET
        ResultActions result = mockMvc.perform(
                get("/api/v1/operator")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert: esperamos status 200 y que la lista tenga 2 elementos
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Carlos Gomez"))
                .andExpect(jsonPath("$[1].name").value("Maria Lopez"));
    }

    // =====================================================================
    // PRUEBA 2 — GET /api/v1/operator/{id} (CASO EXITOSO)
    // Verifica que cuando existe el operador, retorna 200 con sus datos
    // =====================================================================
    @Test
    public void operatorController_getOperatorById_returnsOperator() throws Exception {

        // Arrange
        Operator op = new Operator("Ana Martinez", "ana.martinez", "123456");

        // Simulamos que el servicio encuentra el operador con id=1
        when(operatorService.selectById(1L)).thenReturn(op);

        // Act
        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert: esperamos 200 y que el nombre coincida
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana Martinez"))
                .andExpect(jsonPath("$.username").value("ana.martinez"));
    }

    // =====================================================================
    // PRUEBA 3 — GET /api/v1/operator/{id} (CASO FALLIDO)
    // Verifica que cuando el operador no existe, retorna 404
    // =====================================================================
    @Test
    public void operatorController_getOperatorById_notFound_returns404() throws Exception {

        // Arrange: simulamos que el servicio lanza excepción cuando no encuentra el operador
        when(operatorService.selectById(999L))
                .thenThrow(new com.maki.web.exception.EntityNotFoundException("Operator no encontrado"));

        // Act
        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/999")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert: esperamos 404
        result.andExpect(status().isNotFound());
    }

    // =====================================================================
    // PRUEBA 4 — POST /api/v1/operator (CREAR)
    // Verifica que se puede crear un operador y retorna 200 con el objeto creado
    // =====================================================================
    @Test
    public void operatorController_createOperator_returnsCreatedOperator() throws Exception {

        // Arrange: operador que vamos a enviar en el body
        Operator newOperator = new Operator("Luis Garcia", "luis.garcia", "123456");

        // Simulamos que el servicio guarda y retorna el operador
        when(operatorService.insert(any(Operator.class))).thenReturn(newOperator);

        // Act: enviamos el POST con el operador serializado en JSON
        ResultActions result = mockMvc.perform(
                post("/api/v1/operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOperator))
        );

        // Assert: esperamos 200 y que el nombre del operador retornado sea correcto
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luis Garcia"))
                .andExpect(jsonPath("$.username").value("luis.garcia"));
    }

    // =====================================================================
    // PRUEBA 5 — POST /api/v1/operator/{id} (ACTUALIZAR)
    // Verifica que se puede actualizar un operador existente y retorna 200
    // =====================================================================
    @Test
    public void operatorController_updateOperator_returnsUpdatedOperator() throws Exception {

        // Arrange: operador original que está en la base de datos
        Operator existingOperator = new Operator("Sofia Rodriguez", "sofia.rodriguez", "123456");

        // Datos de actualización que llegan en el body
        Operator updateData = new Operator("Sofia Rodriguez Updated", "sofia.updated", "newpass");

        // Simulamos que el servicio encuentra el operador por id
        when(operatorService.selectById(1L)).thenReturn(existingOperator);

        // Simulamos que el servicio guarda los cambios y retorna el operador actualizado
        when(operatorService.update(any(Operator.class))).thenReturn(updateData);

        // Act
        ResultActions result = mockMvc.perform(
                post("/api/v1/operator/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData))
        );

        // Assert: esperamos 200 y que el nombre actualizado se refleje
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sofia Rodriguez Updated"))
                .andExpect(jsonPath("$.username").value("sofia.updated"));
    }

    // =====================================================================
    // PRUEBA 6 — DELETE /api/v1/operator/{id}
    // Verifica que eliminar un operador retorna 200 con true
    // =====================================================================
    @Test
    public void operatorController_deleteOperator_returnsTrue() throws Exception {

        // Arrange: no necesitamos preparar nada especial porque delete no retorna objeto
        // Solo simulamos que el servicio no lanza excepción (comportamiento por defecto del mock)

        // Act
        ResultActions result = mockMvc.perform(
                delete("/api/v1/operator/1")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert: esperamos 200 y true como respuesta
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    // =====================================================================
    // PRUEBA 7 — GET /api/v1/operator/username/{username}
    // Verifica que se puede obtener un operador por username usando el query personalizado
    // =====================================================================
    @Test
    public void operatorController_getOperatorByUsername_returnsOperator() throws Exception {

        // Arrange
        Operator op = new Operator("Raul Perez", "raul.perez", "123456");
        when(operatorService.selectByUsername("raul.perez")).thenReturn(op);

        // Act
        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/username/raul.perez")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("raul.perez"))
                .andExpect(jsonPath("$.name").value("Raul Perez"));
    }

    // =====================================================================
    // PRUEBA 8 — GET /api/v1/operator/search?term={term}
    // Verifica que el endpoint de búsqueda usa el query personalizado y retorna coincidencias
    // =====================================================================
    @Test
    public void operatorController_searchOperators_returnsMatchingOperators() throws Exception {

        // Arrange
        Operator op1 = new Operator("Carla Gomez", "carla.gomez", "123456");
        Operator op2 = new Operator("Carlos Lopez", "carlos.lopez", "123456");
        when(operatorService.searchByNameOrUsername("car")).thenReturn(List.of(op1, op2));

        // Act
        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/search")
                        .param("term", "car")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("carla.gomez"))
                .andExpect(jsonPath("$[1].username").value("carlos.lopez"));
    }

    // =====================================================================
    // PRUEBA 9 — GET /api/v1/operator/count/{username}
    // Verifica que el endpoint de conteo retorna la cantidad correcta usando el query personalizado
    // =====================================================================
    @Test
    public void operatorController_countOperatorsByUsername_returnsCount() throws Exception {

        // Arrange
        when(operatorService.countByUsername("carla.gomez")).thenReturn(1L);

        // Act
        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/count/carla.gomez")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    // =====================================================================
    // PRUEBA 10 — GET /api/v1/operator/ordered
    // Verifica que el endpoint retorna operadores ordenados por nombre usando el query personalizado
    // =====================================================================
    @Test
    public void operatorController_getOperatorsOrderedByName_returnsSortedList() throws Exception {

        // Arrange
        Operator op1 = new Operator("Ana Martinez", "ana.martinez", "123456");
        Operator op2 = new Operator("Beto Suarez", "beto.suarez", "123456");
        when(operatorService.selectAllOrderedByName()).thenReturn(List.of(op1, op2));

        // Act
        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/ordered")
                        .contentType(MediaType.APPLICATION_JSON)
        );

        // Assert
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ana Martinez"))
                .andExpect(jsonPath("$[1].name").value("Beto Suarez"));
    }

    // =====================================================================
    // PRUEBA 11 — POST /api/v1/operator/log-in (CREDENCIALES CORRECTAS)
    // Verifica que el login retorna 200 con el operador cuando las credenciales son válidas
    // =====================================================================
    @Test
    public void operatorController_login_validCredentials_returnsOperator() throws Exception {

        // Arrange: credenciales válidas
        Operator loginData = new Operator("Javier Martinez", "javier.martinez", "123456");

        // TODO: ARREGLAR
        // Simulamos que el servicio verifica credenciales y retorna el operador
        // when(operatorService.verifyCredentials("javier.martinez", "123456"))
        //         .thenReturn(loginData);

        // Act: enviamos POST al endpoint de login
        ResultActions result = mockMvc.perform(
                post("/api/v1/operator/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData))
        );

        // Assert: esperamos 200 y que el operador retornado tenga el nombre correcto
        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Javier Martinez"))
                .andExpect(jsonPath("$.username").value("javier.martinez"));
    }

    // =====================================================================
    // PRUEBA 8 — POST /api/v1/operator/log-in (CREDENCIALES INCORRECTAS)
    // Verifica que el login retorna 400 cuando las credenciales son inválidas
    // =====================================================================
    @Test
    public void operatorController_login_invalidCredentials_returns400() throws Exception {

        // Arrange: credenciales inválidas
        Operator badLogin = new Operator("Nadie", "noexiste", "wrongpass");

        // TODO: ARREGLAR
        // Simulamos que el servicio lanza excepción de credenciales inválidas
        // when(operatorService.verifyCredentials(anyString(), anyString()))
        //         .thenThrow(new com.maki.web.exception.InvalidCredentialsException("Credenciales inválidas"));

        // Act
        ResultActions result = mockMvc.perform(
                post("/api/v1/operator/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badLogin))
        );

        // Assert: esperamos 400 Bad Request
        result.andExpect(status().isBadRequest());
    }
}
