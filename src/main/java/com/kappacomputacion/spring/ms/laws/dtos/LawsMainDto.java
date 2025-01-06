package com.kappacomputacion.spring.ms.laws.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * DTO for {@link com.kappacomputacion.spring.ms.laws.entities.LawsMain}
 */
@Value
public class LawsMainDto implements Serializable {
    @Size(max = 60)
    @NotNull
    @NotBlank
    String itemId;
    @NotNull
    @NotBlank
    @Size(max = 60)
    String decretoId;
    @NotNull
    @Size(max = 2)
    String country;
    @NotNull
    @Size(max = 2)
    String lan;
    @NotNull
    @Size(max = 255)
    String decretoNombre;
    String decretoDescripcion;
    LocalDate inicioVigencia;
    LocalDate finVigencia;
    LocalDate fechaPublicacion;
    @Size(max = 60)
    String decretante;
    @Size(max = 60)
    String tipoLey;
    int version;
}