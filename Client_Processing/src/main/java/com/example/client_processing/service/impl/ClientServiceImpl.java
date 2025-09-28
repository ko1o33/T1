package com.example.client_processing.service.impl;

import com.example.client_processing.entite.client.Client;
import com.example.client_processing.repository.ClientRepository;
import com.example.client_processing.service.ClientService;
import com.example.client_processing.exception.ClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;

    public String getClientId (String pathId){
        var clientId = clientRepository.findLastClientId(pathId);
        if(clientId == null){
            return pathId+"0".repeat(8);
        }
        Long number = Long.parseLong(clientId)+1;
        return String.valueOf(number);
    }

    @Transactional
    public boolean clientSave(Client client){
        if(!clientRepository.getByUserId(client.getUserId()).isEmpty()){
            throw new ClientException("Такой client есть");
        }
        clientRepository.save(client);
        return true;
    }
}
