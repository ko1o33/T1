package com.example.credit_processing.dto;

import lombok.Value;

@Value
public class ClientResponse {

    String firstName;

    String middleName;

    String lastName;

    String documentType;

    String documentId;

    String documentPrefix;

    String documentSuffix;
}
