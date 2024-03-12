package com.example.orders.prodotto;

import com.example.orders.acquisto.Acquisto;
import com.example.orders.cliente.Cliente;
import com.example.orders.enums.TipoProdotto;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.ingrediente.Ingrediente;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="prodotti")
@Data
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nomeProdotto;
    private double prezzo;
    @Enumerated(EnumType.STRING)
    private TipoProdotto tipoProdotto;
    @ManyToOne
    @JsonIgnore
@JoinColumn(name = "esercizio_id")
    private EsercizioCommerciale esercizioCommerciale;
    @ManyToMany
    @JoinTable(name = "prodotto_ingrediente",
    joinColumns = @JoinColumn(name = "prodotto_id"),
    inverseJoinColumns = @JoinColumn(name = "ingrediente_id"))
    private List<Ingrediente> ingredientes;
    @ManyToMany(mappedBy = "prodotti")
    @JsonIgnore
    private List<Cliente> clienteList;
    @ManyToMany(mappedBy = "prodottos")
    @JsonIgnore
    private List<Acquisto> acquistos;
}
