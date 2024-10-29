package ma.stand.iso8583.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import ma.stand.iso8583.Model.M0100repo;
import ma.stand.iso8583.Model.M0110repo;
import ma.stand.iso8583.Model.iso0100;
import ma.stand.iso8583.Model.iso0110;
import ma.stand.iso8583.service.IsoService ;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.json.JSONArray;
import org.json.JSONObject;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ma.stand.iso8583.cache.cache;
import ma.stand.iso8583.service.auth_resp;

import  ma.stand.iso8583.service.IsoService;

import static ma.stand.iso8583.service.IsoService.*;

@RequestMapping("/auth")
@RestController
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8000"})
@Tag(name = "ISO8583 Message Processing", description = "API endpoints for handling various ISO8583 message components")
public class IsoController {

    @Autowired
    private IsoService isoService;

    @Autowired
    private auth_resp auth_res;

    @Autowired
    private cache cache ;

    @Autowired
    public iso0110 isodata ;

    @Autowired
    public iso0100 isorequest ;

    @Autowired
    public M0110repo repo ;

    @Autowired
    public M0100repo repo1 ;

    @Operation(summary = "Process MTI (Message Type Indicator)",
            description = "Extracts and processes the MTI from the incoming ISO8583 message")
    @ApiResponse(responseCode = "200", description = "MTI processed successfully")
    @ApiResponse(responseCode = "400", description = "No MTI data in request")
    @PostMapping("/mti")
    public ResponseEntity<String> handleMtiRequest(@RequestBody String requestBody) {
        JSONObject requestJson = new JSONObject(requestBody);
        if (requestJson.has("mti")) {
            JSONArray mtiArray = requestJson.getJSONArray("mti");

            cache.setProcessedMti(mtiArray);
            System.out.println("\nmti formatting:");

            isoService.extractMti(mtiArray);
            return ResponseEntity.ok(mtiArray.toString(4));
        } else {
            return ResponseEntity.badRequest().body("No MTI data in request");
        }
    }

    @Operation(summary = "Process Card Data",
            description = "Extracts and processes card-related information from the ISO8583 message")
    @ApiResponse(responseCode = "200", description = "Card data processed successfully")
    @ApiResponse(responseCode = "400", description = "No Card data in request")
    @PostMapping("/cards")
    public ResponseEntity<String> handleCardRequest(@RequestBody String requestBody) {
        JSONObject requestJson = new JSONObject(requestBody);
        if (requestJson.has("cards")) {
            JSONArray cardArray = requestJson.getJSONArray("cards");

            cache.setProcessedCards(cardArray);
            System.out.println("\ncards formatting:");

            isoService.extractCard(cardArray);
            return ResponseEntity.ok(cardArray.toString(4));
        } else {
            return ResponseEntity.badRequest().body("No Card data in request");
        }
    }

    @Operation(summary = "Process Transaction Data",
            description = "Extracts and processes transaction-specific information from the ISO8583 message")
    @ApiResponse(responseCode = "200", description = "Transaction data processed successfully")
    @ApiResponse(responseCode = "400", description = "No Transaction data in request")
    @PostMapping("/transac")
    public ResponseEntity<String> handleTransacRequest(@RequestBody String requestBody) {
        JSONObject requestJson = new JSONObject(requestBody);
        if (requestJson.has("transac")) {
            JSONArray transacArray = requestJson.getJSONArray("transac");

            cache.setProcessedTransac(transacArray);
            System.out.println("\ntransac formatting:");
            isoService.extractTransac(transacArray);
            return ResponseEntity.ok(transacArray.toString(4));
        } else {
            return ResponseEntity.badRequest().body("No Transac data in request");
        }
    }

    @Operation(summary = "Process Acquirer Data",
            description = "Extracts and processes acquirer-related information from the ISO8583 message")
    @ApiResponse(responseCode = "200", description = "Acquirer data processed successfully")
    @ApiResponse(responseCode = "400", description = "No acquirer data in request")
    @PostMapping("/Acq")
    public ResponseEntity<String> handleAcqRequest(@RequestBody String requestBody) {
        JSONObject requestJson = new JSONObject(requestBody);
        if (requestJson.has("acq")) {
            JSONArray acqArray = requestJson.getJSONArray("acq");

            cache.setProcessedAcq(acqArray);
            System.out.println("\nacquerir formatting:");
            isoService.extractAcq(acqArray);


            return ResponseEntity.ok( acqArray.toString(4));
        } else {
            return ResponseEntity.badRequest().body("No acq data in request");
        }
    }

    @Operation(summary = "Get Combined Response",
            description = "Retrieves a combined response of all processed ISO8583 message components")
    @ApiResponse(responseCode = "200", description = "Combined response retrieved successfully")
    @ApiResponse(responseCode = "400", description = "No processed data available")
    @GetMapping("/combined-response")
    public ResponseEntity<String> handleCombinedResponse() throws Exception {


        ISOMsg iso =  new ISOMsg() ;

        iso = auth_res.createAuthResponse() ;

        isodata = fill0110(isodata,iso);

        isodata.setJsonData(auth_res.getAuthResponseAsJson());

        repo.save(isodata) ;

        isodata.setJsonData(auth_res.getAuthResponseAsJson());

        JSONObject combinedResponse = new JSONObject();

        JSONObject response =  auth_res.convertToJSONObject(auth_res.getAuthResponseAsJson()) ;

        JSONArray responseArray = new JSONArray();

        responseArray.put(response);

        combinedResponse.put("response",responseArray) ;

        if (cache.getProcessedMti() != null) {
            combinedResponse.put("mti", cache.getProcessedMti());

        }

        if (cache.getProcessedCards() != null) {
            combinedResponse.put("cards", cache.getProcessedCards());

        }

        if (cache.getProcessedTransac() != null) {
            combinedResponse.put("transac", cache.getProcessedTransac());

        }

        if (cache.getProcessedAcq() != null) {
            combinedResponse.put("acq", cache.getProcessedAcq());

        }

        if (combinedResponse.length() > 0) {
            // Reset the processed data after combining
            cache.setProcessedMti(null);
            cache.setProcessedCards(null);
            cache.setProcessedTransac(null);
            cache.setProcessedAcq(null);

            isorequest = createIso0100FromMessage() ;
            isorequest.setJsonData(combinedResponse.toString(4));

            System.out.println("i m here " +combinedResponse);

            repo1.save(isorequest) ;

            return ResponseEntity.ok( combinedResponse.toString(4));

        } else {
          //  return ResponseEntity.badRequest().body(new JSONObject().put("error", "No processed data available"));
            return null;
        }
    }

    @Operation(summary = "Get Hexadecimal Representation",
            description = "Retrieves the hexadecimal representation of the ISO8583 message and response")
    @ApiResponse(responseCode = "200", description = "Hexadecimal representation retrieved successfully")
    @GetMapping("/hex")
    public ResponseEntity<String> handlehex() throws Exception {



        JSONObject message = new JSONObject();

        String hexres = ISOUtil.hexString(auth_res.createAuthResponse().pack());

        String hexreq = ISOUtil.hexString(isoMsg.pack());

        message.put("hexreq", hexreq);

        message.put("hexres", hexres);

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(message.toString());


    }

}