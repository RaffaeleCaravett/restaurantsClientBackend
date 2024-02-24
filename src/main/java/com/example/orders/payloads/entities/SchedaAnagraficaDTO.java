package com.example.orders.payloads.entities;

import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record SchedaAnagraficaDTO(
        @NotEmpty(message = "capitale sociale vuoto")
        String capitaleSociale,
        @NotEmpty(message = "pIva vuota")
        String pIva,
                @NotEmpty(message = "rappresentante vuoto")
                String rappresentante,
        @NotNull(message = "esercizio id vuoto")
       long esercizio_id
) {
}
