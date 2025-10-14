package com.example.client_processing.service;

import com.example.client_processing.dto.client.ClientRequestToMic;
import com.example.client_processing.entite.client.Client;


public interface ClientService {

    String getClientId(String pathId);

    boolean clientSave(Client client);

    ClientRequestToMic getClient(String clientId);

}
