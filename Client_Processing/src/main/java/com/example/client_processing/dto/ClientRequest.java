package com.example.client_processing.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class ClientRequest {

    @NotBlank
    String firstName;

    @NotBlank
    String middleName;

    @NotBlank
    String lastName;

    @NotBlank
    String dateOfBirth;

    @NotBlank
    String documentType;

    @NotBlank
    String documentId;
}