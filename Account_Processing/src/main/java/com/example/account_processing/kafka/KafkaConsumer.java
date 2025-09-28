package com.example.account_processing.kafka;

import com.example.account_processing.service.AccountService;
import com.example.account_processing.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumer {

    private final AccountService accountService;
    private final CardService cardService;

    @KafkaListener(topics = "client_products", groupId = "create-account")
    public void listenAccount(String json) {
        try {
            accountService.createAccount(json);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @KafkaListener(topics = "client_transactions", groupId = "my_consumer")
    public void listenTransactions(String json) {

    }

    @KafkaListener(topics = "client_cards", groupId = "my_consumer")
    public void listenCards(String json) {
        try {
            cardService.createCard(json);
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }


}
