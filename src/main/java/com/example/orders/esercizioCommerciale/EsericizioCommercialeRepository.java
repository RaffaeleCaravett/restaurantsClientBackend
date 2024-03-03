package com.example.orders.esercizioCommerciale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EsericizioCommercialeRepository extends JpaRepository<EsercizioCommerciale,Long> {
    Optional<EsercizioCommerciale> findByEmail(String email);
List<EsercizioCommerciale> findByCitta_Id(Long id);
    List<EsercizioCommerciale> findByCitta_IdAndNomeContaining(Long id,String nome);
    List<EsercizioCommerciale> findByNomeContaining(String nome);
}
