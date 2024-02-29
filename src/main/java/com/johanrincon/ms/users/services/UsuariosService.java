package com.johanrincon.ms.users.services;

import com.johanrincon.ms.users.dtos.UsuarioDTO;
import com.johanrincon.ms.users.entities.Usuarios;
import com.johanrincon.ms.users.exceptions.CustomException;

import java.security.NoSuchAlgorithmException;

public interface UsuariosService {

    UsuarioDTO registrarUsusario(UsuarioDTO body) throws CustomException;

}
