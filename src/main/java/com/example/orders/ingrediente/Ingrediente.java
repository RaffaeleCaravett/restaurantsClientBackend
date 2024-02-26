package com.example.orders.ingrediente;

import com.example.orders.prodotto.Prodotto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ingredienti")
@Data
public class Ingrediente {
@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
private String nome;
private double prezzo;
@ManyToMany(mappedBy = "ingredientes")
    @JsonIgnore
    private List<Prodotto> prodottos;

}
