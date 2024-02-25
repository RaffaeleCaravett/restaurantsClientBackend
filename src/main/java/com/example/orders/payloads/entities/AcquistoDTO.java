package com.example.orders.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AcquistoDTO(
        @NotNull(message = "totale vuoto")
        double totale,
        @NotNull(message = "anno vuoto")
        int anno,
        @NotNull(message = "mese vuoto")
        int mese,
        @NotNull(message = "giorno vuoto")
        int giorno,
        @NotEmpty(message="tipo pagamento vuoto")
        String tipo_pagamento,
        @NotNull(message="almeno un prodotto")
        List<Long> prodotto_id,
        @NotNull(message = "esercizio_id vuoto")
        long esercizio_id,
        @NotNull(message = "cliente_id vuoto")
        long cliente_id
) {
}
