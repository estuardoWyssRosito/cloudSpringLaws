package com.kappacomputacion.spring.ms.laws.endpoints;

import com.kappacomputacion.spring.ms.laws.dtos.LawsMainDto;
import com.kappacomputacion.spring.ms.laws.dtos.LawsMainUpdateDto;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.CountryNode;
import com.kappacomputacion.spring.ms.laws.dtos.treeui.TreeProcessNodes;
import com.kappacomputacion.spring.ms.laws.entities.LawsMain;
import com.kappacomputacion.spring.ms.laws.fence.CloudValidation;
import com.kappacomputacion.spring.ms.laws.i18n.CloudValidationMultiLingual;
import com.kappacomputacion.spring.ms.laws.responses.MainLawListResponse;
import com.kappacomputacion.spring.ms.laws.responses.MainLawResponse;
import com.kappacomputacion.spring.ms.laws.responses.MainLawTreeUiNodesResponse;
import com.kappacomputacion.spring.ms.laws.services.LawsMainService;
import com.kappacomputacion.spring.ms.laws.shared.ResponseObject;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @company laws.app
 * @coder estuardo.wyss
 * @date
 */
@RestController
@RequestMapping(value = "/v1/ikc-spring-admin/laws")
@Tag(name = "International laws API")
@CrossOrigin(origins = "*")
public class LawsController {

    private final LawsMainService lawsMainService;
    private final CloudValidation cloudValidation;
    private final CloudValidationMultiLingual cloudValidationMultiLingual;

    private final String defaultApiKey = "NzIwNGY5NjgzNmFiMmI5MmU0ZTY0NmQwYjdjMzM5ZmE6OmE2OTFjNWM5ZjczMDkwYmU5YTcxN2I4MmM5YTc2NTFjOjpzRmx3eHM4YkN6QlpaVUVmZzBsRmNRPT0=::NzIwNGY5NjgzNmFiMmI5MmU0ZTY0NmQwYjdjMzM5ZmE6OmE2OTFjNWM5ZjczMDkwYmU5YTcxN2I4MmM5YTc2NTFjOjpzRmx3eHM4YkN6QlpaVUVmZzBsRmNRPT0=";
    private final String defaultSecKey = "NzIwNGY5NjgzNmFiMmI5MmU0ZTY0NmQwYjdjMzM5ZmE6OmE2OTFjNWM5ZjczMDkwYmU5YTcxN2I4MmM5YTc2NTFjOjpzRmx3eHM4YkN6QlpaVUVmZzBsRmNRPT0=";


    public LawsController(
            LawsMainService lawsMainService,
            CloudValidation cloudValidation1,
            CloudValidationMultiLingual cloudValidationMultiLingual1) {
        this.lawsMainService = lawsMainService;
        this.cloudValidation = cloudValidation1;
        this.cloudValidationMultiLingual = cloudValidationMultiLingual1;
    }

    @PostMapping("/new/law")
    public ResponseEntity<ResponseObject> insertNewLaw(
        @RequestBody LawsMainDto newLaw,
        @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
        @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
        @DefaultValue("es") @RequestParam("userLan") String userLan

    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "zBYleH@K25s40Bkoe2DgZ9ml@b9Cq2*mgn5";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        ResponseObject ikcResponse = new ResponseObject();
        LawsMain newEntity = null;

        if (cloudValidation.validateSupportedLanguages(userLan)) {
            // if lan is not supported by the sys we set the default lan to english thinking world-wide
            userLan = "en";
            extraMessage = "<br/>" + cloudValidation.nonSupportedLanMsg(userLan);
        }

        if (!secKeyFromCookieDecrypted.equals("**error**")) {
            if (cloudValidation.validateTokens(endPointApiKey,
                    apikeyReceivedDecrypted,
                    secKeyFromCookieDecrypted, secKeyReceivedDecrypted, userLan)) {

                LawsMain entity = lawsMainService.getLaw(newLaw.getItemId());

                if (Objects.isNull(entity)) {
                    newEntity = lawsMainService.saveNewLaw(lawsMainService.createLawsMainEntity(newLaw));
                }

                ikcResponse.setSuccess(true);

                if (Objects.nonNull(newEntity)) {
                    ikcResponse.setStatus("ok");
                    ikcResponse.setMessage(lawsMainService.getInsertError());
                    ikcResponse.setAmount(1);
                } else {
                    ikcResponse.setStatus("err");
                    ikcResponse.setMessage(entity.getDecretoNombre() + ", ya existe en la base de datos!!");
                    ikcResponse.setAmount(0);
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



    @PutMapping("/update/law/{lawKey}")
    public ResponseEntity<ResponseObject> updateExistingLaw(
            @RequestBody LawsMainUpdateDto updateLaw,
            @PathVariable("lawKey") String lawKey,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "z6ov30ErcuUrXeqCcbQtl6B845ksa7pygtm";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);

        lawKey = lawKey.replace("%23","#");
        lawKey = lawKey.replace("%2b","+");

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

                LawsMain entity = lawsMainService.getLaw(updateLaw.getItemId());

                ikcResponse.setSuccess(true);

                if (Objects.nonNull(entity)) {
                    int newVersion = lawsMainService.updateLawJpaStandard(updateLaw, entity);

                    ikcResponse.setNewVersion(newVersion);
                    ikcResponse.setAmount(lawsMainService.getUpdated());
                    ikcResponse.setMessage(lawsMainService.getUpdateError());

                    if(!lawsMainService.isErrorOccurred()) {
                        ikcResponse.setStatus("ok");
                    }else{
                        ikcResponse.setStatus("err");
                    }
                } else {
                    ikcResponse.setAmount(0);
                    ikcResponse.setMessage(lawKey + "<br/>Ley no existe en la base de datos.");
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

    @DeleteMapping("/delete/law/{lawKey}")
    public ResponseEntity<ResponseObject> deleteExistingLaw(
            @PathVariable("lawKey") String lawKey,
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

        lawKey = lawKey.replace("%23","#");
        lawKey = lawKey.replace("%2b","+");

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

                LawsMain entity = lawsMainService.getLaw(lawKey);

                ikcResponse.setSuccess(true);

                if (Objects.nonNull(entity)) {
                    boolean borrado = lawsMainService.deleteLaw(lawKey);

                    ikcResponse.setMessage(lawsMainService.getDeleteError());
                    if (borrado) {
                        ikcResponse.setStatus("ok");
                        ikcResponse.setAmount(1);
                    } else {
                        ikcResponse.setStatus("err");
                        ikcResponse.setAmount(0);
                    }
                } else {
                    ikcResponse.setAmount(0);
                    ikcResponse.setMessage(lawKey + "<br/>Ley no existe en la base de datos. Proceso de eliminación se abortó");
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

    @GetMapping("/get/law/{lawKey}")
    public ResponseEntity<MainLawResponse> getLaw(
            @PathVariable("lawKey") String lawKey,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "wohu@FLM8o9Z$HqirXgZEyQzVcgzoCDJMe3";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        MainLawResponse ikcResponse = new MainLawResponse();

        lawKey = lawKey.replace("%23", "#");
        lawKey = lawKey.replace("%2b", "+");

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

                LawsMain law = this.lawsMainService.getLaw(lawKey);

                ikcResponse.setMessage(this.lawsMainService.getSelectError());

                if(!this.lawsMainService.isErrorOccurred()){
                    ikcResponse.setStatus("ok");
                    ikcResponse.setMainLaw(law);
                }else{
                    ikcResponse.setStatus("err");
                    ikcResponse.setMainLaw(null);
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

    @GetMapping("/tree-ui/nodes")
    public ResponseEntity<MainLawTreeUiNodesResponse> getLawsForTreeNodesUI(
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "u4jYTy5pDnhPHKt1ob2hVQra5Ov*AVPa2NY";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        MainLawTreeUiNodesResponse ikcResponse = new MainLawTreeUiNodesResponse();

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

                List<LawsMain> lawsList = this.lawsMainService.getLawsForTreeNodesUI();

                TreeProcessNodes nodes = this.lawsMainService.createEntityForTreeUiNodes(lawsList);

                ikcResponse.setMessage(this.lawsMainService.getSelectError());

                if(!this.lawsMainService.isErrorOccurred()){
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
        }else {
            // sec issue
            ikcResponse.setSuccess(false);
            ikcResponse.setStatus("securityIssue");
            ikcResponse.setMessage(cloudValidation.getSecurityMessage());

        }

        return ResponseEntity.ok(ikcResponse);
    }

    @GetMapping("/list/nodes/{country}")
    public ResponseEntity<MainLawListResponse> getLawsListByCountry(
            @PathVariable("country") String country,
            @DefaultValue(defaultApiKey) @RequestParam("apikey") String apikeyReceived,
            @DefaultValue(defaultSecKey) @RequestParam("secKey") String secKeyReceived,
            @DefaultValue("es") @RequestParam("userLan") String userLan
    ){
        String[] ikcData = apikeyReceived.split("::");
        String endPointApiKey = "l4l2nYLOxx8vy1cJvBeZ$pqwdEw839mDlC7";
        String extraMessage = "";
        String apikeyReceivedDecrypted = cloudValidation.decryptKeys(ikcData[0]);
        String secKeyReceivedDecrypted = cloudValidation.decryptKeys(secKeyReceived);
        String secKeyFromCookie = ikcData[1];
        String secKeyFromCookieDecrypted = cloudValidation.decryptKeys(secKeyFromCookie);
        MainLawListResponse ikcResponse = new MainLawListResponse();


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

                List<LawsMain> laws = this.lawsMainService.getLawsForTreeNodesUI();

                ikcResponse.setMessage(this.lawsMainService.getSelectError());

                if(!this.lawsMainService.isErrorOccurred()){
                    ikcResponse.setStatus("ok");
                    ikcResponse.setLaws(laws);
                }else{
                    ikcResponse.setStatus("err");
                    ikcResponse.setLaws(null);
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

    @GetMapping("/ping")
    public String ping() {
        return "Admin for International laws is working fine!!";
    }
}
