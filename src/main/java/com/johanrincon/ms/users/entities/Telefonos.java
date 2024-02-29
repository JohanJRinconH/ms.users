package com.johanrincon.ms.users.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "telefono")
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Telefonos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String number;
    private String citycode;
    private String countrycode;
    @ManyToOne
    private Usuarios usuario;

}
