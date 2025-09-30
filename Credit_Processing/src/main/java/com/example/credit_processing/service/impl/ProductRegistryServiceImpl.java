package com.example.credit_processing.service.impl;

import com.example.credit_processing.dto.ClientResponse;
import com.example.credit_processing.dto.ProductRegistryRequest;
import com.example.credit_processing.entite.paymentRegistry.ProductRegistry;
import com.example.credit_processing.exception.MyException;
import com.example.credit_processing.repository.PaymentRegistryRepository;
import com.example.credit_processing.repository.ProductRegistryRepository;
import com.example.credit_processing.service.ProductRegistryService;
import com.example.credit_processing.util.PaymentCalculation;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductRegistryServiceImpl implements ProductRegistryService {

    @Value("${product.limit}")
    private Long limit;

    private final ObjectMapper mapper;
    private final WebClient webClient;
    private final ProductRegistryRepository productRegistryRepository;
    private final PaymentRegistryRepository paymentRegistryRepository;
    private final PaymentCalculation paymentCalculation;


    @Override
    @Transactional
    public void createProduct(String json) {
        try {
            var product = mapper.readValue(json, ProductRegistryRequest.class);
            if(checkClient(product) &&
                    getSumAmount(product.getClientId())+ product.getAmount() > limit &&
                    !checkExpired(product)) {
                var productRegistry = ProductRegistry.builder()
                        .clientId(product.getClientId())
                        .accountId(product.getAccountId())
                        .interestRate(product.getInterestRate())
                        .monthCount(product.getMonthCount())
                        .openDate(product.getOpenDate())
                        .productId(product.getProductId())
                        .build();
                productRegistryRepository.save(productRegistry);
                var listPayment = paymentCalculation.getPayment(product,productRegistry);
                log.info("сохранеия");
                paymentRegistryRepository.saveAll(listPayment);
            }else {
                throw new MyException("Данный client не может получить продукт");
            }
        }catch (Exception e){
            log.error(e.getMessage());
        }
    }

    @Override
    public Long getSumAmount(Long clientId) {
        log.info("вызван getSumAmount");
        Long amount = 0L;
        var productList = productRegistryRepository.findByClientId(clientId);
        var opAmount = paymentRegistryRepository.findByProductRegistry(productList);
        if(opAmount.isEmpty()) {
            return amount;
        }
        amount = opAmount.get();
        return amount;
    }

    @Override
    public boolean checkClient(ProductRegistryRequest product) {
        try {
            log.info("вызван checkClient");
            var clientResponse = webClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/client/get")
                            .queryParam("clientId", product.getClientId())
                            .build())
                    .retrieve()
                    .bodyToMono(ClientResponse.class).block();
            if(clientResponse != null){
                return true;
            }
            return false;
        }catch (Exception e){
            log.error(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean checkExpired(ProductRegistryRequest product) {
        log.info("вызван checkExpired");
        var productEntite = productRegistryRepository.findByClientId(product.getClientId());
        if(productEntite.isEmpty()) {
            return false;
        }
        return paymentRegistryRepository.existsExpired(productEntite);
    }


}
