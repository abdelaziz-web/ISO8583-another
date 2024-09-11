package ma.stand.iso8583.service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.ISOException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IsoServiceTest {

    @Autowired
    private IsoService isoService;

    private JSONArray mtiJson;
    private JSONArray cardJson;
    private JSONArray transacJson;
    private JSONArray acqJson;

    @BeforeEach
    void setUp() {
        // Setup test data
        mtiJson = new JSONArray("[{\"selectedOption\":\"0100\"}]");
        cardJson = new JSONArray("[{\"values\":{\"track2\":\"4111111111111111=22041011000001234567\",\"cardSequence\":1,\"pan\":\"4111111111111111\",\"cardExpiration\":\"2204\"}}]");
        transacJson = new JSONArray("[{\"values\":{\"transmissionDateTime\":\"0610173030\",\"systemTraceAuditNumber\":\"123456\",\"billingAmount\":\"000000010000\",\"processingCode\":\"000000\",\"transactionAmount\":\"000000010000\",\"posConditionCode\":\"00\",\"transactionCurrencyCode\":\"840\",\"posEntryMode\":\"051\",\"retrievalReferenceCode\":\"123456789012\",\"billingCurrencyCode\":\"840\",\"billingConversionRate\":\"00001000\"}}]");
        acqJson = new JSONArray("[{\"values\":{" +
                "\"cardAcceptorId\":\"123456789012345\"," +
                "\"cardAcceptorIdCode\":\"12345678\"," + // Truncated to 8 characters
                "\"acquirerCountryCode\":\"840\"," +
                "\"forwardInstIdCode\":\"123456\"," +
                "\"acquirerInstIdCode\":\"12345678901\"," +
                "\"merchantType\":\"5411\"," +
                "\"cardAcceptorNameLocation\":\"FOOD SOCIETY PARIS         PARIS           FR\"" + // Adjusted to fit the packager
                "}}]");    }

    @Test
    void testExtractMti() throws ISOException {
        isoService.extractMti(mtiJson);
        ISOMsg isoMsg = isoService.getIsoMsg();
        assertEquals("0100", isoMsg.getMTI());
    }

    @Test
    void testExtractCard() throws ISOException {
        isoService.extractCard(cardJson);
        ISOMsg isoMsg = isoService.getIsoMsg();
        assertEquals("4111111111111111=22041011000001234567", isoMsg.getString(35));
        assertEquals("1", isoMsg.getString(23));
        assertEquals("4111111111111111", isoMsg.getString(2));
        assertEquals("2204", isoMsg.getString(14));
    }

    @Test
    void testExtractTransac() throws ISOException {
        isoService.extractTransac(transacJson);
        ISOMsg isoMsg = isoService.getIsoMsg();
        assertEquals("0610173030", isoMsg.getString(7));
        assertEquals("123456", isoMsg.getString(11));
        assertEquals("000000010000", isoMsg.getString(6));
        assertEquals("000000", isoMsg.getString(3));
        assertEquals("000000010000", isoMsg.getString(4));
        assertEquals("00", isoMsg.getString(25));
        assertEquals("840", isoMsg.getString(49));
        assertEquals("051", isoMsg.getString(22));
        assertEquals("123456789012", isoMsg.getString(37));
        assertEquals("840", isoMsg.getString(51));
        assertEquals("00001000", isoMsg.getString(10));
    }

    //@Test
   /* void testExtractAcq() throws ISOException {
        isoService.extractAcq(acqJson);
        ISOMsg isoMsg = isoService.getIsoMsg();
        assertEquals("1234567", isoMsg.getString(42));
        assertEquals("1234568", isoMsg.getString(41));
        assertEquals("840", isoMsg.getString(19));
        assertEquals("123456", isoMsg.getString(33));
        assertEquals("123567", isoMsg.getString(32));
        assertEquals("5411", isoMsg.getString(18));

    }*/
}