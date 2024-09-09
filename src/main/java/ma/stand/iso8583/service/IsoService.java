package ma.stand.iso8583.service;

import jakarta.annotation.PostConstruct;
import org.jpos.iso.ISOComponent;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Formatter;


import java.io.InputStream;

@Service
public class IsoService {


    public static ISOMsg isoMsg;
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
        } catch (ISOException e) {
            System.out.println("Error initializing ISOMsg: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void extractMti(JSONArray mtiJson) {
        try {
            JSONObject mtiObject = mtiJson.getJSONObject(0);
            String mti = mtiObject.getString("selectedOption");
            isoMsg.setMTI(mti);
            System.out.println("MTI: " + mti);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractCard(JSONArray cardJson) {
        try {
            JSONObject cardObject = cardJson.getJSONObject(0);
            JSONObject values = cardObject.getJSONObject("values");

            isoMsg.set(35, values.getString("track2"));
            isoMsg.set(23, String.valueOf(values.getInt("cardSequence")));
            isoMsg.set(2, values.getString("pan"));
            isoMsg.set(14, values.getString("cardExpiration"));

            System.out.println("Card Data:");
            System.out.println("Track2: " + values.getString("track2"));
            System.out.println("Card Sequence: " + values.getInt("cardSequence"));
            System.out.println("PAN: " + values.getString("pan"));
            System.out.println("Card Expiration: " + values.getString("cardExpiration"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractTransac(JSONArray transacJson) {
        try {
            JSONObject transacObject = transacJson.getJSONObject(0);
            JSONObject values = transacObject.getJSONObject("values");

            isoMsg.set(7, values.getString("transmissionDateTime"));
            isoMsg.set(11, values.getString("systemTraceAuditNumber"));
            isoMsg.set(6, values.getString("billingAmount"));
            isoMsg.set(3, values.getString("processingCode"));
            isoMsg.set(4, values.getString("transactionAmount"));
            isoMsg.set(25, values.getString("posConditionCode"));
            isoMsg.set(49, values.getString("transactionCurrencyCode"));
            isoMsg.set(22, values.getString("posEntryMode"));
            isoMsg.set(37, values.getString("retrievalReferenceCode"));
            isoMsg.set(51, values.getString("billingCurrencyCode"));
            isoMsg.set(10, values.getString("billingConversionRate"));

            System.out.println("\nTransaction Data:");
            System.out.println("Transmission Date Time: " + values.getString("transmissionDateTime"));
            System.out.println("System Trace Audit Number: " + values.getString("systemTraceAuditNumber"));
            System.out.println("Billing Amount: " + values.getString("billingAmount"));
            System.out.println("Processing Code: " + values.getString("processingCode"));
            System.out.println("Transaction Amount: " + values.getString("transactionAmount"));
            System.out.println("POS Condition Code: " + values.getString("posConditionCode"));
            System.out.println("Transaction Currency Code: " + values.getString("transactionCurrencyCode"));
            System.out.println("POS Entry Mode: " + values.getString("posEntryMode"));
            System.out.println("Retrieval Reference Code: " + values.getString("retrievalReferenceCode"));
            System.out.println("Billing Currency Code: " + values.getString("billingCurrencyCode"));
            System.out.println("Billing Conversion Rate: " + values.getString("billingConversionRate"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void extractAcq(JSONArray acqJson) {
        try {
            JSONObject acqObject = acqJson.getJSONObject(0);
            JSONObject values = acqObject.getJSONObject("values");

            isoMsg.set(42, values.getString("cardAcceptorIdCode"));
            isoMsg.set(41, values.getString("cardAcceptorId"));
            isoMsg.set(19, values.getString("acquirerCountryCode"));
            isoMsg.set(33, values.getString("forwardInstIdCode"));
            isoMsg.set(32, values.getString("acquirerInstIdCode"));
            isoMsg.set(18, values.getString("merchantType"));

            String part1 = null;
            String part2 = null;
            String part3 = null;
            try {
                String[] parts = values.getString("cardAcceptorNameLocation").split("\\*");

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

            System.out.println("\nAcquirer Data:");
            System.out.println("Card Acceptor ID : " + values.getString("cardAcceptorId"));
            System.out.println("Card Acceptor ID Code : " + values.getString("cardAcceptorIdCode"));
            System.out.println("Acquirer Country Code: " + values.getString("acquirerCountryCode"));
            System.out.println("Forward Inst ID Code: " + values.getString("forwardInstIdCode"));
            System.out.println("Acquirer Inst ID Code: " + values.getString("acquirerInstIdCode"));
            System.out.println("Merchant Type: " + values.getString("merchantType"));
            System.out.println("Card Acceptor Name Location: " + values.getString("cardAcceptorNameLocation"));


            byte[] packedMessage = isoMsg.pack();


            // Display packed message length
            System.out.println("Packed message length: " + packedMessage.length);

            String hexString = ISOUtil.hexString(packedMessage);
            System.out.println("Packed message (Hex): " + hexString);



            // Display packed message in hexdump format
            System.out.println("Packed message (Hexdump):");
            System.out.println(getHexDump(packedMessage));

            printFields(isoMsg);

        } catch (Exception e) {
            e.printStackTrace();
        }
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