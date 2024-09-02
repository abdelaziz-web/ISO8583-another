package ma.stand.iso8583.cache;

import org.json.JSONArray;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class cache {

    private JSONArray processedMti;
    private JSONArray processedCards;
    private JSONArray processedTransac;
    private JSONArray processedAcq;


    public JSONArray getProcessedAcq() {
        return processedAcq;
    }

    public void setProcessedAcq(JSONArray processedAcq) {
        this.processedAcq = processedAcq;
    }

    public JSONArray getProcessedMti() {
        return processedMti;
    }

    public void setProcessedMti(JSONArray processedMti) {
        this.processedMti = processedMti;
    }

    public JSONArray getProcessedCards() {
        return processedCards;
    }

    public void setProcessedCards(JSONArray processedCards) {
        this.processedCards = processedCards;
    }

    public JSONArray getProcessedTransac() {
        return processedTransac;
    }

    public void setProcessedTransac(JSONArray processedTransac) {
        this.processedTransac = processedTransac;
    }
}
