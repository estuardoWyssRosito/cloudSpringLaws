package com.kappacomputacion.spring.ms.laws.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseObject {
    private boolean success;
    private String message;
    private String status;
    private int newVersion;
    private int amount;
}
