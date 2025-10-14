package com.example.account_processing.service.impl;

import com.example.account_processing.dto.CardRequest;
import com.example.account_processing.entite.account.Account;
import com.example.account_processing.entite.card.Card;
import com.example.account_processing.entite.card.StatusList;
import com.example.account_processing.repository.AccountRepository;
import com.example.account_processing.repository.CardRepository;
import com.example.account_processing.service.CardService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CardServiceImpl implements CardService {

    private final ObjectMapper objectMapper;
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    @Override
    public void createCard(String json) {
        try {
            log.info("Create card: " + json);
            var cardRequest = objectMapper.readValue(json, CardRequest.class);
            var account = accountRepository.getById(cardRequest.getAccountId());
            if (account.getStatus() != com.example.account_processing.entite.account.StatusList.BLOCKED) {
                var card = Card.builder()
                        .cardId(cardRequest.getCardId())
                        .paymentSystem(cardRequest.getPaymentSystem())
                        .status(StatusList.valueOf(cardRequest.getStatus()))
                        .account(account)
                        .build();
                cardRepository.save(card);
                log.info("Card created id : " + card.getId());
            } else {
                log.info("This account has BLOCKED");
            }
        } catch (Exception e) {
            log.info("При создание card произошла ошибк " + e.getMessage());
        }
    }
}
