package com.example.client_processing.service;

import com.example.client_processing.entite.client.Client;
import org.springframework.stereotype.Service;


public interface ClientService {

    String getClientId (String pathId);

    boolean clientSave(Client client);


}
