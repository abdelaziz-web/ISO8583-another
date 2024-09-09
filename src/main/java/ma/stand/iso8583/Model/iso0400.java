package ma.stand.iso8583.Model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component
@ToString
@Document(collection = "m0400")
public class iso0400 {
    @Id
    private String id ;
    private String mti; // not same
    private String pan_2; //same
    private String processingCode_3; //same
    private String transactionAmount_4; //same
    private String billingAmount_6; //same
    private String transmissionDateTime_7; // not same
    private String billingConversionRate_10;// same
    private String systemTraceAuditNumber_11; // same
    private String cardExpiration_14; // same
    private String merchantType_18; // same
    private String acquirerCountryCode_19; // same
    private String posEntryMode_22; // same
    private String posConditionCode_25; // same
    private String acquirerInstIdCode_32; // same
    private String retrievalReferenceCode_37;// same
    private String Authorization_Id_Resp_38 ; // same from response 0110
    private String cardAcceptorId_41; // same
    private String cardAcceptorIdCode_42;// same
    private String cardAcceptorNameLocation_43;// same
    private String transactionCurrencyCode_49;// same
    private String billingCurrencyCode_51;// same

    private  String jsonData;

    public iso0400(String jsonData) {
        this.jsonData = jsonData;
    }


}
