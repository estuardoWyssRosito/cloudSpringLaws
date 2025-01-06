package com.kappacomputacion.spring.ms.laws.i18n;

import org.springframework.stereotype.Service;

@Service
public class CloudValidationMultiLingual {

    public synchronized String apiKeyDidntMatchMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "La apiKey enviada no coincide.";
                break;
            case "en":
                translation = "Sent apiKey does not match.";
                break;
            case "it":
                translation = "L'apiKey inviata non corrisponde.";
                break;
            case "de":
                translation = "Gesendeter apiKey stimmt nicht überein.";
                break;
            case "fr":
                translation = "L'apiKey envoyée ne correspond pas.";
                break;
        }

        return translation;
    }

    public synchronized String securityKeyDidntMatchMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "La llave de seguridad enviada no coincide.";
                break;
            case "en":
                translation = "The security key sent does not match.";
                break;
            case "it":
                translation = "La chiave di sicurezza inviata non corrisponde.";
                break;
            case "de":
                translation = "Der gesendete Sicherheitsschlüssel stimmt nicht überein.";
                break;
            case "fr":
                translation = "La clé de sécurité envoyée ne correspond pas.";
                break;
        }

        return translation;
    }

    public synchronized String problemTransferingInfoMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "Hubo un problema en la transferencia de información.";
                break;
            case "en":
                translation = "There was a problem transferring information.";
                break;
            case "it":
                translation = "Si è verificato un problema durante il trasferimento delle informazioni.";
                break;
            case "de":
                translation = "Beim Übertragen von Informationen ist ein Problem aufgetreten.";
                break;
            case "fr":
                translation = "Un problème est survenu lors du transfert des informations.";
                break;
        }

        return translation;
    }

    public synchronized String databaseIsDownMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "La base de datos está caída.";
                break;
            case "en":
                translation = "Database is down.";
                break;
            case "it":
                translation = "Il database è inattivo.";
                break;
            case "de":
                translation = "Datenbank ist ausgefallen.";
                break;
            case "fr":
                translation = "La base de données est en panne.";
                break;
            case "pt":
                translation = "O banco de dados está inativo.";
                break;
        }

        return translation;
    }

    public synchronized String wrongLedgerAccountClassificationMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "El tipo de clasificación contable enviado no esta soportado por la aplicación.";
                break;
            case "en":
                translation = "The type of accounting classification sent is not supported by the application.";
                break;
            case "it":
                translation = "La tipologia di classificazione contabile inviata non è supportata dall'applicazione.";
                break;
            case "de":
                translation = "Die Art der gesendeten Buchhaltungsklassifizierung wird von der Anwendung nicht unterstützt.";
                break;
            case "fr":
                translation = "Le type de nomenclature comptable envoyé n'est pas supporté par l'application.";
                break;
            case "pt":
                translation = "O tipo de classificação contábil enviada não é suportada pelo aplicativo.";
                break;

        }

        return translation;
    }

    public synchronized String operationContextDesntMatchMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "El tipo de operacion contextual enviado no esta soportado por la aplicación.";
                break;
            case "en":
                translation = "The type of contextual operation sent is not supported by the application.";
                break;
            case "it":
                translation = "Il tipo di operazione contestuale inviata non è supportato dall'applicazione.";
                break;
            case "de":
                translation = "Die Art der gesendeten kontextabhängigen Operation wird von der Anwendung nicht unterstützt.";
                break;
            case "fr":
                translation = "Le type d'opération contextuelle envoyé n'est pas supporté par l'application.";
                break;
            case "pt":
                translation = "O tipo de operação contextual enviada não é suportada pelo aplicativo.";
                break;

        }

        return translation;
    }

    public synchronized String wrongInfoForCompanyDataMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "Los datos para la conectividad de la empresa estan equivocados.";
                break;
            case "en":
                translation = "The data for the company's connectivity is wrong.";
                break;
            case "it":
                translation = "I dati per la connettività dell'azienda sono errati.";
                break;
            case "de":
                translation = "Die Daten zur Konnektivität des Unternehmens sind falsch.";
                break;
            case "fr":
                translation = "Les données de connectivité de l'entreprise sont erronées.";
                break;
            case "pt":
                translation = "Os dados de conectividade da empresa estão errados.";
                break;

        }

        return translation;
    }

    public synchronized String wrongContextMsg(String lan) {
        String translation="";
        switch (lan){
            case "es":
                translation = "Tipo de operacion contextual incorrecta.";
                break;
            case "en":
                translation = "Incorrect type of contextual operation.";
                break;
            case "it":
                translation = "Tipo errato di operazione contestuale.";
                break;
            case "de":
                translation = "Falsche Art der kontextabhängigen Operation.";
                break;
            case "fr":
                translation = "Type d'opération contextuelle incorrect.";
                break;
            case "pt":
                translation = "Tipo incorreto de operação contextual.";
                break;

        }

        return translation;
    }


}
