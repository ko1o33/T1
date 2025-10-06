package com.example.client_processing.controller.clientProductController;

import com.example.client_processing.dto.CardRequest;
import com.example.client_processing.dto.TransactionRequest;
import com.example.client_processing.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/transaction")
@Slf4j
public class TransactionController {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper ;

    @PostMapping("/create")
    public ResponseEntity<?> createTransaction(@Valid @RequestBody TransactionRequest transactionRequest){
        try {
            kafkaProducer.sendTo("client_transactions", transactionRequest);
            return ResponseEntity.ok().build();
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
