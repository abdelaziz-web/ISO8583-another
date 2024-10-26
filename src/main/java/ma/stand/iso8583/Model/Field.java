package ma.stand.iso8583.Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import lombok.Data;

import jakarta.xml.bind.annotation.*;
import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Field {
    @XmlAttribute
    private String id;

    @XmlAttribute(name = "NAME")
    private String name;

    @XmlAttribute(name = "WORDING")
    private String wording;

    @XmlAttribute
    private String value;
}