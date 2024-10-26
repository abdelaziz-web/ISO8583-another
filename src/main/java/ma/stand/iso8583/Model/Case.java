package ma.stand.iso8583.Model;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Case {
    @XmlAttribute
    private String name;

    @XmlElement
    private Request request;

    @XmlElement
    private Response response;
}

