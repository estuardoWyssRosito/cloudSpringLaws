package com.kappacomputacion.spring.ms.laws.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "laws_main", schema = "laws", indexes = {
        @Index(name = "uniqueLaw", columnList = "decreto_id, country, tipo_ley", unique = true),
        @Index(name = "IndexLaw", columnList = "decreto_id")
})

public class LawsMain {
    @Id
    @Size(max = 60)
    @NotNull
    @Column(name = "item_id", nullable = false, length = 60)
    private String itemId;

    @Size(max = 60)
    @NotNull
    @Column(name = "decreto_id", nullable = false, length = 60)
    private String decretoId;

    @Size(max = 2)
    @NotNull
    @Column(name = "country", nullable = false, length = 2)
    private String country;

    @Size(max = 2)
    @NotNull
    @Column(name = "lan", nullable = false, length = 2)
    private String lan;

    @Size(max = 255)
    @NotNull
    @Column(name = "decreto_nombre", nullable = false)
    private String decretoNombre;

    @Lob
    @Column(name = "decreto_descripcion")
    private String decretoDescripcion;

    @Column(name = "inicio_vigencia")
    private LocalDate inicioVigencia;

    @Column(name = "fin_vigencia")
    private LocalDate finVigencia;

    @Column(name = "fecha_publicacion")
    private LocalDate fechaPublicacion;

    @Size(max = 60)
    @Column(name = "decretante", length = 60)
    private String decretante;

    @Size(max = 60)
    @Column(name = "tipo_ley", length = 60)
    private String tipoLey;

    @Column(name = "version")
    private int version;

    @Size(max = 60)
    @NotNull
    @ColumnDefault("'law'")
    @Column(name = "iaItem", nullable = false, length = 60)
    private String iaItem;

}