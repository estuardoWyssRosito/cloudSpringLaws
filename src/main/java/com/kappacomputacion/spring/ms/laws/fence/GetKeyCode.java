package com.kappacomputacion.spring.ms.laws.fence;

import org.springframework.stereotype.Service;

/**
 * @company <a href="http://kappacomputacion.net">...</a> (Kappa Computacion)
 * @designer Juan Estuardo Wyss
 * @date july-08-2021
 * @author Juan Estuardo Wyss Rosito
 * @contact estuardo.wyss@kappacomputacion.com  or  estuardo.wyss@yahoo.com
 */

@Service
public class GetKeyCode {
    private KeyCode objKeyCode=null;
    private String kcKeySession="";
    private SecurityService secService=null;

    /**
     * return of the key is length 15
     */
    public synchronized  String get_keyCode()
    { //generates a unique key for this servlet session.
        objKeyCode = new KeyCode(15);
        secService = SecurityService.getInstance();
        kcKeySession = objKeyCode.getKeyCode();
        return secService.encrypt(kcKeySession);
    }

    /**
     * return of the key is variable length depends on length parameter
     * @param lenght
     */
    public synchronized  String get_keyCode(int lenght) {
        objKeyCode = new KeyCode(lenght);
        secService = SecurityService.getInstance();
        kcKeySession = objKeyCode.getKeyCode();
        return secService.encrypt(kcKeySession);
    }
}
