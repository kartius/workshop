package org.hys.workshop.statemachine.service;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HillelOnlineShopService implements PaymentService {


    private static final String SHOP_NAME = "Hillel";

    @Override
    public String pay(String paymentId) {
        String result = String.format("Payment in %s with payment id %s", SHOP_NAME, paymentId);
        log.info(result);
        return result;
    }

    @Override
    public String fulfill(String paymentId) {
        String result = String.format("Fulfillment in %s with payment id %s", SHOP_NAME, paymentId);
        log.info(result);
        return result;
    }

    @Override
    public String cancel(String paymentId) {
        String result = String.format("Cancelling in %s with payment id %s", SHOP_NAME, paymentId);
        log.info(result);
        return result;
    }
}
