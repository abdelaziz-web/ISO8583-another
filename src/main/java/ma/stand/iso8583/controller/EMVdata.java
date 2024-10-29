package ma.stand.iso8583.controller;

import ma.stand.iso8583.EMVFieldGenerator;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOUtil;
import org.jpos.tlv.TLVList;
import org.jpos.tlv.TLVMsg;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/iso8583")
public class EMVdata {

    @PostMapping("/pro")
    public ResponseEntity<Map<String, Object>> emvget() throws ISOException {
        TLVList tlvList = EMVFieldGenerator.generateEMVData();
        Map<String, Object> response = new HashMap<>();


       for( TLVMsg msg : tlvList.getTags() ){

          int tag =    msg.getTag();

         String hexValue =  tlvList.getString(tag) ;

           Map<String, String> tagInfo = new HashMap<>();
           tagInfo.put("description", EMVFieldGenerator.getEMVTagDescription(tag));
           tagInfo.put("value", hexValue);

           response.put(String.format("0x%04X", tag), tagInfo);
       }


        return ResponseEntity.ok(response);
    }
}