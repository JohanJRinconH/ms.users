package com.johanrincon.ms.users.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.List;

@Data
@Validated
public class UsuarioDTO {

    @NotBlank
    private String name;

    @Email
    @NotBlank
    private String email;

    @NotBlank
    private String password;

    private Date created;
    private Date modified;
    private Date lastLogin;
    private String token;
    private boolean isActive;

    @Valid
    @NotEmpty
    private List<TelefonoDTO> phones;

}
