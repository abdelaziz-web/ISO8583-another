package ma.stand.iso8583.service;
import ma.stand.iso8583.Model.Cases;
import ma.stand.iso8583.Model.Case;
import ma.stand.iso8583.Model.Field;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class Iso8583Service {
    private final XmlService xmlService;

    public String processIso8583Request(String cardNumber, String processingCode, String code) {
        try {
            Cases cases = xmlService.readXmlFile();

            // Find matching case based on all fields
            Case matchingCase = cases.getCases().stream()
                    .filter(c -> {
                        var fields = c.getRequest().getFields();

                        // Check field2 (card number) match
                        boolean cardNumberMatches = fields.stream()
                                .anyMatch(f -> "2".equals(f.getId()) && cardNumber.equals(f.getValue()));

                        // Check field3 (processing code) match if it exists
                        boolean processingCodeMatches = true;
                        if (processingCode != null) {
                            processingCodeMatches = fields.stream()
                                    .filter(f -> "3".equals(f.getId()))
                                    .map(Field::getValue)
                                    .findFirst()
                                    .map(value -> value.equals(processingCode))
                                    .orElse(true);
                        }

                        // Check field11 (code) match if it exists
                        boolean codeMatches = true;
                        if (code != null) {
                            codeMatches = fields.stream()
                                    .filter(f -> "11".equals(f.getId()))
                                    .map(Field::getValue)
                                    .findFirst()
                                    .map(value -> value.equals(code))
                                    .orElse(true);
                        }

                        return cardNumberMatches && processingCodeMatches && codeMatches;
                    })
                    .findFirst()
                    .orElse(null);

            if (matchingCase != null) {
                // Get response field 39 value
                return matchingCase.getResponse().getFields().stream()
                        .filter(f -> "39".equals(f.getId()))
                        .map(Field::getValue)
                        .findFirst()
                        .orElse("96"); // Default error response if field39 not found
            }

            return "25"; // No matching case found
        } catch (Exception e) {
            log.error("Error processing ISO8583 request: ", e);
            return "96"; // System malfunction
        }
    }
}
