package com.example.client_processing.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Value
public class ClientRequest {

    @NotBlank
    @Size(min=2, max=2)
    String regionNumber;

    @NotBlank
    @Size(min=2, max=2)
    String bankNumber;

    @NotBlank
    String login;

    @NotBlank
    String password;

    @NotBlank
    String firstName;

    @NotBlank
    String middleName;

    @NotBlank
    String lastName;

    @Past(message = "Дата рождения должна быть в прошлом")
    LocalDate dateOfBirth;

    @NotBlank
    @Pattern(regexp = "PASSPORT|INT_PASSPORT|BIRTH_CERT")
    String documentType;

    @NotBlank
    String documentId;

    @NotBlank
    @Size(min=4, max=4)
    String documentPrefix;

    @NotBlank
    @Size(min=6, max=6)
    String documentSuffix;
}