package com.example.orders.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record ClienteDTO(
@NotEmpty(message = "nome vuoto")
        String nome,
@NotEmpty(message = "cognome vuoto")
        String cognome,
@NotEmpty(message = "email vuota")
        String email,
@NotEmpty(message = "password vuota")
        String password,
@NotNull(message = "età vuota")
        int eta
) {
}
