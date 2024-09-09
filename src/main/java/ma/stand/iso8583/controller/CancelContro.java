package ma.stand.iso8583.controller;

import com.fasterxml.jackson.databind.node.ObjectNode;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.stand.iso8583.Model.*;
import ma.stand.iso8583.service.IsoService;
import ma.stand.iso8583.service.ReverseService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.logging.Logger;

@RequestMapping("/cancel")
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8000"})
@Tag(name = "ISO8583 Cancellation Processing", description = "API endpoints for handling ISO8583 Cancellation Message")
public class CancelContro {

    @Autowired
    private ReverseService reverseService;


    @PostMapping("/submit")
    @Operation(summary = "Submit cancellation data", description = "Endpoint to handle cancellation data submission")
    @ApiResponse(responseCode = "200", description = "Cancellation data processed successfully")
    public ResponseEntity<String> submitCancellationData(@RequestBody String data) {
        System.out.println("Received cancellation data: " + data);

        try {
            JSONObject jsonObject = new JSONObject(data);
            reverseService.extractTopLevelFields(jsonObject);

            try {
                JSONObject json = new JSONObject(data);
                iso0410 iso = reverseService.getresponse();

                JSONObject responseJson = new JSONObject();
                responseJson.put("mti", iso.getMti());
                responseJson.put("pan_2", iso.getPan_2());
                responseJson.put("processingCode_3", iso.getProcessingCode_3());
                responseJson.put("transactionAmount_4", iso.getTransactionAmount_4());
                responseJson.put("transmissionDateTime_7", iso.getTransmissionDateTime_7());
                responseJson.put("systemTraceAuditNumber_11", iso.getSystemTraceAuditNumber_11());
                responseJson.put("acquirerCountryCode_19", iso.getAcquirerCountryCode_19());
                responseJson.put("posConditionCode_25", iso.getPosConditionCode_25());
                responseJson.put("acquirerInstIdCode_32", iso.getAcquirerInstIdCode_32());
                responseJson.put("retrievalReferenceCode_37", iso.getRetrievalReferenceCode_37());
                responseJson.put("responseCode_39", iso.getResponseCode_39());
                responseJson.put("cardAcceptorId_41", iso.getCardAcceptorId_41());
                System.out.println( iso.getCardAcceptorIdCode_42() );
                System.out.println( iso.getTransactionCurrencyCode_49());

                responseJson.put("cardAcceptorIdCode_42", iso.getCardAcceptorIdCode_42());
                responseJson.put("transactionCurrencyCode_49", iso.getTransactionCurrencyCode_49());


                return ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(responseJson.toString());
            } catch (Exception e) {
                e.printStackTrace();
                JSONObject errorJson = new JSONObject();
                errorJson.put("error", "Error processing cancellation data: " + e.getMessage());
                return ResponseEntity.badRequest()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(errorJson.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("{\"error\": \"Error processing cancellation data: " + e.getMessage() + "\"}");
        }
    }



    @PostMapping("/gethexformat")
    @Operation(summary = "Submit cancellation data", description = "Endpoint to handle cancellation data submission")
    @ApiResponse(responseCode = "200", description = "Cancellation data processed successfully")
    public ResponseEntity<String > gethexformat(@RequestBody(required = false) String data) throws Exception {




        String hex0400 = reverseService.createvancelrequestpacked();
        String hex0410 = reverseService.createcancelResponsepacked();



        JSONObject response = new JSONObject();
        response.put("hex0400", hex0400);
        response.put("hex0410", hex0410);

        System.out.println(response.toString());

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response.toString());


    }

}