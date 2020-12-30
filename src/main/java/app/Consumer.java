package app;

import app.core.AmazonNotifier;
import app.core.Costume;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@Slf4j
@Component
public class Consumer implements MessageListener {

    @Autowired
    AmazonNotifier amazonNotifier;

    @Override
    @JmsListener(destination = "amazon")
    public void onMessage(Message message) {
        try {
            log.info("Received message for amazon: " + message);

            var text = ((TextMessage) message).getText();
            var costume =  fromJson(text);

            amazonNotifier.notifyCostume(costume);

        } catch (Exception ex) {
            log.error("Error consuming message: " + ex.getLocalizedMessage());
        }
    }

    private Costume fromJson(String data) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(data, Costume.class);
    }
}
