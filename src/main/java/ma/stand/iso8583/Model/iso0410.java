package ma.stand.iso8583.Model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@Document(collection = "m0410")
public class iso0410 {

    @Id
    private String id ;
    private String mti;
    private String pan_2;
    private String processingCode_3;
    private String transactionAmount_4;
    private String transmissionDateTime_7;
    private String systemTraceAuditNumber_11;
    private String acquirerCountryCode_19;
    private String posConditionCode_25;
    private String acquirerInstIdCode_32;
    private String retrievalReferenceCode_37;
    private String responseCode_39;
    private String cardAcceptorId_41;
    private String cardAcceptorIdCode_42;
    private String transactionCurrencyCode_49;
    private  String jsonData;

    public iso0410(String jsonData) {
        this.jsonData = jsonData;
    }

}
