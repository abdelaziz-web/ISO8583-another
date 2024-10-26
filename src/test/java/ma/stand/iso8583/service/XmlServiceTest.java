package ma.stand.iso8583.service;
import ma.stand.iso8583.Model.Case;
import ma.stand.iso8583.Model.Cases;
import ma.stand.iso8583.Model.Field;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class XmlServiceTest {

    @Autowired
    private XmlService xmlService;

    @Test
    public void testXmlFileReading() {
        // Read the XML file
        Cases cases = xmlService.readXmlFile();

        // Test if cases were loaded
        assertNotNull(cases);
        assertNotNull(cases.getCases());
        assertEquals(5, cases.getCases().size());

        // Test specific case details (e.g., case1)
        Case case1 = cases.getCases().get(0);
        assertEquals("case1", case1.getName());

        // Test case1 request fields
        assertNotNull(case1.getRequest());
        assertNotNull(case1.getRequest().getFields());

        // Find and test CARD NUMBER field
        Field cardNumberField = case1.getRequest().getFields().stream()
                .filter(f -> f.getId().equals("2"))
                .findFirst()
                .orElse(null);

        assertNotNull(cardNumberField);
        assertEquals("123456789876511", cardNumberField.getValue());
        assertEquals("CARD NUMBER", cardNumberField.getWording());

        // Test response field
        assertNotNull(case1.getResponse());
        Field responseField = case1.getResponse().getFields().get(0);
        assertEquals("05", responseField.getValue());
        assertEquals("39", responseField.getId());
    }
}