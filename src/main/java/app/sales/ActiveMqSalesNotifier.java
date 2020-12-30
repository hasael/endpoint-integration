package app.sales;

import app.core.Sale;
import app.core.SalesNotifier;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class ActiveMqSalesNotifier implements SalesNotifier {

    @Autowired
    JmsTemplate jmsTemplate;

    @Override
    public void notifySale(Sale sale) {
        try {
            log.info("sending costume for assignment: " + sale.getCostumeId());
            jmsTemplate.convertAndSend("sales", toJson(sale));
        } catch (Exception ex) {
            log.error("error: " + ex.getLocalizedMessage());
        }

    }

    private String toJson(Sale sale) {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(sale);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }
}
