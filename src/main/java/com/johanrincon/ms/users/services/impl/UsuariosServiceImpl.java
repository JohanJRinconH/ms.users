package com.johanrincon.ms.users.services.impl;

import com.johanrincon.ms.users.dtos.TelefonoDTO;
import com.johanrincon.ms.users.dtos.UsuarioDTO;
import com.johanrincon.ms.users.entities.Telefonos;
import com.johanrincon.ms.users.entities.Usuarios;
import com.johanrincon.ms.users.exceptions.CustomException;
import com.johanrincon.ms.users.repositories.UsuariosRepository;
import com.johanrincon.ms.users.services.UsuariosService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.johanrincon.ms.users.enums.InternalCodes.INTERNAL_SERVER_ERROR;
import static com.johanrincon.ms.users.enums.InternalCodes.OPERATION_FAILED;

@Service
@RequiredArgsConstructor
public class UsuariosServiceImpl implements UsuariosService {

    private final UsuariosRepository usuariosRepository;

    public UsuarioDTO registrarUsusario(UsuarioDTO body) throws CustomException {

        boolean validEmail = isValidEmail(body.getEmail());

        if (!validEmail) {
            throw new CustomException("Formato de emial invalido", OPERATION_FAILED);
        };

        Optional<Usuarios> usuario = usuariosRepository.findByEmail(body.getEmail());
        if (usuario.isPresent()) {
            throw new CustomException("Correo ya registrado", OPERATION_FAILED);
        };

        Date date = new Date();
        List<Telefonos> listTelefono = new ArrayList<>();

        for (TelefonoDTO telefono : body.getPhones()) {
            Telefonos telefonos = Telefonos.builder()
                    .number(telefono.getNumber())
                    .citycode(telefono.getCitycode())
                    .countrycode(telefono.getCountrycode())
                    .build();

            listTelefono.add(telefonos);
        }

        Usuarios usuarioNuevo =  Usuarios.builder()
                .created(date)
                .modified(date)
                .lastLogin(date)
                .email(body.getEmail())
                .nombre(body.getName())
                .password(body.getPassword())
                .token(getJWT(body.getEmail()))
                .isActive(true)
                .telefonos(listTelefono)
                .build();

        try {
            usuariosRepository.save(usuarioNuevo);
        } catch (Exception e) {
            throw new CustomException("ERROR captado al guardar en la BD", INTERNAL_SERVER_ERROR);
        }

        body.setCreated(usuarioNuevo.getCreated());
        body.setModified(usuarioNuevo.getModified());
        body.setLastLogin(usuarioNuevo.getLastLogin());
        body.setToken(usuarioNuevo.getToken());
        body.setActive(usuarioNuevo.isActive());

        return body;
    }

    private String getJWT(String email) {

        Date now = new Date();
        Date expirationDate = new Date(now.getTime() + 86400000); // 24 horas de expiración

        String SECRET_KEY = "tBOVh+GMHxhLqBpbmfOjbUdq1wAtsHhJbvObGNUFHS71YRvPNnDnxjGH5uheeR0pMZJQvXZyPNuvHGxChcOZKg==";
        return Jwts.builder()
                .setSubject(email)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();

    }

    private boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";
        return Pattern.matches(regex, email);
    }
}
