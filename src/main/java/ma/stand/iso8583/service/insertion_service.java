package ma.stand.iso8583.service;

import Model.M0110repo;
import Model.iso0110;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class insertion_service {

    @Autowired
    public iso0110 isoData  ;

    @Autowired
    private M0110repo m0110repo;


    public  void save(ISOMsg response) throws Exception {

        isoData.setMti(response.getString(0));
        isoData.setPan_2(response.getString(2));
        isoData.setProcessingCode_3(response.getString(3));
        isoData.setTransactionAmount_4(response.getString(4));
        isoData.setTransmissionDateTime_7(response.getString(7));
        isoData.setSystemTraceAuditNumber_11(response.getString(11));
        isoData.setAcquirerCountryCode_19(response.getString(19));
        isoData.setCardSequence_23(response.getString(23));
        isoData.setPosConditionCode_25(response.getString(25));
        isoData.setAcquirerInstIdCode_32(response.getString(32));
        isoData.setRetrievalReferenceNumber_37(response.getString(37));
        isoData.setCardAcceptorTerminalId_41(response.getString(41));
        isoData.setCardAcceptorIdCode_42(response.getString(42));
        isoData.setTransactionCurrencyCode_49(response.getString(49));

        m0110repo.save(isoData) ;

        System.out.println("is inserted");


    }

}
