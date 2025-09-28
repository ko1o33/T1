package com.example.client_processing.util.mapper;

import com.example.client_processing.dto.ClientRequest;
import com.example.client_processing.dto.ClientResponse;
import com.example.client_processing.entite.client.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientMapper {
    public Client mapToClient(ClientRequest clientRequest) {
        var client = Client.builder()
                .clientId()
                .userId()
                .firstName(clientRequest.getFirstName())
                .lastName(clientRequest.getLastName())
                .middleName(clientRequest.getMiddleName())
                .dateOfBirth()
                .documentType()
                .documentId()
                .documentPrefix()
                .documentSuffix()
                .build();
        return new Client();
    }

    public ClientResponse mapToClientResponse() {
        return new ClientResponse();
    }
}
