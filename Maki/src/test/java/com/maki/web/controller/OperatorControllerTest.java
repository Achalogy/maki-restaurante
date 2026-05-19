package com.maki.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maki.web.dtos.MakiMapper;
import com.maki.web.dtos.OperatorDTO;
import com.maki.web.entities.Operator;
import com.maki.web.service.OperatorService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OperatorController.class)
public class OperatorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OperatorService operatorService;

    // ESTO ES LO QUE FALTABA — el controlador ahora depende de MakiMapper
    @MockBean
    private MakiMapper makiMapper;

    @Autowired
    private ObjectMapper objectMapper;

    // Helper para no repetir codigo en cada test
    private OperatorDTO buildDTO(Long id, String name, String username) {
        OperatorDTO dto = new OperatorDTO();
        dto.setId(id);
        dto.setName(name);
        dto.setUsername(username);
        return dto;
    }

    @Test
    public void operatorController_getAllOperators_returnsList() throws Exception {
        Operator op1 = new Operator("Carlos Gomez", "carlos.gomez", "123456");
        Operator op2 = new Operator("Maria Lopez", "maria.lopez", "123456");

        when(operatorService.selectAll()).thenReturn(List.of(op1, op2));
        when(makiMapper.toOperatorDTO(op1)).thenReturn(buildDTO(1L, "Carlos Gomez", "carlos.gomez"));
        when(makiMapper.toOperatorDTO(op2)).thenReturn(buildDTO(2L, "Maria Lopez", "maria.lopez"));

        ResultActions result = mockMvc.perform(
                get("/api/v1/operator").contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Carlos Gomez"))
                .andExpect(jsonPath("$[1].name").value("Maria Lopez"));
    }

    @Test
    public void operatorController_getOperatorById_returnsOperator() throws Exception {
        Operator op = new Operator("Ana Martinez", "ana.martinez", "123456");

        when(operatorService.selectById(1L)).thenReturn(op);
        when(makiMapper.toOperatorDTO(op)).thenReturn(buildDTO(1L, "Ana Martinez", "ana.martinez"));

        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/1").contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Ana Martinez"))
                .andExpect(jsonPath("$.username").value("ana.martinez"));
    }

    @Test
    public void operatorController_getOperatorById_notFound_returns404() throws Exception {
        when(operatorService.selectById(999L))
                .thenThrow(new com.maki.web.exception.EntityNotFoundException("Operator no encontrado"));

        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/999").contentType(MediaType.APPLICATION_JSON));

        result.andExpect(status().isNotFound());
    }

    @Test
    public void operatorController_createOperator_returnsCreatedOperator() throws Exception {
        Operator newOperator = new Operator("Luis Garcia", "luis.garcia", "123456");

        when(operatorService.insert(any(Operator.class))).thenReturn(newOperator);
        when(makiMapper.toOperatorDTO(newOperator)).thenReturn(buildDTO(1L, "Luis Garcia", "luis.garcia"));

        ResultActions result = mockMvc.perform(
                post("/api/v1/operator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newOperator)));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Luis Garcia"))
                .andExpect(jsonPath("$.username").value("luis.garcia"));
    }

    @Test
    public void operatorController_updateOperator_returnsUpdatedOperator() throws Exception {
        Operator existingOperator = new Operator("Sofia Rodriguez", "sofia.rodriguez", "123456");
        Operator updateData = new Operator("Sofia Rodriguez Updated", "sofia.updated", "newpass");

        when(operatorService.selectById(1L)).thenReturn(existingOperator);
        when(operatorService.update(any(Operator.class))).thenReturn(updateData);
        when(makiMapper.toOperatorDTO(updateData))
                .thenReturn(buildDTO(1L, "Sofia Rodriguez Updated", "sofia.updated"));

        ResultActions result = mockMvc.perform(
                post("/api/v1/operator/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateData)));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Sofia Rodriguez Updated"))
                .andExpect(jsonPath("$.username").value("sofia.updated"));
    }

    @Test
    public void operatorController_deleteOperator_returnsTrue() throws Exception {
        ResultActions result = mockMvc.perform(
                delete("/api/v1/operator/1").contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(true));
    }

    @Test
    public void operatorController_getOperatorByUsername_returnsOperator() throws Exception {
        Operator op = new Operator("Raul Perez", "raul.perez", "123456");
        when(operatorService.selectByUsername("raul.perez")).thenReturn(op);
        when(makiMapper.toOperatorDTO(op)).thenReturn(buildDTO(1L, "Raul Perez", "raul.perez"));

        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/username/raul.perez").contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("raul.perez"))
                .andExpect(jsonPath("$.name").value("Raul Perez"));
    }

    @Test
    public void operatorController_searchOperators_returnsMatchingOperators() throws Exception {
        Operator op1 = new Operator("Carla Gomez", "carla.gomez", "123456");
        Operator op2 = new Operator("Carlos Lopez", "carlos.lopez", "123456");
        when(operatorService.searchByNameOrUsername("car")).thenReturn(List.of(op1, op2));
        when(makiMapper.toOperatorDTO(op1)).thenReturn(buildDTO(1L, "Carla Gomez", "carla.gomez"));
        when(makiMapper.toOperatorDTO(op2)).thenReturn(buildDTO(2L, "Carlos Lopez", "carlos.lopez"));

        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/search")
                        .param("term", "car")
                        .contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].username").value("carla.gomez"))
                .andExpect(jsonPath("$[1].username").value("carlos.lopez"));
    }

    @Test
    public void operatorController_countOperatorsByUsername_returnsCount() throws Exception {
        when(operatorService.countByUsername("carla.gomez")).thenReturn(1L);

        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/count/carla.gomez").contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(content().string("1"));
    }

    @Test
    public void operatorController_getOperatorsOrderedByName_returnsSortedList() throws Exception {
        Operator op1 = new Operator("Ana Martinez", "ana.martinez", "123456");
        Operator op2 = new Operator("Beto Suarez", "beto.suarez", "123456");
        when(operatorService.selectAllOrderedByName()).thenReturn(List.of(op1, op2));
        when(makiMapper.toOperatorDTO(op1)).thenReturn(buildDTO(1L, "Ana Martinez", "ana.martinez"));
        when(makiMapper.toOperatorDTO(op2)).thenReturn(buildDTO(2L, "Beto Suarez", "beto.suarez"));

        ResultActions result = mockMvc.perform(
                get("/api/v1/operator/ordered").contentType(MediaType.APPLICATION_JSON));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("Ana Martinez"))
                .andExpect(jsonPath("$[1].name").value("Beto Suarez"));
    }

    @Test
    public void operatorController_login_validCredentials_returnsOperator() throws Exception {
        Operator loginData = new Operator("Javier Martinez", "javier.martinez", "123456");

        when(operatorService.verifyCredentials("javier.martinez", "123456")).thenReturn(loginData);
        when(makiMapper.toOperatorDTO(loginData)).thenReturn(buildDTO(1L, "Javier Martinez", "javier.martinez"));

        ResultActions result = mockMvc.perform(
                post("/api/v1/operator/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginData)));

        result
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Javier Martinez"))
                .andExpect(jsonPath("$.username").value("javier.martinez"));
    }

    @Test
    public void operatorController_login_invalidCredentials_returns400() throws Exception {
        Operator badLogin = new Operator("Nadie", "noexiste", "wrongpass");

        when(operatorService.verifyCredentials(anyString(), anyString()))
                .thenThrow(new com.maki.web.exception.InvalidCredentialsException("Credenciales invalidas"));

        ResultActions result = mockMvc.perform(
                post("/api/v1/operator/log-in")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(badLogin)));

        result.andExpect(status().isBadRequest());
    }
}