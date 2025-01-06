package com.kappacomputacion.spring.ms.laws.responses;

import com.kappacomputacion.spring.ms.laws.dtos.TextoLeyDto;
import com.kappacomputacion.spring.ms.laws.shared.ResponseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date 10/20/2023
 */
@Getter
@Setter
public class TextoLeyItemResponse  extends ResponseObject {
    TextoLeyDto textoLeyDto;
}
