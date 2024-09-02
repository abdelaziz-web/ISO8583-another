package Model;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Document(collection = "m0100")
public class iso0100 {
         @MongoId
        private String mti;
        private String pan_2;
        private String processingCode_3;
        private String transactionAmount_4;
        private String billingAmount_6;
        private String transmissionDateTime_7;
        private String billingConversionRate_10;
        private String systemTraceAuditNumber_11;
        private String cardExpiration_14;
        private String merchantType_18;
        private String acquirerCountryCode_19;
        private String posEntryMode_22;
        private String cardSequence_23;
        private String posConditionCode_25;
        private String acquirerInstIdCode_32;
        private String forwardInstIdCode_33;
        private String track2_35;
        private String retrievalReferenceCode_37;
        private String cardAcceptorId_41;
        private String cardAcceptorIdCode_42;
        private String cardAcceptorNameLocation_43;
        private String transactionCurrencyCode_49;
        private String billingCurrencyCode_51;

}
