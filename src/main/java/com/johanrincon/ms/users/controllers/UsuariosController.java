package com.johanrincon.ms.users.controllers;

import com.johanrincon.ms.users.dtos.UsuarioDTO;
import com.johanrincon.ms.users.exceptions.CustomException;
import com.johanrincon.ms.users.services.UsuariosService;
import com.johanrincon.ms.users.utils.HeadersUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import static com.johanrincon.ms.users.enums.InternalCodes.OPERATION_FAILED;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
@Tag(name = "UsuariosController", description = "controlador para peticiones para registrar usuarios")
public class UsuariosController {

    private final UsuariosService service;

    /**
     * Controller que se encarga de peticiones para registrar usuarios
     * @return ResponseEntity<UsuarioDTO>
     */
    @Operation(summary = "Controlador para peticiones para registrar usuarios",
            responses = {
                    @ApiResponse(responseCode = "200",
                            description = "Responde un DTO del usuario registrado en caso de exito"),
                    @ApiResponse(responseCode = "400",
                            description = "Mensaje de error funcional"),
                    @ApiResponse(responseCode = "500",
                            description = "Mensaje de error de sistema"),
            })
    @PostMapping("/registrar")
    public ResponseEntity<UsuarioDTO> usuariosController(@Valid @RequestBody UsuarioDTO body) throws CustomException {

        UsuarioDTO usuario;
        try {
            usuario = service.registrarUsusario(body);
        } catch (Exception e) {
            throw new CustomException("ERROR : "+e.getMessage(), OPERATION_FAILED);
        }

        return new ResponseEntity<>(usuario,HeadersUtils.getGenericHeaders(), HttpStatus.OK);
    }

}
