package com.kappacomputacion.spring.ms.laws.responses;

import com.kappacomputacion.spring.ms.laws.entities.LawsMain;
import com.kappacomputacion.spring.ms.laws.shared.ResponseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date
 */
@Getter
@Setter
public class MainLawResponse extends ResponseObject {
    LawsMain mainLaw;
}
