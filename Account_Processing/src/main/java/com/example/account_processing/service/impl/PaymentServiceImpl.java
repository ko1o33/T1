package com.example.account_processing.service.impl;

import com.example.account_processing.dto.PaymentDto;
import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.entite.payment.Payment;
import com.example.account_processing.entite.payment.TypeList;
import com.example.account_processing.exception.MyException;
import com.example.account_processing.repository.AccountRepository;
import com.example.account_processing.repository.PaymentRepository;
import com.example.account_processing.service.PaymentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public void checkPayment(){
        try {
            var payments = paymentRepository.findByPaymentDate(LocalDate.now());
            payments.forEach(payment -> {
                var account = accountRepository.getById(payment.getAccountId());
                if(account.getBalance()>payment.getAmount()){
                    accountRepository.updateAccountAmount(account.getBalance()-payment.getAmount(),account.getId());
                    paymentRepository.updatePayment(TypeList.LOAN_PAYMENT.name(), payment.getId());
                }else{
                    paymentRepository.updatePayment(TypeList.EXPIRED.name(), payment.getId());
                }
            });
        }catch (Exception e){
            throw new MyException(e.getMessage());
        }
    }

    @Transactional
    public void paymentCredit(PaymentDto paymentDto) {
        try {
            var list = paymentRepository.findByAccountId(paymentDto.getAccountId());
            Long balance = list.stream()
                    .map(Payment::getAmount)
                    .mapToLong(Long::longValue)
                    .sum();
            if(balance == paymentDto.getAmount()){
                list.forEach(payment -> {
                    payment.setType(TypeList.PAYED_AT);
                    payment.setPaymentDate(LocalDate.now());
                    paymentRepository.saveAll(list);
                });
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

}
