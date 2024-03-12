package com.example.orders.cliente;

import com.example.orders.acquisto.Acquisto;
import com.example.orders.citta.Citta;
import com.example.orders.enums.Role;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.prodotto.Prodotto;
import com.example.orders.recensione.Recensione;
import com.example.orders.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name="clients")
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends User {

private String cognome;
private int eta;
@ManyToOne
@JoinColumn(name="citta_id")
private Citta citta;
@ManyToMany
@JoinTable(name = "cliente_esercizio",
joinColumns = @JoinColumn(name="user_id"),
        foreignKey = @ForeignKey(name = "esercizio_id"),
        inverseJoinColumns = @JoinColumn(name = "esercizio_id"),
        inverseForeignKey = @ForeignKey(name = "user_id"))
private List<EsercizioCommerciale> esercizioCommercialeList;
@ManyToMany
@JsonIgnore
@JoinTable(name = "cliente_prodotto",
joinColumns = @JoinColumn(name="cliente_id"),
inverseJoinColumns = @JoinColumn(name = "prodotto_id"))
private List<Prodotto> prodotti;
@OneToMany(mappedBy = "cliente")
@JsonIgnore
private List<Acquisto> acquisto;
@OneToMany(mappedBy = "cliente")
@JsonIgnore
private List<Recensione> recensione;
    public Cliente(long id, String email, String password,String nome, Role role, String immagine_profilo, String cognome, int eta,Citta citta) {
        super(id, email, password,nome, role,immagine_profilo);
        this.cognome = cognome;
        this.eta = eta;
    this.citta=citta;
    }
}
