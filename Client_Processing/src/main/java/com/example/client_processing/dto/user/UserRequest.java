package com.example.client_processing.dto.user;

import com.example.client_processing.entite.user.RoleList;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

@Value
public class UserRequest {

    @NotBlank(message = "Ошибка в логине")
    @Size(min = 2, max = 20, message = "Ошибка в логине иза длины")
    String login;

    @NotBlank(message = "Ошибка в пароле")
    @Size(min = 2, max = 20, message = "Ошибка в пароле иза длины")
    String password;

    @Email(message = "Ошибка в email")
    @NotBlank
    String email;
}