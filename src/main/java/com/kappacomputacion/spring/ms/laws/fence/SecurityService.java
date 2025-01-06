package com.kappacomputacion.spring.ms.laws.fence;

import java.util.Base64;

public class SecurityService {
    private static SecurityService instance;

    private SecurityService(){
    }

    public synchronized String encrypt(String plaintext){
        return Base64.getEncoder().encodeToString(plaintext.getBytes());
    }

    public static synchronized SecurityService getInstance(){
        if(instance == null){
            instance=new SecurityService();
        }
        return instance;
    }
}
