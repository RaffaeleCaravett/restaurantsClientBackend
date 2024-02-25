package com.example.orders.citta;

import com.example.orders.cliente.Cliente;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name="città")
@Data
public class Citta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    @OneToMany(mappedBy = "citta")
    @JsonIgnore
    private List<Cliente> clienteList;
    @OneToMany(mappedBy = "citta")
    @JsonIgnore
    private List<EsercizioCommerciale> esercizioCommercialeList;
}
