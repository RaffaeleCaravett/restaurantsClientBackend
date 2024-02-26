package com.example.orders.schedaAnagrafica;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SchedaAnagraficaRepository extends JpaRepository<SchedaAnagrafica,Long> {
    Optional<SchedaAnagrafica> findByPartitaIva(String PartitaIva);
    List<SchedaAnagrafica> findByPartitaIvaContaining(String PartitaIva);
}
