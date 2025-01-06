package com.kappacomputacion.spring.ms.laws.responses;

import com.kappacomputacion.spring.ms.laws.dtos.treeui.LawNode;
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
public class NodeContentTreeUiResponse extends ResponseObject {
    LawNode nodes;
}
