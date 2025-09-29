package com.example.client_processing.controller.clientProductController;

import com.example.client_processing.dto.AccountAndProductRequest;
import com.example.client_processing.exception.MyException;
import com.example.client_processing.kafka.KafkaProducer;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Pattern;

@RestController
@RequiredArgsConstructor
@RequestMapping("/account")
@Slf4j
public class AccountController {

    private final KafkaProducer kafkaProducer;
    private final ObjectMapper objectMapper ;

    private final Set<String> set1 = new HashSet<>(Arrays.asList("DC","CC","NS","PENS"));
    private final Set<String> set2 = new HashSet<>(Arrays.asList("IPO","PC","AC"));
    private final Pattern pattern = Pattern.compile("([A-Za-z]+)(\\d+)");

    @PostMapping("/create")
    public ResponseEntity<?> accountCreate(@Valid @RequestBody AccountAndProductRequest account) {
        try {
            var mather = pattern.matcher(account.getProductId());
            mather.find();
            if(set1.contains(mather.group(1))) {
                kafkaProducer.sendTo("client_products",objectMapper.writeValueAsString(account));
                return ResponseEntity.ok().build();
            }else if(set2.contains(mather.group(1))) {
                kafkaProducer.sendTo("client_credit_products",objectMapper.writeValueAsString(account));
                return ResponseEntity.ok().build();
            }
            throw new MyException("Такой тип продукта запрещен");
        }catch (Exception e){
            log.info(e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка "+e.getMessage());
        }
    }
}
