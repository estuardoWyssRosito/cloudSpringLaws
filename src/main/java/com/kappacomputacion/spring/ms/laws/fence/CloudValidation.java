package com.kappacomputacion.spring.ms.laws.fence;



import com.kappacomputacion.spring.ms.laws.i18n.CloudValidationMultiLingual;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CloudValidation {
    private String passPhrase = "****************";
    private String securityMessage="";


    private final CloudValidationMultiLingual cloudValidationMultiLingual;
    private final AesSecurityUtil aesSecurityUtil;

    public CloudValidation(CloudValidationMultiLingual cloudValidationMultiLingual, AesSecurityUtil aesSecurityUtil) {
        this.cloudValidationMultiLingual = cloudValidationMultiLingual;
        this.aesSecurityUtil = aesSecurityUtil;
    }

    private synchronized boolean validateApiKey(String apiKey, String apiKeyReceived, String userLan) {
        boolean validApiKey = apiKeyReceived.equals(apiKey);
        if(!validApiKey) {
            securityMessage = cloudValidationMultiLingual.apiKeyDidntMatchMsg(userLan) + "<br/>";
        }
        return validApiKey;
    }

    private synchronized  boolean validateCloudSecurity(String tokenFromCookie, String tokenReceived, String userLan) {
        boolean validToken = tokenReceived.equals(tokenFromCookie);
        if(!validToken) {
            securityMessage = cloudValidationMultiLingual.securityKeyDidntMatchMsg(userLan) + "<br/>";
        }
        return validToken;
    }

    public synchronized boolean validateTokens(String apiKey, String apiKeyReceived,String tokenFromCookie, String tokenReceived, String userLan) {
        return validateApiKey(apiKey, apiKeyReceived, userLan) && validateCloudSecurity(tokenFromCookie, tokenReceived, userLan);
    }

    public synchronized String getSecurityMessage() {
        return securityMessage;
    }

    /**
     * returns true if not exists the lan gotten
     * @param userLan
     * @return
     */
    public synchronized boolean validateSupportedLanguages(String userLan) {
        List<String> lans = new ArrayList<>();
        lans.add("es");
        lans.add("it");
        lans.add("fr");
        lans.add("de");
        lans.add("en");
        lans.add("pt");
        return !lans.contains(userLan);
    }

    public synchronized String nonSupportedLanMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "Usted envió un idioma no soportado por la aplicación. Lo ajustamos a ingles.";
                break;
            case "en":
                translation = "You sent a language not supported by the application. We adjust it to english.";
                break;
            case "it":
                translation = "Hai inviato una lingua non supportata dall'applicazione. Lo adattiamo all'inglese.";
                break;
            case "de":
                translation = "Sie haben eine Sprache gesendet, die von der Anwendung nicht unterstützt wird. Wir stellen es auf Englisch ein.";
                break;
            case "fr":
                translation = "Vous avez envoyé une langue non prise en charge par l'application. Nous l'ajustons à l'anglais.";
                break;
        }

        return translation;
    }

    public synchronized String decryptKeys(String data){
        System.out.println("data --> "+data);
        String decryptedData =  new String(java.util.Base64.getDecoder().decode(data));
        String dataDecrypted = "";

        if (decryptedData.split("::").length == 3) {
            String[] ikcData = decryptedData.split("::");
            String salt = ikcData[1];
            String iv = ikcData[0];
            String cypherText = ikcData[2];
            dataDecrypted = aesSecurityUtil.decrypt(salt, iv, cypherText);
        }

        return  dataDecrypted;
    }

    public synchronized String decryptTokenFormCookie(String token) {
        return decryptKeys(token);
    }

}
