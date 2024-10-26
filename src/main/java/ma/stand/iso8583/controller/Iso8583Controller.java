package ma.stand.iso8583.controller;

import lombok.RequiredArgsConstructor;
import ma.stand.iso8583.service.Iso8583Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api/iso8583")
@RequiredArgsConstructor
public class Iso8583Controller {
    private final Iso8583Service iso8583Service;

    @PostMapping("/process")
    public String processRequest(@RequestBody Map<String, String> request) {
        return iso8583Service.processIso8583Request(
                request.get("cardNumber"),
                request.get("processingCode"),
                request.get("code")
        );
    }
}