package app.sales;

import app.core.AmazonNotifier;
import app.core.Sale;
import app.core.SalesNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class SalesController {

    @Autowired
    SalesNotifier salesNotifier;

    @RequestMapping(value = "notify/sale", method = RequestMethod.POST, consumes = "application/json", produces = "application/json")
    public void NewSale(@RequestBody Sale sale) {
        log.debug("Received sale for costume :" + sale.getCostumeId());
        salesNotifier.notifySale(sale);
    }
}
