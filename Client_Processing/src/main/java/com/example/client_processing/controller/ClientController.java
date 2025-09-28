package com.example.client_processing.controller;

import com.example.client_processing.dto.ClientRequest;
import com.example.client_processing.dto.ClientResponse;
import com.example.client_processing.service.ClientService;
import com.example.client_processing.service.UserService;
import com.example.client_processing.util.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
public class ClientController {

    private final ClientMapper clientMapper;
    private final UserService userService;
    private final ClientService clientService;

    @PostMapping("/create")
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequest  clientRequest) {
        try {
           var user = userService.findByLogin(clientRequest.getLogin(),clientRequest.getPassword());
            var client = clientMapper.mapToClient(clientRequest,user);
            clientService.clientSave(client);
            return ResponseEntity.ok(client);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка "+e.getMessage());
        }
    }

}
