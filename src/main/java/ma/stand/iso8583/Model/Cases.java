package ma.stand.iso8583.Model;


import jakarta.xml.bind.annotation.*;
import java.util.List;
import lombok.Data;

@Data
@XmlRootElement(name = "CASES")
@XmlAccessorType(XmlAccessType.FIELD)
public class Cases {
    @XmlElement(name = "case")
    private List<Case> cases;
}