package ma.stand.iso8583.service;


import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Unmarshaller;
import ma.stand.iso8583.Model.Cases;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import lombok.extern.slf4j.Slf4j;
import java.io.File;

@Service
@Slf4j
public class XmlService {

    public Cases readXmlFile() {
        try {
            File file = ResourceUtils.getFile("classpath:cases.xml");
            JAXBContext jaxbContext = JAXBContext.newInstance(Cases.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            return (Cases) unmarshaller.unmarshal(file);
        } catch (Exception e) {
            log.error("Error reading XML file: ", e);
            throw new RuntimeException("Failed to read XML file", e);
        }
    }
}