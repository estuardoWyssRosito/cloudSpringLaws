package com.kappacomputacion.spring.ms.laws.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date
 */
@Getter
@Setter
public class LawsMainUpdateDto implements Serializable {
    @Size(max = 60)
    @NotNull
    String itemId;
    @Size(max = 60)
    @NotNull
    @NotBlank
    String iaItem;
    @NotNull
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
    @Min(value = 0, message = "Value must be greater than or equal to 0")
    int version;

    boolean decretoIdChanged;
    boolean countryChanged;
    boolean lanChanged;
    boolean decretoNombreChanged;
    boolean decretoDescripcionChanged;
    boolean inicioVigenciaChanged;
    boolean finVigenciaChanged;
    boolean fechaPublicacionChanged;
    boolean decretanteChanged;
    boolean tipoLeyChanged;
    boolean iaItemChanged;
}
