package com.example.client_processing.service.impl;

import com.example.client_processing.dto.client.ClientRequestToMic;
import com.example.client_processing.entite.client.Client;
import com.example.client_processing.repository.ClientRepository;
import com.example.client_processing.service.ClientService;
import com.example.client_processing.exception.ClientException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public String getClientId(String pathId) {
        var clientId = clientRepository.findLastClientId(pathId);
        if (clientId == null) {
            return pathId + "0".repeat(8);
        }
        Long number = Long.parseLong(clientId) + 1;
        return String.valueOf(number);
    }

    @Transactional
    @Override
    public boolean clientSave(Client client) {
        if (!clientRepository.getByUserId(client.getUserId()).isEmpty()) {
            log.info("Такой client есть: {}", client);
            throw new ClientException("Такой client есть");
        }
        log.info("Сохранен client: {}", client);
        clientRepository.save(client);
        return true;
    }

    @Override
    public ClientRequestToMic getClient(String clientId) {
        log.info("get client by id: {}", clientId);
        var client = clientRepository.getByClientId(clientId);
        if (client.isEmpty()) {
            log.info("clientId not found");
            throw new ClientException("no client found");
        }
        var clientGet = client.get();
        var dtoClient = ClientRequestToMic.builder()
                .documentId(clientGet.getDocumentId())
                .middleName(clientGet.getMiddleName())
                .firstName(clientGet.getFirstName())
                .lastName(clientGet.getLastName())
                .documentPrefix(clientGet.getDocumentPrefix())
                .documentSuffix(clientGet.getDocumentSuffix())
                .documentType(clientGet.getDocumentType().toString())
                .build();
        return dtoClient;
    }
}
