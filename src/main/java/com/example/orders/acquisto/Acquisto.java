package com.example.orders.acquisto;

import com.example.orders.cliente.Cliente;
import com.example.orders.enums.TipoPagamento;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "acquisti")
@Data
public class Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int anno;
    private int mese;
    private int giorno;
    private double totale;
    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;
    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "acquisto_prodotto",
    joinColumns = @JoinColumn(name = "acquisto_id"),
    inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
    private List<Prodotto> prodottos;
    @ManyToOne
    @JoinColumn(name = "esercizio_id")
    private EsercizioCommerciale esercizioCommerciale;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
