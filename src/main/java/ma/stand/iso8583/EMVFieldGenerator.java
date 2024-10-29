package ma.stand.iso8583;

import ma.stand.iso8583.Model.iso0100;
import org.jpos.tlv.TLVList;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.ISOException;

import java.util.Random;

import static ma.stand.iso8583.service.IsoService.createIso0100FromMessage;

public class EMVFieldGenerator {

    public static TLVList generateEMVData() throws ISOException {
        TLVList tlvList = new TLVList();

        iso0100 iso =  createIso0100FromMessage();

        // Add EMV tags
        addEMVTag(tlvList, 0x9F02, "00001000");  // Amount, Authorized (1000 = $10.00)
        addEMVTag(tlvList, 0x9F1A, "0840");          // Terminal Country Code (840 = USA)
        addEMVTag(tlvList, 0x5F2A, "0840");          // Transaction Currency Code (840 = USD)
        addEMVTag(tlvList, 0x95, "00000000");      // Terminal Verification Results
        addEMVTag(tlvList, 0x9A, "241015");          // Transaction Date (YYMMDD) +
        addEMVTag(tlvList, 0x9C, iso.getProcessingCode_3());              // Transaction Type (00 = Purchase) +
        addEMVTag(tlvList, 0x5F25, "231225");       // Application Effective date
        addEMVTag(tlvList, 0x5F24, "281225");       //Application Expiration date
        addEMVTag(tlvList, 0x9F33, "E0F8C8");        // Terminal Capabilities
        addEMVTag(tlvList, 0x9F37, String.valueOf(generateRandom8DigitNumber()));      // Unpredictable Number

        return tlvList;
    }
    public static int generateRandom8DigitNumber() {
        Random random = new Random();

        // Generate a random number between 10000000 and 99999999 (inclusive)
        return 10000000 + random.nextInt(90000000);
    }
    private static void addEMVTag(TLVList tlvList, int tag, String value) throws ISOException {
        byte[] valueBytes = ISOUtil.hex2byte(value);
        tlvList.append(tag, valueBytes);
    }

    public static String getEMVTagDescription(int tag) {
        switch (tag) {
            case 0x9F02: return "Amount, Authorized";
            case 0x9F1A: return "Terminal Country Code";
            case 0x5F2A: return "Transaction Currency Code";
            case 0x95: return "Terminal Verification Results";
            case 0x9A: return "Transaction Date";
            case 0x9C: return "Transaction Type";
            case 0x5F24: return "Application Expiration date Effective date";
            case 0x5F25: return "Application Effective date";
            case 0x9F33: return "Terminal Capabilities";
            case 0x9F37: return "Unpredictable Number";
            default: return "Unknown Tag";
        }
    }
}