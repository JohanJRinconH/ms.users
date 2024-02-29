package com.johanrincon.ms.users.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TelefonoDTO {

    @NotBlank
    private String number;

    @NotBlank
    private String citycode;

    @NotBlank
    private String countrycode;

}
