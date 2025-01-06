package com.kappacomputacion.spring.ms.laws.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.kappacomputacion.spring.ms.laws.entities.TextoLey}
 */
@Value
public class TextoLeyDto implements Serializable {
    @Size(max = 60)
    @NotNull
    @NotBlank
    @NotEmpty
    String recordId;
    @NotNull
    @NotBlank
    @Size(max = 60)
    String itemId;
    @NotNull
    @Size(max = 60)
    String fuenteId;
    @NotNull
    @Size(max = 60)
    String fuenteArticuloNumero;
    @NotNull
    @Size(max = 255)
    String fuenteArticuloNombre;
    @NotNull
    String fuenteArticuloTexto;
    int version;
    String country;

}