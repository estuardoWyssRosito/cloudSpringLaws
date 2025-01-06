/*
 * Password generator
 *
 */
package com.kappacomputacion.spring.ms.laws.fence;

import org.springframework.stereotype.Service;

@Service
public class KeyCode {
    private String keyCodeReturn=null;
    private int keyLength=0;

    public  KeyCode(){
        keyLength=15;
        keyCodeReturn=randomKey();
    }
    public  KeyCode(int length){
        keyLength=length;
        keyCodeReturn=randomKey();
    }

    private synchronized String randomKey(){
        String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        String pass = "";

        try{
            int ii=0;
            for (int x=0;x<keyLength;x++){
                Double i = Math.floor(Math.random() * 62);
                ii = i.intValue();
                pass += chars.charAt(ii);
            }
        }catch  (NumberFormatException numE){
            pass=numE.getMessage();
        }
        return pass;
    }

    public String getKeyCode(){
        return keyCodeReturn;
    }

    public String generateKeyCode(){
        return randomKey();
    }
}
