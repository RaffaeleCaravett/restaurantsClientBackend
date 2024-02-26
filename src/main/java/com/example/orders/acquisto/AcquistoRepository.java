package com.example.orders.acquisto;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AcquistoRepository extends JpaRepository<Acquisto,Long> {
    List<Acquisto> findByAnno(int anno);
    List<Acquisto> findByMese(int mese);
    List<Acquisto> findByGiorno(int giorno);
    List<Acquisto> findByAnnoAndMese(int anno,int mese);
    List<Acquisto> findByAnnoAndGiorno(int anno,int giorno);
    List<Acquisto> findByMeseAndGiorno(int mese,int giorno);
    List<Acquisto> findByAnnoAndMeseAndGiorno(int anno,int mese, int giorno);
    List<Acquisto> findByCliente_Id(long cliente_id);
    List<Acquisto> findByCliente_IdAndMese(long cliente_id,int mese);
    List<Acquisto> findByCliente_IdAndAnno(long cliente_id,int anno);

    List<Acquisto> findByCliente_IdAndAnnoAndMese(long cliente_id,int anno,int mese);
    List<Acquisto> findByCliente_IdAndAnnoAndMeseAndGiorno(long cliente_id,int anno,int mese, int giorno);
    List<Acquisto> findByEsercizioCommerciale_Id(long esercizioCommerciale_id);
    List<Acquisto> findByEsercizioCommerciale_IdAndMese(long esercizioCommerciale_id,int mese);
    List<Acquisto> findByEsercizioCommerciale_IdAndAnno(long esercizioCommerciale_id,int anno);
    List<Acquisto> findByEsercizioCommerciale_IdAndAnnoAndMese(long esercizioCommerciale_id,int anno,int mese);

    List<Acquisto> findByEsercizioCommerciale_IdAndAnnoAndMeseAndGiorno(long esercizioCommerciale_id,int anno,int mese, int giorno);
List<Acquisto> findByTotaleGreaterThan(double totale);
    List<Acquisto> findByTotaleLessThan(double totale);
    List<Acquisto> findByTotaleBetween(double totale1, double totale2);
}
