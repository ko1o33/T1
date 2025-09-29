package com.example.client_processing.service.impl;

import com.example.client_processing.dto.ClientRequestToMic;
import com.example.client_processing.entite.client.Client;
import com.example.client_processing.repository.ClientRepository;
import com.example.client_processing.service.ClientService;
import com.example.client_processing.exception.ClientException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    @Override
    public String getClientId (String pathId){
        var clientId = clientRepository.findLastClientId(pathId);
        if(clientId == null){
            return pathId+"0".repeat(8);
        }
        Long number = Long.parseLong(clientId)+1;
        return String.valueOf(number);
    }

    @Transactional
    @Override
    public boolean clientSave(Client client){
        if(!clientRepository.getByUserId(client.getUserId()).isEmpty()){
            throw new ClientException("Такой client есть");
        }
        clientRepository.save(client);
        return true;
    }

    @Override
    public ClientRequestToMic getClient(String clientId) {
        var client = clientRepository.getByClientId(clientId);
        if(client.isEmpty()){
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
