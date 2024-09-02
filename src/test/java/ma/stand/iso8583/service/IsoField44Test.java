package ma.stand.iso8583.service;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOUtil;
import org.jpos.iso.packager.GenericPackager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class IsoField44Test {

    private GenericPackager packager;

    @BeforeEach
    void setUp() throws ISOException {
      //  InputStream is = getClass().getResourceAsStream("visa.xml");
        packager = new GenericPackager(System.getenv("visa"));
    }

    @Test
    void testField44PackingAndUnpacking() throws ISOException {
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





        byte[] packed = msg.pack();

        ISOMsg unpackedMsg = new ISOMsg();
        unpackedMsg.setPackager(packager);
        unpackedMsg.unpack(packed);

        ISOMsg unpackedField44 = (ISOMsg) unpackedMsg.getComponent(44);



        assertEquals("A", unpackedField44.getString(1), "Subfield 1 mismatch");
        System.out.println(ISOUtil.hexString(unpackedField44.getBytes(1)));
        assertEquals("Y", unpackedField44.getString(2), "Subfield 2 mismatch");
        assertEquals(" ", unpackedField44.getString(3), "Subfield 3 mismatch");
        assertEquals("C", unpackedField44.getString(4), "Subfield 4 mismatch");
        assertEquals("M", unpackedField44.getString(5), "Subfield 5 mismatch");
        assertEquals("00", unpackedField44.getString(6), "Subfield 6 mismatch");
        assertEquals("0", unpackedField44.getString(7), "Subfield 7 mismatch");
        assertEquals("Y", unpackedField44.getString(8), "Subfield 8 mismatch");
        assertEquals(" ", unpackedField44.getString(9), "Subfield 9 mismatch");
        assertEquals("M", unpackedField44.getString(10), "Subfield 10 mismatch");
        assertEquals("00", unpackedField44.getString(11), "Subfield 11 mismatch");
        assertEquals("0", unpackedField44.getString(12), "Subfield 12 mismatch");
        assertEquals("3", unpackedField44.getString(13), "Subfield 13 mismatch");
        assertEquals("0000", unpackedField44.getString(14), "Subfield 14 mismatch");
    }
}