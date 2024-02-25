package com.example.orders.esercizioCommerciale;

import com.example.orders.acquisto.Acquisto;
import com.example.orders.citta.Citta;
import com.example.orders.cliente.Cliente;
import com.example.orders.enums.Role;
import com.example.orders.enums.TipoEsercizio;
import com.example.orders.prodotto.Prodotto;
import com.example.orders.schedaAnagrafica.SchedaAnagrafica;
import com.example.orders.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "esercizi_commerciali")
@Getter
@Setter
@NoArgsConstructor
public class EsercizioCommerciale extends User {

    @Enumerated(EnumType.STRING)
    private TipoEsercizio tipoEsercizio;
    private String indirizzo;
    @OneToOne(mappedBy = "esercizioCommerciale")
    private SchedaAnagrafica schedaAnagrafica;
    @ManyToMany(mappedBy = "esercizioCommerciale",fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Cliente> clienteList;
    @ManyToOne
    @JoinColumn(name="citta_id")
    private Citta citta;
    @OneToMany(mappedBy = "esercizioCommerciale")
    private List<Prodotto> prodottos;
    @OneToMany(mappedBy = "esercizioCommerciale")
    @JsonIgnore
    private Acquisto acquisto;
    public EsercizioCommerciale(long id, String email, String password, String nome, Role role, String immagine_profilo, TipoEsercizio tipoEsercizio, String indirizzo,SchedaAnagrafica schedaAnagrafica,Citta citta) {
        super(id, email, password, nome, role, immagine_profilo);
        this.tipoEsercizio = tipoEsercizio;
        this.indirizzo = indirizzo;
        this.schedaAnagrafica=schedaAnagrafica;
        this.citta=citta;
    }
}
