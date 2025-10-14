package com.example.account_processing.service.impl;

import com.example.account_processing.entite.account.Account;
import com.example.account_processing.repository.AccountRepository;
import com.example.account_processing.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;

    @Override
    @Transactional
    public void createAccount(String json) {
        try {
            var account = objectMapper.readValue(json, Account.class);
            log.info("Собщение обработано {}", account);
            accountRepository.save(account);
            log.info("Акаунт сохранен");
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }
}
