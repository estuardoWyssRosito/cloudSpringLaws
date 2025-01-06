package com.kappacomputacion.spring.ms.laws.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "texto_ley", schema = "laws", indexes = {
        @Index(name = "dd_idx", columnList = "item_id"),
        @Index(name = "lawsIndex", columnList = "fuente_id")
})
public class TextoLey {
    @Id
    @Size(max = 60)
    @NotNull
    @Column(name = "record_id", nullable = false, length = 60)
    private String recordId;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "item_id", nullable = false)
    private LawsMain itemId;

    @Size(max = 60)
    @NotNull
    @Column(name = "fuente_id", nullable = false, length = 60)
    private String fuenteId;

    @Size(max = 60)
    @NotNull
    @Column(name = "fuente_articulo_numero", nullable = false, length = 60)
    private String fuenteArticuloNumero;

    @Size(max = 255)
    @NotNull
    @Column(name = "fuente_articulo_nombre", nullable = false)
    private String fuenteArticuloNombre;

    @NotNull
    @Lob
    @Column(name = "fuente_articulo_texto", nullable = false)
    private String fuenteArticuloTexto;

    @Column(name = "version")
    private int version;
}