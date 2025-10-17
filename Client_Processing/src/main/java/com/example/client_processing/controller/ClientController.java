package com.example.client_processing.controller;



import com.example.client_processing.dto.client.ClientRequest;
import com.example.client_processing.exception.MyException;
import com.example.client_processing.service.ClientService;
import com.example.client_processing.service.UserService;
import com.example.client_processing.util.mapper.ClientMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/client")
@Slf4j
public class ClientController {

    private final ClientMapper clientMapper;
    private final UserService userService;
    private final ClientService clientService;

    @PostMapping("/create")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    public ResponseEntity<?> createClient(@Valid @RequestBody ClientRequest clientRequest) {
        try {
            log.info("Create new client: {}", clientRequest);
            var user = userService.findByLogin(clientRequest.getLogin(), clientRequest.getPassword());
            var client = clientMapper.mapToClient(clientRequest, user);
            clientService.clientSave(client);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            log.info("Произошла ошибка при создание клиента: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка " + e.getMessage());
        }
    }

    @GetMapping("/get")
    @LogDatasourceError
    @Metric
    @HttpIncomeRequestLog
    @HttpOutcomeRequestLog
    @Cached
    public ResponseEntity<?> getClient(@RequestParam String clientId) {
        try {
            log.info("Get client by id: {}", clientId);
            var client = clientService.getClient(clientId);
            log.info("Client send: {}", client);
            return ResponseEntity.ok(client);
        } catch (Exception e) {
            log.info("Ошибка " + e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Ошибка " + e.getMessage());
        }
    }

}
