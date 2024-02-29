package com.johanrincon.ms.users.controllers;

import com.johanrincon.ms.users.dtos.UsuarioDTO;
import com.johanrincon.ms.users.entities.Usuarios;
import com.johanrincon.ms.users.exceptions.CustomException;
import com.johanrincon.ms.users.services.UsuariosService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuariosControllerTest {

    @InjectMocks
    UsuariosController controller;

    @Mock
    UsuariosService service;


    @Test
    void usuariosController() {
    }

    @BeforeEach
    void setUp() {
        controller = new UsuariosController(service);
    }

    @Test
    void testUsuariosController() {
        when(service.registrarUsusario(any(UsuarioDTO.class))).thenReturn(mock(UsuarioDTO.class));
        ResponseEntity<UsuarioDTO> response = controller.usuariosController(mock(UsuarioDTO.class));
        Assertions.assertNotNull(response.getBody());
    }

    @Test
    void testUsuariosControllerError() {
        when(service.registrarUsusario(any(UsuarioDTO.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> controller.usuariosController(mock(UsuarioDTO.class)));
    }


}