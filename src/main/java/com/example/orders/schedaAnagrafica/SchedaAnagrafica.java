package com.example.orders.schedaAnagrafica;

import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="schede_anagrafiche")
@Data
public class SchedaAnagrafica {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String capitaleSociale;
    private String pIva;
    private String rappresentante;
    @OneToOne
    @JoinColumn(name = "esercizio_id")
    private EsercizioCommerciale esercizioCommerciale;
}
