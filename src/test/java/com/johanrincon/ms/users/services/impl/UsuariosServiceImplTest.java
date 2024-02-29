package com.johanrincon.ms.users.services.impl;

import com.johanrincon.ms.users.dtos.TelefonoDTO;
import com.johanrincon.ms.users.dtos.UsuarioDTO;
import com.johanrincon.ms.users.entities.Usuarios;
import com.johanrincon.ms.users.exceptions.CustomException;
import com.johanrincon.ms.users.repositories.UsuariosRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UsuariosServiceImplTest {

    @InjectMocks
    UsuariosServiceImpl service;

    @Mock
    UsuariosRepository usuariosRepository;

    @BeforeEach
    void setUp() {
        service = new UsuariosServiceImpl(usuariosRepository);
    }

    @Test
    void registrarUsusario() {
        UsuarioDTO usuarioDTO = service.registrarUsusario(loadBody("email@domain.com"));
        assertNotNull(usuarioDTO);
    }

    @Test
    void invalidEmailTest() {
        assertThrows(CustomException.class, () -> service.registrarUsusario(loadBody("emaildomain.com")));
    }

    @Test
    void emailYaRegistradoTest() {
        when(usuariosRepository.findByEmail(anyString())).thenReturn(Optional.of(new Usuarios()));
        assertThrows(CustomException.class, () -> service.registrarUsusario(loadBody("email@domain.com")));
    }

    @Test
    void errorAlGuardEnDBTest() {
        when(usuariosRepository.save(any(Usuarios.class))).thenThrow(RuntimeException.class);
        assertThrows(CustomException.class, () -> service.registrarUsusario(loadBody("email@domain.com")));
    }

    private UsuarioDTO loadBody(String email) {

        TelefonoDTO telefonoDTO = new TelefonoDTO();

        telefonoDTO.setNumber("1234");
        telefonoDTO.setCitycode("1234");
        telefonoDTO.setCountrycode("1234");

        List<TelefonoDTO> list = new ArrayList<>();
        list.add(telefonoDTO);

        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setEmail(email);
        usuarioDTO.setPhones(list);

        return usuarioDTO;

    }
}