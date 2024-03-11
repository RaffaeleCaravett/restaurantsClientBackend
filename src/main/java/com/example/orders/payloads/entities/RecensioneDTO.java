package com.example.orders.payloads.entities;

import jakarta.validation.constraints.NotNull;

public record RecensioneDTO(
        @NotNull(message = "Voto necessario")
        int voto,
        @NotNull(message = "Cliente id necessario")
        long cliente_id,
        @NotNull(message = "Esercizio id necessario")
        long esercizio_id
        ) {
}
