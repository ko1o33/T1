package com.example.client_processing.controller;

import com.example.client_processing.dto.ClientRequest;
import com.example.client_processing.dto.ClientResponse;
import com.example.client_processing.util.mapper.ClientMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientMapper clientMapper;

    @PostMapping("/create")
    public ClientResponse createClient(@RequestBody ClientRequest  clientRequest) {
        //проверка
        var client = clientMapper.mapToClient(clientRequest);
        return clientMapper.mapToClientResponse();
    }

}
