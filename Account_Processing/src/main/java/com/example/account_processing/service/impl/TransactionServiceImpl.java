package com.example.account_processing.service.impl;

import com.example.account_processing.dto.CardRequest;
import com.example.account_processing.dto.TransactionRequest;
import com.example.account_processing.entite.transaction.StatusList;
import com.example.account_processing.entite.transaction.Transaction;
import com.example.account_processing.entite.transaction.TypeList;
import com.example.account_processing.exception.MyException;
import com.example.account_processing.repository.AccountRepository;
import com.example.account_processing.repository.CardRepository;
import com.example.account_processing.repository.TransactionRepository;
import com.example.account_processing.service.AccountService;
import com.example.account_processing.service.TransactionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final CardRepository cardRepository;
    private final TransactionRepository transactionRepository;

    Map<TypeList,Boolean> map = Map.of(TypeList.DEPOSIT,true,
            TypeList.REFUND,true,
            TypeList.INTEREST,true,
            TypeList.WITHDRAWAL, false,
            TypeList.PAYMENT, false,
            TypeList.FEE, false,
            TypeList.LOAN_PAYMENT, false,
            TypeList.TRANSFER, false
    );

    @Transactional
    public void createTransaction(String json) {
        try {
            var transactionRequest = objectMapper.readValue(json, TransactionRequest.class);
            var account = accountRepository.getById(transactionRequest.getAccountId());
            var card = cardRepository.getByCardId(transactionRequest.getCardId());
            if (account.getStatus().equals(com.example.account_processing.entite.account.StatusList.ACTIVE)){
                if(account.isRecalc()){

                }
                if(map.get(TypeList.valueOf(transactionRequest.getStatus()))){
                    account.setBalance(account.getBalance()+transactionRequest.getAmount());
                }else if(!map.get(TypeList.valueOf(transactionRequest.getStatus()))){
                    account.setBalance(account.getBalance()-transactionRequest.getAmount());
                }
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
            accountRepository.updateAmount(account.getBalance());
            transactionRepository.save(transaction);
        }catch (Exception e){
            log.info(e.getMessage());
        }
    }
}
