package com.kappacomputacion.spring.ms.laws.dtos.treeui;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date 10/18/2023
 */
@Getter
@Setter
public class TreeProcessNodes {
    List<CountryNode> nodes = new ArrayList<>();
}
