package com.example.client_processing.controller.clientProductController;

import com.example.client_processing.aop.annotation.LogDatasourceError;
import com.example.client_processing.dto.CardRequest;
import com.example.client_processing.exception.MyException;
import com.example.client_processing.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/card")
@Slf4j
public class CardController {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper ;

    @PostMapping("/create")
    @LogDatasourceError
    public ResponseEntity<?> createCard(@Valid @RequestBody CardRequest cardRequest){
        try {
            if(true)throw new Exception();
            kafkaProducer.sendTo("client_cards", objectMapper.writeValueAsString(cardRequest));
            return ResponseEntity.ok().build();
        }catch (Exception e){
            log.info(e.getMessage());
            throw new MyException(e.getMessage());
            //return ResponseEntity.badRequest().build();
        }
    }

}
