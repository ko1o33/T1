package com.example.client_processing.dto.client;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ClientRequestToMic {

    String firstName;

    String middleName;

    String lastName;

    String documentType;

    String documentId;

    String documentPrefix;

    String documentSuffix;
}
