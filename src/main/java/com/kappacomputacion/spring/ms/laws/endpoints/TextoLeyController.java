package com.kappacomputacion.spring.ms.laws.endpoints;

import com.kappacomputacion.spring.ms.laws.dtos.LawsMainUpdateDto;
import com.kappacomputacion.spring.ms.laws.dtos.TextoLeyDto;
import com.kappacomputacion.spring.ms.laws.dtos.TextoLeyTreeUiDto;
import com.kappacomputacion.spring.ms.laws.dtos.TextoLeyUpdateDto;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.LawNode;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.TreeProcessNodes;
import com.kappacomputacion.spring.ms.laws.entities.LawsMain;
import com.kappacomputacion.spring.ms.laws.entities.TextoLey;
import com.kappacomputacion.spring.ms.laws.fence.CloudValidation;
import com.kappacomputacion.spring.ms.laws.i18n.CloudValidationMultiLingual;
import com.kappacomputacion.spring.ms.laws.responses.*;
import com.kappacomputacion.spring.ms.laws.services.LawsMainService;
import com.kappacomputacion.spring.ms.laws.services.TextoLeyService;
import com.kappacomputacion.spring.ms.laws.shared.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @company kappa.computacion
 * @coder estuardo.wyss
 * @date
 */
@RestController
@RequestMapping(value = "/v1/ikc-spring-admin/laws-content")
@Tag(name = "International laws content API")
@CrossOrigin(origins = "*")
public class TextoLeyController {
    private final LawsMainService lawsMainService;
    private final TextoLeyService textoLeyService;
    private final CloudValidation cloudValidation;
    private final CloudValidationMultiLingual cloudValidationMultiLingual;

    private final String defaultApiKey = "NzIwNGY5NjgzNmFiMmI5MmU0ZTY0NmQwYjdjMzM5ZmE6OmE2OTFjNWM5ZjczMDkwYmU5YTcxN2I4MmM5YTc2NTFjOjpzRmx3eHM4YkN6QlpaVUVmZzBsRmNRPT0=::NzIwNGY5NjgzNmFiMmI5MmU0ZTY0NmQwYjdjMzM5ZmE6OmE2OTFjNWM5ZjczMDkwYmU5YTcxN2I4MmM5YTc2NTFjOjpzRmx3eHM4YkN6QlpaVUVmZzBsRmNRPT0=";
    private final String defaultSecKey = "NzIwNGY5NjgzNmFiMmI5MmU0ZTY0NmQwYjdjMzM5ZmE6OmE2OTFjNWM5ZjczMDkwYmU5YTcxN2I4MmM5YTc2NTFjOjpzRmx3eHM4YkN6QlpaVUVmZzBsRmNRPT0=";

    public TextoLeyController(LawsMainService lawsMainService, TextoLeyService textoLeyService, CloudValidation cloudValidation, CloudValidationMultiLingual cloudValidationMultiLingual) {
        this.lawsMainService = lawsMainService;
        this.textoLeyService = textoLeyService;
        this.cloudValidation = cloudValidation;
        this.cloudValidationMultiLingual = cloudValidationMultiLingual;
    }

    @GetMapping("/list/nodes")
    public synchronized ResponseEntity<TextoLeyResponse> getListLawContent(
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ) {
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "@fyBemelnZlXVdghJmpF0x4wTt$nMqVa*1n";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        TextoLeyResponse ikcResponse = new TextoLeyResponse();

        if (cloudValidation.validateSupportedLanguages(userLan)) {
            // if lan is not supported by the sys we set the default lan to english thinking world-wide
            userLan = "en";
            extraMessage = "<br/>" + cloudValidation.nonSupportedLanMsg(userLan);
        }

        if (!secKeyFromCookieDecrypted.equals("**error**")) {
            if (cloudValidation.validateTokens(endPointApiKey,
                    apikeyReceivedDecrypted,
                    secKeyFromCookieDecrypted, secKeyReceivedDecrypted, userLan)) {

                ikcResponse.setSuccess(true);

                List<TextoLeyDto> nodes = this.textoLeyService.getListOfContentLaws();

                ikcResponse.setMessage(this.textoLeyService.getSelectError());

                if(!this.textoLeyService.isErrorOccurred()){
                    ikcResponse.setStatus("ok");
                    ikcResponse.setNodes(nodes);
                }else{
                    ikcResponse.setStatus("err");
                    ikcResponse.setNodes(null);
                }

            }else {
                // cloud validation issue
                ikcResponse.setSuccess(false);
                ikcResponse.setStatus("securityIssue");
                ikcResponse.setMessage(cloudValidation.getSecurityMessage() + extraMessage);

            }
        } else {
            // sec issue
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("securityIssue");
            ikcResponse.setMessage(cloudValidation.getSecurityMessage());

        }

        return ResponseEntity.ok(ikcResponse);
    }

    @GetMapping("/tree-ui/nodes")
    public synchronized ResponseEntity<NodeContentTreeUiResponse> getTreeUiContent(
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ) {
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "wWObAg6q1tU*vgpVV0Aa$fvubsGhMsmve8l";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        NodeContentTreeUiResponse ikcResponse = new NodeContentTreeUiResponse();

        if (cloudValidation.validateSupportedLanguages(userLan)) {
            // if lan is not supported by the sys we set the default lan to english thinking world-wide
            userLan = "en";
            extraMessage = "<br/>" + cloudValidation.nonSupportedLanMsg(userLan);
        }

        if (!secKeyFromCookieDecrypted.equals("**error**")) {
            if (cloudValidation.validateTokens(endPointApiKey,
                    apikeyReceivedDecrypted,
                    secKeyFromCookieDecrypted, secKeyReceivedDecrypted, userLan)) {

                ikcResponse.setSuccess(true);

                List<TextoLeyDto> nodesText = this.textoLeyService.getListOfContentLaws();

                LawNode uiNodes = this.textoLeyService.createTextoLeyForTreeUi(nodesText);

                ikcResponse.setMessage(this.textoLeyService.getSelectError());

                if(!this.textoLeyService.isErrorOccurred()){
                    ikcResponse.setStatus("ok");
                    ikcResponse.setNodes(uiNodes);
                }else{
                    ikcResponse.setStatus("err");
                    ikcResponse.setNodes(null);
                }

            }else {
                // cloud validation issue
                ikcResponse.setSuccess(false);
                ikcResponse.setStatus("securityIssue");
                ikcResponse.setMessage(cloudValidation.getSecurityMessage() + extraMessage);

            }
        } else {
            // sec issue
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("securityIssue");
            ikcResponse.setMessage(cloudValidation.getSecurityMessage());

        }

        return ResponseEntity.ok(ikcResponse);
    }

    @PostMapping("/new/content")
    public synchronized ResponseEntity<ResponseObject> insertNewLawText(
            @RequestBody TextoLeyDto newLawText,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan

    ) {
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "rsJ2QgKo4pWZaYa43s9TlSQwkdY7Co*7xY1";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);

        ResponseObject ikcResponse = new ResponseObject();
        TextoLey newEntity = null;

        if (!newLawText.getRecordId().isEmpty()) {
            LawsMain entity = lawsMainService.getLaw(newLawText.getItemId());

            ikcResponse.setSuccess(true);

            if (Objects.nonNull(entity)) {
                TextoLey entityLawText = textoLeyService.getLawText(newLawText.getRecordId());

                if (Objects.isNull(entityLawText)) {
                    newEntity = textoLeyService.saveNewLawContent(textoLeyService.createTextoLeyEntity(newLawText, entity));

                    ikcResponse.setMessage(textoLeyService.getInsertError());

                    if (Objects.nonNull(newEntity)) {
                        ikcResponse.setStatus("ok");
                        ikcResponse.setAmount(1);
                    } else {
                        ikcResponse.setStatus("err");
                        ikcResponse.setAmount(0);
                    }
                } else {
                    // law text doesn't exist
                    ikcResponse.setStatus("err");
                    ikcResponse.setMessage(newLawText.getFuenteArticuloNombre() + ", ya existe en la base de datos!!");
                    ikcResponse.setAmount(0);
                }
            } else {
                // law doesn't exist
                ikcResponse.setStatus("err");
                ikcResponse.setMessage("La ley utilizada no existe en la base de datos!!");
                ikcResponse.setAmount(0);
            }
        } else {
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("err");
            ikcResponse.setMessage("Envió llave principal vacia!!");
            ikcResponse.setAmount(0);
        }

        return ResponseEntity.ok(ikcResponse);
    }

    @PutMapping("/update/law/content/{recordId}")
    public synchronized ResponseEntity<ResponseObject> updateExistingLawText(
            @RequestBody TextoLeyUpdateDto updateDto,
            @PathVariable("recordId") String recordId,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "hEDyucUn684uGMF95epmsIP70CciISqKvuj";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);

        recordId = recordId.replace("%23","#");
        recordId = recordId.replace("%2b","+");

        ResponseObject ikcResponse = new ResponseObject();

        if (cloudValidation.validateSupportedLanguages(userLan)) {
            // if lan is not supported by the sys we set the default lan to english thinking world-wide
            userLan = "en";
            extraMessage = "<br/>" + cloudValidation.nonSupportedLanMsg(userLan);
        }

        if (!secKeyFromCookieDecrypted.equals("**error**")) {
            if (cloudValidation.validateTokens(endPointApiKey,
                    apikeyReceivedDecrypted,
                    secKeyFromCookieDecrypted, secKeyReceivedDecrypted, userLan)) {

                LawsMain entity = lawsMainService.getLaw(updateDto.getItemId());

                ikcResponse.setSuccess(true);

                if (Objects.nonNull(entity)) {
                    TextoLey managedTextoLeyEntity = textoLeyService.getEntityLawContent(recordId);
                    int newVersion = updateDto.getVersion();

                    if(Objects.nonNull(managedTextoLeyEntity)) {
                        newVersion = textoLeyService.updateLawContent(updateDto, managedTextoLeyEntity);
                    }

                    ikcResponse.setNewVersion(newVersion);
                    ikcResponse.setAmount(textoLeyService.getUpdated());
                    ikcResponse.setMessage(textoLeyService.getUpdateError());

                    if(!lawsMainService.isErrorOccurred()) {
                        ikcResponse.setStatus("ok");
                    }else{
                        ikcResponse.setStatus("err");
                    }
                } else {
                    ikcResponse.setAmount(0);
                    ikcResponse.setMessage(recordId + "<br/>Ley no existe en la base de datos.");
                    ikcResponse.setStatus("err");
                }
            }else {
                // cloud validation issue
                ikcResponse.setSuccess(false);
                ikcResponse.setStatus("securityIssue");
                ikcResponse.setMessage(cloudValidation.getSecurityMessage() + extraMessage);

            }
        }else {
            // sec issue
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("securityIssue");
            ikcResponse.setMessage(cloudValidation.getSecurityMessage());

        }

        return ResponseEntity.ok(ikcResponse);
    }

    @GetMapping("/content/{lawKey}")
    public synchronized ResponseEntity<NodeContentTreeUiResponse> getLawContentByLawKey(
            @PathVariable("lawKey") String lawKey,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan

    ) {
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "ZaYa43s9TlS$nMqVa*1nbAg6q1tU*TlSQwkdY";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        NodeContentTreeUiResponse ikcResponse = new NodeContentTreeUiResponse();
        TextoLey newEntity = null;

        lawKey = lawKey.replace("%23","#");
        lawKey = lawKey.replace("%2b","+");

        if (!lawKey.isEmpty()) {
            LawsMain entity = lawsMainService.getLaw(lawKey);

            ikcResponse.setSuccess(true);

            if (Objects.nonNull(entity)) {
                List<TextoLeyTreeUiDto> contentNodes = textoLeyService.getLawContentByLawKey(entity);

                LawNode finalNodes = textoLeyService.createTextoLeyForTreeUiWindows(contentNodes);

                ikcResponse.setStatus("ok");
                ikcResponse.setMessage("Contenidos de ley recuperados exitosamente!!");
                ikcResponse.setAmount(contentNodes.size());
                ikcResponse.setNodes(finalNodes);

            } else {
                // law doesn't exist
                ikcResponse.setStatus("err");
                ikcResponse.setMessage("La ley utilizada no existe en la base de datos!!");
                ikcResponse.setAmount(0);
            }
        } else {
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("err");
            ikcResponse.setMessage("Envió llave principal vacia!!");
            ikcResponse.setAmount(0);
        }

        return ResponseEntity.ok(ikcResponse);
    }

    @GetMapping("/get/law/content/{recordId}")
    public synchronized ResponseEntity<TextoLeyItemResponse> getLawContentByRecordId(
            @PathVariable("recordId") String recordId,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "cxG0Tdur1ujpcy0w2YwYYlm2bdfAqRMxk2T";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        TextoLeyItemResponse ikcResponse = new TextoLeyItemResponse();

        recordId = recordId.replaceAll("%23", "#");
        recordId = recordId.replaceAll("%2b", "+");
        recordId = recordId.replaceAll("%24", "$");

        if (cloudValidation.validateSupportedLanguages(userLan)) {
            // if lan is not supported by the sys we set the default lan to english thinking world-wide
            userLan = "en";
            extraMessage = "<br/>" + cloudValidation.nonSupportedLanMsg(userLan);
        }

        if (!secKeyFromCookieDecrypted.equals("**error**")) {
            if (cloudValidation.validateTokens(endPointApiKey,
                    apikeyReceivedDecrypted,
                    secKeyFromCookieDecrypted, secKeyReceivedDecrypted, userLan)) {

                ikcResponse.setSuccess(true);

                TextoLeyDto law = this.textoLeyService.getLawContent(recordId);

                ikcResponse.setMessage(this.textoLeyService.getSelectError());

                if(Objects.nonNull(law)){
                    ikcResponse.setStatus("ok");
                    ikcResponse.setTextoLeyDto(law);
                }else{
                    ikcResponse.setStatus("err");
                    ikcResponse.setTextoLeyDto(null);
                }

            }else {
                // cloud validation issue
                ikcResponse.setSuccess(false);
                ikcResponse.setStatus("securityIssue");
                ikcResponse.setMessage(cloudValidation.getSecurityMessage() + extraMessage);

            }
        }else {
            // sec issue
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("securityIssue");
            ikcResponse.setMessage(cloudValidation.getSecurityMessage());

        }

        return ResponseEntity.ok(ikcResponse);
    }

    @DeleteMapping("/delete/law/content/{recordId}")
    public synchronized ResponseEntity<ResponseObject> deleteExistingLawContent(
            @PathVariable("recordId") String recordId,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan

    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "8gN4OipKxmjzh0ewctqEt92wg@hSoIr*0br";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);

        recordId = recordId.replace("%23","#");
        recordId = recordId.replace("%2b","+");

        ResponseObject ikcResponse = new ResponseObject();

        if (cloudValidation.validateSupportedLanguages(userLan)) {
            // if lan is not supported by the sys we set the default lan to english thinking world-wide
            userLan = "en";
            extraMessage = "<br/>" + cloudValidation.nonSupportedLanMsg(userLan);
        }

        if (!secKeyFromCookieDecrypted.equals("**error**")) {
            if (cloudValidation.validateTokens(endPointApiKey,
                    apikeyReceivedDecrypted,
                    secKeyFromCookieDecrypted, secKeyReceivedDecrypted, userLan)) {

                TextoLey entity = textoLeyService.getEntityLawContent(recordId);

                ikcResponse.setSuccess(true);

                if (Objects.nonNull(entity)) {
                    boolean borrado = textoLeyService.deleteLawContent(entity);

                    ikcResponse.setMessage(textoLeyService.getDeleteError());
                    if (borrado) {
                        ikcResponse.setStatus("ok");
                        ikcResponse.setAmount(1);
                    } else {
                        ikcResponse.setStatus("err");
                        ikcResponse.setAmount(0);
                    }
                } else {
                    ikcResponse.setAmount(0);
                    ikcResponse.setMessage(recordId + "<br/>Contenido del artículo no existe en la base de datos. Proceso de eliminación se abortó");
                    ikcResponse.setStatus("err");
                }
            }else {
                // cloud validation issue
                ikcResponse.setSuccess(false);
                ikcResponse.setStatus("securityIssue");
                ikcResponse.setMessage(cloudValidation.getSecurityMessage() + extraMessage);

            }
        }else {
            // sec issue
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("securityIssue");
            ikcResponse.setMessage(cloudValidation.getSecurityMessage());

        }

        return ResponseEntity.ok(ikcResponse);
    }
}
