package com.example.account_processing.service.impl;


import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.entite.account.Account;
import com.example.account_processing.entite.card.Card;
import com.example.account_processing.entite.payment.Payment;
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

import java.time.LocalDateTime;
import java.util.List;
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


    Map<TypeList, Boolean> map = Map.of(
            TypeList.DEPOSIT, true,
            TypeList.WITHDRAWAL, false,
            TypeList.LOAN_PAYMENT, false
    );

    public void createTransaction(TransactionRequest transactionRequest) {
        try {
            log.info("Start create transaction");
            var account = accountRepository.getById(transactionRequest.getAccountId());
            var card = cardRepository.getByCardId(transactionRequest.getCardId());
            var fromTime = LocalDateTime.now().minusMinutes(5);
            Long AccountCount = transactionRepository.countTransactionsByAccountAndTimeRange(account.getId(), fromTime,
                    LocalDateTime.now());
            if (AccountCount > 20) {
                log.info("This account has a lot of transactions");
                accountRepository.blockAccount(com.example.account_processing.entite.account.StatusList.BLOCKED.name(), account.getId());
                log.info("Account blocked by blocking transaction" + account.getId());
                throw new MyException("Account blocked by blocking transaction");
            }
            //проверка что банкавский счет не заблокирован
            if (account.getStatus().equals(com.example.account_processing.entite.account.StatusList.ACTIVE)) {
                //кредитная или нет
                log.info("Check account for recalc account id {}", account.getId());
                if (account.isRecalc()) {
                    creditTransaction(transactionRequest, account, card);
                } else {
                    debitTransaction(transactionRequest, account, card);
                }
            } else {
                log.info("This account is blocked {}", account.getId());
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    @Transactional
    protected void creditTransaction(TransactionRequest transactionRequest, Account account, Card card) {
        //зачисление или списание
        log.info("Start credit transaction");
        var transaction = Transaction.builder()
                .type(TypeList.valueOf(transactionRequest.getType()))
                .amount(transactionRequest.getAmount())
                .account(account)
                .card(card)
                .status(StatusList.valueOf(transactionRequest.getStatus()))
                .timestamp(LocalDateTime.now())
                .build();
        if (map.get(TypeList.valueOf(transactionRequest.getType()))) {
            log.info("checking for debt cancellation {}", account.getId());
            account.setBalance(account.getBalance() + transactionRequest.getAmount());
            if (paymentRepository.existsByIsCreditAndAccountId(account.getId())) {
                log.info("debt cancellation {}", account.getId());
                var list = paymentRepository.findByAccountIdAndTypeOrderByPaymentDate(account.getId(), com.example.account_processing.entite.payment.TypeList.EXPIRED);
                list.stream().filter(payment -> payment.getAmount() < transactionRequest.getAmount())
                        .forEach(payment -> {
                            log.info("Checking for debiting a payment : {}", payment);
                            if ((account.getBalance() - payment.getAmount()) > 0) {
                                account.setBalance(account.getBalance() - payment.getAmount());
                                payment.setType(com.example.account_processing.entite.payment.TypeList.PAYED_AT);
                                paymentRepository.save(payment);
                                log.info("the debt is paid off {}", payment);
                            }
                            log.info("This payment can't cancellation: {}", payment);
                        });
                accountRepository.save(account);
            } else {
                account.setBalance(account.getBalance() + transactionRequest.getAmount());
                accountRepository.save(account);
            }
        } else if (!map.get(TypeList.valueOf(transactionRequest.getType()))) {
            log.info("Crate payments credit list for {}", account.getId());
            List<Payment> listPayments = paymentCalculation.getPayment(account, transactionRequest);
            paymentRepository.saveAll(listPayments);
            listPayments.forEach(payment -> {
                log.info("Payment credit crated {}", payment);
            });
        } else {
            log.info("This type transactional is missing {}", transactionRequest.getType());
            throw new MyException("нету такого типа транзакции");
        }
        transactionRepository.save(transaction);
    }

    @Transactional
    protected void debitTransaction(TransactionRequest transactionRequest, Account account, Card card) {
        //зачисление или списание
        if (map.get(TypeList.valueOf(transactionRequest.getType()))) {
            account.setBalance(account.getBalance() + transactionRequest.getAmount());
        } else if (!map.get(TypeList.valueOf(transactionRequest.getStatus()))) {
            account.setBalance(account.getBalance() - transactionRequest.getAmount());
        } else {
            throw new MyException("нету такого типа транзакции");
        }
        if (account.getBalance() < 0) {
            throw new MyException("Недостаточно средств");
        }
        var transaction = Transaction.builder()
                .type(TypeList.valueOf(transactionRequest.getType()))
                .amount(transactionRequest.getAmount())
                .account(account)
                .card(card)
                .status(StatusList.valueOf(transactionRequest.getStatus()))
                .timestamp(LocalDateTime.now())
                .build();
        accountRepository.save(account);
        transactionRepository.save(transaction);
    }


}
