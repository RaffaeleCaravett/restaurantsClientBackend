package com.example.orders.cliente;

import com.example.orders.enums.Role;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.user.User;
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
@ManyToMany
@JoinTable(name = "cliente_esercizio",
joinColumns = @JoinColumn(name="user_id"),
        foreignKey = @ForeignKey(name = "esercizio_id"),
        inverseJoinColumns = @JoinColumn(name = "esercizio_id"),
        inverseForeignKey = @ForeignKey(name = "user_id"))
private List<EsercizioCommerciale> esercizioCommercialeList;
    public Cliente(long id, String email, String password,String nome, Role role, String immagine_profilo, String cognome, int eta) {
        super(id, email, password,nome, role,immagine_profilo);
        this.cognome = cognome;
        this.eta = eta;
    }
}
