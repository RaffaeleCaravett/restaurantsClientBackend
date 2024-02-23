package com.example.orders.cliente;

import com.example.orders.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name="clients")
@Getter
@Setter

public class Cliente extends User{

private String cognome;
private int eta;
    private String immagine_profilo;


    public Cliente(String username, String password, Collection<? extends GrantedAuthority> authorities,String cognome,int eta,String immagine_profilo) {
        super(username, password, authorities);
        this.cognome=cognome;
        this.eta=eta;
        this.immagine_profilo=immagine_profilo;
    }
}
