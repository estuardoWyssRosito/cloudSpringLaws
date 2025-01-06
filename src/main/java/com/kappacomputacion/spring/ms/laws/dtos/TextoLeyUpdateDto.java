package com.kappacomputacion.spring.ms.laws.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date 10/20/2023
 */
@Value
public class TextoLeyUpdateDto{
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
    boolean fuenteIdChanged;
    boolean fuenteArticuloNumeroChanged;
    boolean fuenteArticuloNombreChanged;
    boolean fuenteArticuloTextoChanged;
}
