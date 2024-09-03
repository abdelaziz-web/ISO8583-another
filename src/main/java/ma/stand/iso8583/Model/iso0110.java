package Model;


import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "m0100")
@Component

public class iso0110 {

    @MongoId
    private String mti;
    private String pan_2;
    private String processingCode_3;
    private String transactionAmount_4;
    private String transmissionDateTime_7;
    private String systemTraceAuditNumber_11;
    private String acquirerCountryCode_19;
    private String cardSequence_23;
    private String posConditionCode_25;
    private String acquirerInstIdCode_32;
    private String retrievalReferenceNumber_37;
    private String authorizationIdResponse_38;
    private String responseCode_39;
    private String cardAcceptorTerminalId_41;
    private String cardAcceptorIdCode_42;
    private String additionalResponseData_44;
    private String transactionCurrencyCode_49;

    private  String jsonData;

    public iso0110(String jsonData) {
        this.jsonData = jsonData;
    }

}
