package com.example.client_processing.service;

import com.example.client_processing.dto.ClientRequestToMic;
import com.example.client_processing.entite.client.Client;
import org.springframework.stereotype.Service;

import java.util.Optional;


public interface ClientService {

    String getClientId (String pathId);

    boolean clientSave(Client client);

    ClientRequestToMic getClient (String clientId);

}
