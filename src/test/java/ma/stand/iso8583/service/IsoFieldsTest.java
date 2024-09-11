package ma.stand.iso8583.service;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOField;
import org.jpos.iso.IFB_NUMERIC;
import org.jpos.iso.ISOUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class IsoFieldsTest {

    @Test
    void testPanField() throws ISOException {
        IFB_NUMERIC panPacker = new IFB_NUMERIC(16, "PAN", false);
        ISOField panField = new ISOField(2, "9126088172431456");
        byte[] packedPan = panPacker.pack(panField);
        assertEquals("9126088172431456", ISOUtil.hexString(packedPan));
    }

    @Test
    void testProcessingCodeField() throws ISOException {
        IFB_NUMERIC processingCodePacker = new IFB_NUMERIC(6, "Processing Code", false);
        ISOField processingCodeField = new ISOField(3, "000000");
        byte[] packedProcessingCode = processingCodePacker.pack(processingCodeField);
        assertEquals("000000", ISOUtil.hexString(packedProcessingCode));
    }

    @Test
    void testBillingCurrencyCodeField() throws ISOException {
        IFB_NUMERIC billingCurrencyCodePacker = new IFB_NUMERIC(3, "Billing Currency Code", true);
        ISOField billingCurrencyCodeField = new ISOField(51, "978");
        byte[] packedBillingCurrencyCode = billingCurrencyCodePacker.pack(billingCurrencyCodeField);
        assertEquals("0978", ISOUtil.hexString(packedBillingCurrencyCode));
    }
}