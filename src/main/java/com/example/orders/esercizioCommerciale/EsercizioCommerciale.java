package com.example.orders.esercizioCommerciale;

import com.example.orders.enums.TipoEsercizio;
import jakarta.persistence.*;
import lombok.Data;
import org.apache.catalina.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "esercizi_commerciali")
@Data
public class EsercizioCommerciale {

    private String nome;
    @Enumerated(EnumType.STRING)
    private TipoEsercizio tipoEsercizio;
    private String indirizzo;
    private String immagine;



}
