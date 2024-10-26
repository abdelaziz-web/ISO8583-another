package ma.stand.iso8583.service;


import jakarta.annotation.PostConstruct;
import ma.stand.iso8583.Model.M0400repo;
import ma.stand.iso8583.Model.M0410repo;
import ma.stand.iso8583.Model.iso0400;
import ma.stand.iso8583.Model.iso0410;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Formatter;


@Service
public class ReverseService {


    @Autowired
    public iso0400 isoObject;

    @Autowired
    public iso0410 iso0410;

    @Autowired
    public cancel_resp responseObject;

    @Autowired
    public M0400repo repo;

    @Autowired
    public M0410repo repo1;

    public static ISOMsg isoMsg;
    public static ISOMsg isoMsg1;
    private GenericPackager packager;



    public ISOMsg getIsoMsg() {
        return isoMsg;
    }

    @PostConstruct
    public void init() {
        try {

            packager = new GenericPackager(System.getenv("visa"));
            isoMsg = new ISOMsg();
            isoMsg.setPackager(packager);
            isoMsg1 = new ISOMsg();
            isoMsg1.setPackager(packager);
        } catch (ISOException e) {
            System.out.println("Error initializing ISOMsg: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public void extractdata(String data) {
        try {



        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void extractTopLevelFields(JSONObject json) throws ISOException {
        System.out.println("Top Level Fields:");

        System.out.println("mti: " + json.getString("mti"));
        System.out.println("pan_2: " + json.getString("pan_2"));
        System.out.println("processingCode_3: " + json.getString("processingCode_3"));
        System.out.println("transactionAmount_4: " + json.getString("transactionAmount_4"));
        System.out.println("billingAmount_6: " + json.getString("billingAmount_6"));
        System.out.println("transmissionDateTime_7: " + json.getString("transmissionDateTime_7"));
        System.out.println("billingConversionRate_10: " + json.getString("billingConversionRate_10"));
        System.out.println("systemTraceAuditNumber_11: " + json.getString("systemTraceAuditNumber_11"));
        System.out.println("cardExpiration_14: " + json.getString("cardExpiration_14"));
        System.out.println("merchantType_18: " + json.getString("merchantType_18"));
        System.out.println("acquirerCountryCode_19: " + json.getString("acquirerCountryCode_19"));
        System.out.println("posEntryMode_22: " + json.getString("posEntryMode_22"));
        System.out.println("posConditionCode_25: " + json.getString("posConditionCode_25"));
        System.out.println("acquirerInstIdCode_32: " + json.getString("acquirerInstIdCode_32"));
        System.out.println("retrievalReferenceCode_37: " + json.getString("retrievalReferenceCode_37"));
        System.out.println("Authorization_Id_Resp_38: " + json.getString("Authorization_Id_Resp_38"));
        System.out.println("cardAcceptorId_41: " + json.getString("cardAcceptorId_41"));
        System.out.println("cardAcceptorIdCode_42: " + json.getString("cardAcceptorIdCode_42"));
        System.out.println("cardAcceptorNameLocation_43: " + json.getString("cardAcceptorNameLocation_43"));




        System.out.println("transactionCurrencyCode_49: " + json.getString("transactionCurrencyCode_49"));
        System.out.println("billingCurrencyCode_51: " + json.getString("billingCurrencyCode_51"));


        isoObject.setMti(json.getString("mti"));
        isoObject.setPan_2(json.getString("pan_2"));
        isoObject.setProcessingCode_3(json.getString("processingCode_3"));
        isoObject.setTransactionAmount_4(json.getString("transactionAmount_4"));
        isoObject.setBillingAmount_6(json.getString("billingAmount_6"));
        isoObject.setTransmissionDateTime_7(json.getString("transmissionDateTime_7"));
        isoObject.setBillingConversionRate_10(json.getString("billingConversionRate_10"));
        isoObject.setSystemTraceAuditNumber_11(json.getString("systemTraceAuditNumber_11"));
        isoObject.setCardExpiration_14(json.getString("cardExpiration_14"));
        isoObject.setMerchantType_18(json.getString("merchantType_18"));
        isoObject.setAcquirerCountryCode_19(json.getString("acquirerCountryCode_19"));
        isoObject.setPosEntryMode_22(json.getString("posEntryMode_22"));
        isoObject.setPosConditionCode_25(json.getString("posConditionCode_25"));
        isoObject.setAcquirerInstIdCode_32(json.getString("acquirerInstIdCode_32"));
        isoObject.setRetrievalReferenceCode_37(json.getString("retrievalReferenceCode_37"));
        isoObject.setAuthorization_Id_Resp_38(json.getString("Authorization_Id_Resp_38"));
        isoObject.setCardAcceptorId_41(json.getString("cardAcceptorId_41"));
        isoObject.setCardAcceptorIdCode_42(json.getString("cardAcceptorIdCode_42"));
        isoObject.setCardAcceptorNameLocation_43(json.getString("cardAcceptorNameLocation_43"));
        isoObject.setTransactionCurrencyCode_49(json.getString("transactionCurrencyCode_49"));
        isoObject.setBillingCurrencyCode_51(json.getString("billingCurrencyCode_51"));
        isoObject.setJsonData(json.getString("jsonData"));

        System.out.println("Extracted Fields:");
        System.out.println(isoObject.toString());

        inserting(isoObject);

    }

    public void inserting(iso0400 isoObject) throws ISOException{

        iso0400 savedObject = repo.save(isoObject);

        System.out.println("0400 message is inserted: " + savedObject.toString());


    }

    public iso0410 getresponse() throws Exception {

        iso0410 = responseObject.createcancelResponse(isoObject);

        iso0410.setId(null);

        insertingresponse(iso0410);

        insertingresponse(iso0410);

        return iso0410;

    }

    public void insertingresponse(iso0410 isoObject) throws ISOException {

        iso0410 savedObject = repo1.save(isoObject);

        System.out.println("0410 message is inserted: " + savedObject.toString());


    }


    public String createcancelResponsepacked() throws Exception {

        isoMsg1.setMTI("0410");

        isoMsg1.set(2, iso0410.getPan_2());
        isoMsg1.set(3, iso0410.getProcessingCode_3());
        isoMsg1.set(4, iso0410.getTransactionAmount_4());
        isoMsg1.set(7, iso0410.getTransmissionDateTime_7());
        isoMsg1.set(11, iso0410.getSystemTraceAuditNumber_11());
        isoMsg1.set(19, iso0410.getAcquirerCountryCode_19());
        isoMsg1.set(25, iso0410.getPosConditionCode_25());
        isoMsg1.set(32, iso0410.getAcquirerInstIdCode_32());
        isoMsg1.set(37, iso0410.getRetrievalReferenceCode_37());
        isoMsg1.set(39, iso0410.getResponseCode_39());  // Response code
        isoMsg1.set(41, iso0410.getCardAcceptorId_41());
        isoMsg1.set(42, iso0410.getCardAcceptorIdCode_42());
        isoMsg1.set(49, iso0410.getTransactionCurrencyCode_49());



        String hexString = ISOUtil.hexString(isoMsg1.pack());
        System.out.println("Packed message (Hex): " + hexString);

        return hexString;
    }


    public String createvancelrequestpacked() throws Exception {

        isoMsg.setMTI("0400");
        isoMsg.set(2, isoObject.getPan_2());
        isoMsg.set(3, isoObject.getProcessingCode_3());
        isoMsg.set(4, isoObject.getTransactionAmount_4());
        isoMsg.set(6, isoObject.getBillingAmount_6());
        isoMsg.set(7, isoObject.getTransmissionDateTime_7());
        isoMsg.set(10, isoObject.getBillingConversionRate_10());
        isoMsg.set(11, isoObject.getSystemTraceAuditNumber_11());
        isoMsg.set(14, isoObject.getCardExpiration_14());
        isoMsg.set(18, isoObject.getMerchantType_18());
        isoMsg.set(19, isoObject.getAcquirerCountryCode_19());
        isoMsg.set(22, isoObject.getPosEntryMode_22());
        isoMsg.set(25, isoObject.getPosConditionCode_25());
        isoMsg.set(32, isoObject.getAcquirerInstIdCode_32());
        isoMsg.set(37, isoObject.getRetrievalReferenceCode_37());
        isoMsg.set(38, isoObject.getAuthorization_Id_Resp_38());
        isoMsg.set(41, isoObject.getCardAcceptorId_41());
        isoMsg.set(42, isoObject.getCardAcceptorIdCode_42());
  //      isoMsg.set(43, isoObject.getCardAcceptorNameLocation_43());
        isoMsg.set(49, isoObject.getTransactionCurrencyCode_49());
        isoMsg.set(51, isoObject.getBillingCurrencyCode_51());

        String part1 = null;
        String part2 = null;
        String part3 = null;
        try {
            String[] parts = isoObject.getCardAcceptorNameLocation_43().split("\\*");

            if (parts.length != 3) {
                throw new IllegalArgumentException("Input string does not contain exactly three parts separated by '*'");
            }

            part1 = parts[0];
            part2 = parts[1];
            part3 = parts[2];

            System.out.println("Part 1: " + part1);
            System.out.println("Part 2: " + part2);
            System.out.println("Part 3: " + part3);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        isoMsg.set("43.1", part1);
        isoMsg.set("43.2", part2);
        isoMsg.set("43.3", part3);


        String hexString = ISOUtil.hexString(isoMsg.pack());
        System.out.println("Packed message (Hex): " + hexString);

        return hexString;
    }





    public static void printFields(ISOMsg message) throws ISOException {
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






    public static String getHexDump(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        StringBuilder ascii = new StringBuilder();
        Formatter formatter = new Formatter(result);

        for (int i = 0; i < bytes.length; i++) {
            if (i % 16 == 0) {
                if (i > 0) {
                    result.append("  ").append(ascii).append("\n");
                    ascii.setLength(0);
                }
                formatter.format("%04X: ", i);
            }
            formatter.format("%02X ", bytes[i]);
            ascii.append(Character.isISOControl(bytes[i]) ? '.' : (char) bytes[i]);
        }

        // Pad the last line if necessary
        int remaining = bytes.length % 16;
        if (remaining > 0) {
            for (int i = 0; i < (16 - remaining); i++) {
                result.append("   ");
            }
        }
        result.append("  ").append(ascii).append("\n");

        formatter.close();
        return result.toString();
    }

}
