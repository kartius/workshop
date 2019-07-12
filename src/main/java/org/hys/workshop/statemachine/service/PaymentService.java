package org.hys.workshop.statemachine.service;

public interface PaymentService {


    String pay(String paymentId);

    String fulfill(String paymentId);

    String cancel(String paymentId);


}
