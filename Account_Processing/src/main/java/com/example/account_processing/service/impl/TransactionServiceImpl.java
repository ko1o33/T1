package com.example.account_processing.service.impl;


import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.entite.account.Account;
import com.example.account_processing.entite.card.Card;
import com.example.account_processing.entite.transaction.StatusList;
import com.example.account_processing.entite.transaction.Transaction;
import com.example.account_processing.entite.transaction.TypeList;
import com.example.account_processing.exception.MyException;
import com.example.account_processing.repository.AccountRepository;
import com.example.account_processing.repository.CardRepository;
import com.example.account_processing.repository.PaymentRepository;
import com.example.account_processing.repository.TransactionRepository;
import com.example.account_processing.service.TransactionService;
import com.example.account_processing.util.PaymentCalculation;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;
    private final PaymentCalculation paymentCalculation;
    private final PaymentRepository paymentRepository;



    Map<TypeList,Boolean> map = Map.of(
            TypeList.DEPOSIT,true,
            TypeList.WITHDRAWAL, false,
            TypeList.LOAN_PAYMENT, false
    );

    public void createTransaction(TransactionRequest transactionRequest) {
        try {
            var account = accountRepository.getById(transactionRequest.getAccountId());
            var card = cardRepository.getByCardId(transactionRequest.getCardId());
            Instant fromTime = Instant.now().minus(5, ChronoUnit.MINUTES);
            Long AccountCount = transactionRepository.countTransactionsByAccountAndTimeRange(account.getId(), fromTime,
                    Instant.now());
            if (AccountCount > 20) {
                accountRepository.blockAccount(com.example.account_processing.entite.account.StatusList.BLOCKED.name(),account.getId());
                log.info("Account blocked by blocking transaction" + account.getId());
                throw new MyException("Account blocked by blocking transaction");
            }
            //проверка что банкавский счет не заблокирован
            if (account.getStatus().equals(com.example.account_processing.entite.account.StatusList.ACTIVE)){
                //кредитная или нет
                if(account.isRecalc()){
                    creditTransaction(transactionRequest,account);
                }else {
                    debitTransaction(transactionRequest,account,card);
                }
            }
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }

    @Transactional
    protected void creditTransaction(TransactionRequest transactionRequest,Account account) {
        //зачисление или списание
        if(map.get(TypeList.valueOf(transactionRequest.getStatus()))){
            account.setBalance(account.getBalance()+transactionRequest.getAmount());
            if(paymentRepository.existsByIsCreditAndAccountId(account.getId())){
                var list = paymentRepository.findByAccountIdAndTypeOrderByPaymentDate(account.getId(), com.example.account_processing.entite.payment.TypeList.EXPIRED.name());
                list.stream().filter(payment -> payment.getAmount()<transactionRequest.getAmount())
                        .forEach(payment -> {
                            if((account.getBalance()-payment.getAmount())>0){
                                account.setBalance(account.getBalance()-payment.getAmount());
                                paymentRepository.updatePayment(com.example.account_processing.entite.payment.TypeList.LOAN_PAYMENT.name(),payment.getId());
                            }
                        });
                accountRepository.updateAccountAmount(account.getBalance(), account.getId());
            }else{
                account.setBalance(account.getBalance()+transactionRequest.getAmount());
            }
        }else if(!map.get(TypeList.valueOf(transactionRequest.getStatus()))){
            var listPayments = paymentCalculation.getPayment(account,transactionRequest);
            paymentRepository.saveAll(listPayments);
        }else {
            throw new MyException("нету такого типа транзакции");
        }
    }

    @Transactional
    protected void debitTransaction(TransactionRequest transactionRequest,Account account,Card card) {
        //зачисление или списание
        if(map.get(TypeList.valueOf(transactionRequest.getStatus()))){
            account.setBalance(account.getBalance()+transactionRequest.getAmount());
        }else if(!map.get(TypeList.valueOf(transactionRequest.getStatus()))){
            account.setBalance(account.getBalance()-transactionRequest.getAmount());
        }else {
            throw new MyException("нету такого типа транзакции");
        }
        if(account.getBalance()<0){
            throw new MyException("Недостаточно средств");
        }
        var transaction = Transaction.builder()
                .type(TypeList.valueOf(transactionRequest.getType()))
                .amount(transactionRequest.getAmount())
                .account(account)
                .card(card)
                .status(StatusList.valueOf(transactionRequest.getStatus()))
                .timestamp(LocalDate.now())
                .build();
        accountRepository.updateAccountAmount(account.getBalance(),account.getId());
        transactionRepository.save(transaction);
    }


}
