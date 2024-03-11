package com.example.orders.recensione;

import com.example.orders.cliente.Cliente;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name="recensioni")
@Data
public class Recensione {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    private int voto;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
    @ManyToOne
    @JoinColumn(name = "esercizio_id")
    private EsercizioCommerciale esercizioCommerciale;
}
