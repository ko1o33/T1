package com.example.account_processing.util;

import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.entite.account.Account;
import com.example.account_processing.entite.payment.Payment;
import com.example.account_processing.entite.payment.TypeList;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class PaymentCalculation {

    @Value("${value.month}")
    Long standardMonth;

    private Long getDeposit(Long amount, float interestRate, Long moth) {
        Long result;
        float i = interestRate / 1200;
        result = (long) Math.ceil(amount * ((i * Math.pow((i + 1), moth)) / (Math.pow((i + 1), moth) - 1)));
        return result;
    }

    public List<Payment> getPayment(Account account, TransactionRequest transactionRequest) {
        List<Payment> payments = new ArrayList<>();
        var deposit = getDeposit(transactionRequest.getAmount(), account.getInterestRate(), standardMonth);
        for (int i = 0; standardMonth >= i; i++) {
            var payment = Payment.builder()
                    .accountId(account.getId())
                    .amount(deposit)
                    .paymentDate(LocalDate.now().plusMonths(i))
                    .isCredit(true)
                    .type(TypeList.LOAN_PAYMENT)
                    .build();
            payments.add(payment);
        }
        return payments;
    }
}
