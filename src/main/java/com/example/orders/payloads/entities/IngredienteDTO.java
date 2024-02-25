package com.example.orders.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record IngredienteDTO(
        @NotEmpty(message = "nome vuoto")
        String nome,
        @NotNull(message = "prezzo vuoto")
        double prezzo
) {
}
