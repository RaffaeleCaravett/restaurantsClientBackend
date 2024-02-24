package com.example.orders.esercizioCommerciale;

import com.example.orders.enums.Role;
import com.example.orders.enums.TipoEsercizio;
import com.example.orders.user.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "esercizi_commerciali")
@Getter
@Setter
@NoArgsConstructor
public class EsercizioCommerciale extends User {

    @Enumerated(EnumType.STRING)
    private TipoEsercizio tipoEsercizio;
    private String indirizzo;

    public EsercizioCommerciale(long id, String email, String password, String nome, Role role, String immagine_profilo, TipoEsercizio tipoEsercizio, String indirizzo) {
        super(id, email, password, nome, role, immagine_profilo);
        this.tipoEsercizio = tipoEsercizio;
        this.indirizzo = indirizzo;
    }
}
