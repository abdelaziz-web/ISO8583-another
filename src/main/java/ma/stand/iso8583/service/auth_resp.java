 package ma.stand.iso8583.service;


 import ma.stand.iso8583.EMVFieldGenerator;
 import ma.stand.iso8583.Model.M0110repo;
 import ma.stand.iso8583.Model.iso0100;
 import ma.stand.iso8583.Model.iso0110;
 import org.jpos.iso.ISOComponent;
 import org.jpos.iso.ISOException;
 import org.jpos.iso.ISOMsg;
 import org.jpos.iso.ISOUtil;
 import org.jpos.iso.packager.GenericPackager;
 import org.jpos.tlv.TLVList;
 import org.json.JSONException;
 import org.json.JSONObject;
 import org.springframework.beans.factory.annotation.Autowired;
 import org.springframework.context.annotation.Configuration;
 import org.springframework.stereotype.Service;
 import ma.stand.iso8583.service.Iso8583Service;



 import java.nio.charset.StandardCharsets;

 import static ma.stand.iso8583.service.IsoService.createIso0100FromMessage;

 @Service
 @Configuration
 public class auth_resp {

     public ISOMsg  response = new ISOMsg();

    @Autowired
    public iso0110 isodata ;

    @Autowired
    public iso0100 iso0100;

    @Autowired
    public M0110repo repo ;


    private final Iso8583Service iso8583Service = new Iso8583Service(new XmlService());

    private static GenericPackager packager;


    public auth_resp() {
         try {

             String packagerPath = System.getenv("visa");

             System.out.println("Packager path: " + packagerPath);
             if (packagerPath == null || packagerPath.isEmpty()) {
                 throw new RuntimeException("Environment variable 'visa' is not set");
             }
             packager = new GenericPackager(packagerPath);

         } catch (ISOException e) {
             throw new RuntimeException("Failed to initialize ISO packager", e);
         }
     }

     public  ISOMsg createAuthResponse() throws Exception {


         response.setPackager(packager);

         iso0100 = createIso0100FromMessage() ;

         response.set(0, "0110");  // MTI for response
         response.set(2, iso0100.getPan_2());
         response.set(3, iso0100.getProcessingCode_3());
         response.set(4, iso0100.getTransactionAmount_4());
         response.set(7, iso0100.getTransmissionDateTime_7());
         response.set(11, iso0100.getSystemTraceAuditNumber_11());
         response.set(19, iso0100.getAcquirerCountryCode_19());
         response.set(23, iso0100.getCardSequence_23());
         response.set(25, iso0100.getPosConditionCode_25());
         response.set(32, iso0100.getAcquirerInstIdCode_32());
         response.set(37, iso0100.getRetrievalReferenceCode_37());


         String Responsecode = iso8583Service.processIso8583Request(
                 iso0100.getPan_2(),
                 iso0100.getProcessingCode_3(),
                 iso0100.getSystemTraceAuditNumber_11()) ;

         response.set(39,Responsecode);



         if(Responsecode.equals("00")){

             String numericCode = ISO8583AuthCodeGenerator.generateNumericAuthCode();
             response.set(38, numericCode);
         }

         response.set(41, iso0100.getCardAcceptorId_41());
         response.set(42, iso0100.getCardAcceptorIdCode_42());
         response.set(49, iso0100.getTransactionCurrencyCode_49());

         ISOMsg field44 = new ISOMsg(44);
         field44.set(1, "A");   // RESPONSE SOURCE/REASON CODE
         field44.set(2, "Y");   // ADDRESS VERIFICATION RESULT CODE
         field44.set(3, " ");   // RESERVED
         field44.set(4, "C");   // CARD PRODUCT TYPE
         field44.set(5, "M");   // CVV/ICVV RESULTS CODE
         field44.set(6, "00");  // PACM DIVERSION LEVEL
         field44.set(7, "0");   // PACM DIVERSION REASON CODE
         field44.set(8, "Y");   // CARD AUTHENTICATION RESULTS CODE
         field44.set(9, " ");   // RESERVED
         field44.set(10, "M");  // CVV2 RESULTS CODE
         field44.set(11, "00"); // ORIGINAL RESPONSE CODE
         field44.set(12, "0");  // CHECK SETTLEMENT CODE (U.S. ONLY)
         field44.set(13, "3");  // CAVV RESULT CODE
         field44.set(14, "0000"); // RESPONSE REASON CODE

         response.set(field44);
/*
         TLVList tlvList = EMVFieldGenerator.generateEMVData();

         // Set initial EMV indicator
         response.set("55.1", new byte[]{0x01});

         // Set the EMV data
         byte[] tlvData = tlvList.pack();
         response.set("55.2", tlvData);
*/
         printFields(response);


         return response;
     }


     public JSONObject convertToJSONObject(String authResponseAsJson) {
         try {
             return new JSONObject(authResponseAsJson);
         } catch (JSONException e) {
             // Log the error and return a default JSONObject
             System.out.println("Error parsing JSON response: {}"+e.getMessage());
             return new JSONObject();
         }
     }



     public String getAuthResponseAsJson() throws Exception {
         ISOMsg response = createAuthResponse();
         StringBuilder jsonBuilder = new StringBuilder("{");
         for (int i = 0; i <= response.getMaxField(); i++) {
             if (response.hasField(i)) {
                 if (jsonBuilder.length() > 1) {
                     jsonBuilder.append(",");
                 }
                 jsonBuilder.append("\"").append(i).append("\":");

                 if (i == 44) {
                     // Handle composite field 44
                     ISOMsg field44 = (ISOMsg) response.getComponent(44);
                     StringBuilder field44Builder = new StringBuilder("{");
                     for (int j = 1; j <= field44.getMaxField(); j++) {
                         if (field44.hasField(j)) {
                             if (field44Builder.length() > 1) {
                                 field44Builder.append(",");
                             }
                             field44Builder.append("\"").append(j).append("\":\"")
                                     .append(new String(field44.getBytes(j), StandardCharsets.UTF_8).replace("\"", "\\\"")).append("\"");
                         }
                     }
                     field44Builder.append("}");
                     jsonBuilder.append(field44Builder);
                 }
                 else {
                     // Handle regular fields
                     jsonBuilder.append("\"").append(new String(response.getBytes(i), StandardCharsets.UTF_8).replace("\"", "\\\"")).append("\"");
                 }
             }
         }
         jsonBuilder.append("}");

         return jsonBuilder.toString();
     }

     public static void printFields(ISOMsg message) throws Exception {
         for (int i = 0; i <= 128; i++) {
             if (message.hasField(i)) {
                 ISOComponent field = message.getComponent(i);
                 if (field instanceof ISOMsg) {
                     // This is a composite field
                     ISOMsg compositeField = (ISOMsg) field;
                     System.out.printf("Field %d (Composite):%n", i);
                     for (int j = 0; j <= compositeField.getMaxField(); j++) {
                         if (compositeField.hasField(j)) {
                             byte[] subfieldValue = compositeField.getBytes(j);
                             String hexValue = ISOUtil.hexString(subfieldValue);
                             System.out.printf("  Subfield %d.%d: %s%n", i, j, hexValue);
                         }
                     }
                 } else {
                     byte[] fieldValue = message.getBytes(i);
                     if (fieldValue != null) {
                         String asciiValue = new String(fieldValue);
                         byte[] bcdValue = ISOUtil.str2bcd(asciiValue, true);
                         String hexValue = ISOUtil.hexString(bcdValue);
                         System.out.printf("Field %d: %s%n", i, hexValue);
                     }
                 }

             }
         }

     }
}