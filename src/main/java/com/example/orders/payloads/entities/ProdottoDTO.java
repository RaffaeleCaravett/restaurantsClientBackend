package com.example.orders.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record ProdottoDTO(
  @NotEmpty(message = "nome vuoto")
  String nome,
  @NotNull(message = "prezzo vuoto")
  double prezzo,
  @NotEmpty(message = "tipo prodotto vuoto")
  String tipoProdotto,
  @NotNull(message = "esercizio vuoto")
  long esercizio_id,
  @NotNull(message = "almeno un ingrediente")
  List<Long> ingredienti_id
) {
}
