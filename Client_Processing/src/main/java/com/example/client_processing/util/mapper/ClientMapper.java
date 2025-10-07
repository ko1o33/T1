package com.example.client_processing.util.mapper;

import com.example.client_processing.dto.ClientRequest;
import com.example.client_processing.entite.client.Client;
import com.example.client_processing.entite.client.DocumentTypeList;
import com.example.client_processing.entite.user.User;
import com.example.client_processing.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class ClientMapper {

    private final ClientService clientService;

    public Client mapToClient(ClientRequest clientRequest, User user) {

        String pathClientId = clientRequest.getRegionNumber() + clientRequest.getBankNumber();

        var client = Client.builder()
                .clientId(clientService.getClientId(pathClientId))
                .userId(user)
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .middleName(clientRequest.getMiddleName())
                .dateOfBirth(clientRequest.getDateOfBirth())
                .documentType(DocumentTypeList.valueOf(clientRequest.getDocumentType()))
                .documentId(clientRequest.getDocumentId())
                .documentPrefix(clientRequest.getDocumentPrefix())
                .documentSuffix(clientRequest.getDocumentSuffix())
                .build();
        return client;
    }

}
