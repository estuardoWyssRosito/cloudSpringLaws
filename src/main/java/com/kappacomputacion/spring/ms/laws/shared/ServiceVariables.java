package com.kappacomputacion.spring.ms.laws.shared;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class ServiceVariables {
    protected String selectError = "";
    protected String insertError = "";
    protected String updateError = "";
    protected String deleteError = "";
    protected String renderingError = "";
    protected String userAgent = "";
    protected String userActor = "";
    protected String ipAddress = "";
    protected String suiteId = "";
    protected String moduleId = "";
    protected String errSchema="";
    protected String sysLan = "es";
    protected int updated = 0;
    protected boolean errorOccurred = false;
}
