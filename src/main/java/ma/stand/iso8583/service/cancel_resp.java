package ma.stand.iso8583.service;


import ma.stand.iso8583.Model.M0410repo;
import ma.stand.iso8583.Model.iso0400;
import ma.stand.iso8583.Model.iso0410;
import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class cancel_resp {


    private static GenericPackager packager;

    @Autowired
    iso0410 isoObject;


    public cancel_resp() {
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

    public  iso0410 createcancelResponse(iso0400 isodata) throws Exception {


        isoObject.setMti("0410");
        isoObject.setPan_2(isodata.getPan_2());
        isoObject.setProcessingCode_3(isodata.getProcessingCode_3());
        isoObject.setTransactionAmount_4(isodata.getTransactionAmount_4());
        isoObject.setTransmissionDateTime_7(isodata.getTransmissionDateTime_7());
        isoObject.setSystemTraceAuditNumber_11(isodata.getSystemTraceAuditNumber_11());
        isoObject.setAcquirerCountryCode_19(isodata.getAcquirerCountryCode_19());
        isoObject.setPosConditionCode_25(isodata.getPosConditionCode_25());
        isoObject.setAcquirerInstIdCode_32(isodata.getAcquirerInstIdCode_32());
        isoObject.setRetrievalReferenceCode_37(isodata.getRetrievalReferenceCode_37());
        isoObject.setResponseCode_39("00");
        isoObject.setCardAcceptorId_41(isodata.getCardAcceptorId_41());
        isoObject.setCardAcceptorIdCode_42(isodata.getCardAcceptorIdCode_42());
        isoObject.setTransactionCurrencyCode_49(isodata.getTransactionCurrencyCode_49());

      return isoObject ;
    }




}
