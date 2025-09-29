package com.example.credit_processing.util;

import com.example.credit_processing.dto.ProductRegistryRequest;
import com.example.credit_processing.entite.paymentRegistry.ProductRegistry;
import com.example.credit_processing.entite.productRegistry.PaymentRegistry;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentCalculation {
    private Long getDeposit(Long amount,float interestRate,Long moth) {
        Long result;
        float i = interestRate/1200;
        result = (long) Math.ceil(amount*((i*Math.pow((i+1),moth))/(Math.pow((i+1),moth)-1)));
        return result;
    }

    public List<PaymentRegistry> getPayment(ProductRegistryRequest product, ProductRegistry registry) {
        List<PaymentRegistry> payments = new ArrayList<>();
        Long deposit = getDeposit(product.getAmount(),product.getInterestRate(),product.getMonthCount());
        for(int i = 0; product.getMonthCount() == i; i++){
            var paymentRegistry = PaymentRegistry.builder()
                    .amount(product.getAmount()-deposit*i)
                    .debtAmount(deposit*i)
                    .expired(false)
                    .interestRateAmount(product.getInterestRate())
                    .paymentExpirationDate(LocalDate.now().plusMonths(i+1))
                    .productRegistry(registry)
                    .build();
        }
        return payments;
    }
}
