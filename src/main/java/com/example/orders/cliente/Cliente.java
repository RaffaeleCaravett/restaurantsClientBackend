package com.example.orders.cliente;

import com.example.orders.enums.Role;
import com.example.orders.user.User;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name="clients")
@Getter
@Setter
@NoArgsConstructor
public class Cliente extends User {

private String cognome;
private int eta;

    public Cliente(long id, String email, String password,String nome, Role role, String immagine_profilo, String cognome, int eta) {
        super(id, email, password,nome, role,immagine_profilo);
        this.cognome = cognome;
        this.eta = eta;
    }
}
