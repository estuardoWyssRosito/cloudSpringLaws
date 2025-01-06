package com.kappacomputacion.spring.ms.laws.dtos.treeui;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date 10/07/2023
 */
@Getter
@Setter
public class LawNode {
    String parent;
    String id;
    String name;
    List<LawContentChild> children= new ArrayList<>();
}
