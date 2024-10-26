package ma.stand.iso8583.Model;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import lombok.Data;

import java.util.List;

import jakarta.xml.bind.annotation.*;
import lombok.Data;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class Response {
    @XmlElement(name = "field")
    private List<Field> fields;
}