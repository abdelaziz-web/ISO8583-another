package ma.stand.iso8583.service;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IsoField44CompletePackTest {

    private GenericPackager packager;

    @BeforeEach
    void setUp() throws ISOException {
        packager = new GenericPackager(System.getenv("visa"));
    }

    @Test
    void testField44CompletePackingAndUnpacking() throws ISOException {
        ISOMsg msg = new ISOMsg();
        msg.setPackager(packager);
        msg.setMTI("0100");

        // Set field 44 and its subfields
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

        msg.set(field44);

        // Pack the message
        byte[] packed = msg.pack();
        String packedHex = ISOUtil.hexString(packed);

        // Expected packed value (this should be the correct packed representation based on your specific implementation)
        String expectedPackedHex = "0100000000000010000013415920434D30303059204D3030303330303030" ;
        System.out.println("Actual packed message  : " + packedHex);
        System.out.println("Expected packed message: " + expectedPackedHex);

        assertEquals(expectedPackedHex, packedHex, "Packed message does not match expected value");

        // Unpack and verify each subfield
        ISOMsg unpackedMsg = new ISOMsg();
        unpackedMsg.setPackager(packager);
        unpackedMsg.unpack(packed);

        ISOMsg unpackedField44 = (ISOMsg) unpackedMsg.getComponent(44);

        for (int i = 1; i <= 14; i++) {
            String expectedValue = field44.getString(i);
            String actualValue = unpackedField44.getString(i);
            assertEquals(expectedValue, actualValue, "Subfield " + i + " mismatch");
            System.out.println("Subfield " + i + " - Expected: " + expectedValue + ", Actual: " + actualValue);
        }
    }
}