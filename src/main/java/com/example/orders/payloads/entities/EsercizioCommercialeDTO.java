package com.example.orders.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record EsercizioCommercialeDTO(@NotEmpty(message = "nome vuoto")
                                      String nome,
                                      @NotEmpty(message = "cognome vuoto")
                                      String cognome,
                                      @NotEmpty(message = "email vuota")
                                      String email,
                                      @NotEmpty(message = "password vuota")
                                      String password,
                                     @NotEmpty(message = "indirizzo vuoto")
                                      String indirizzo,
                                      @NotEmpty(message="tipo esercizio vuoto")
                                      String tipoEsercizio,
                                      @NotNull(message = "città vuota")
                                      long citta_id
                                      ) {
}
