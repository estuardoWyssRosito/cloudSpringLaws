package com.kappacomputacion.spring.ms.laws.responses;

import com.kappacomputacion.spring.ms.laws.dtos.treeui.CountryNode;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.TreeProcessNodes;
import com.kappacomputacion.spring.ms.laws.shared.ResponseObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date 10/16/2023
 */
@Getter
@Setter
public class MainLawTreeUiNodesResponse extends ResponseObject {
    TreeProcessNodes nodes;
}
