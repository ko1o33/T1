package com.example.account_processing.util;

import com.example.account_processing.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CheckPayment {
    private final PaymentService paymentService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void checkPayment() {
        try {
            paymentService.checkPayment();
        } catch (Exception e) {
            checkPayment();
        }

    }

}
