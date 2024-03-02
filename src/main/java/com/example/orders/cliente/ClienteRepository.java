package com.example.orders.cliente;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,Long> {
    Optional<Cliente> findByEmail(String email);
    List<Cliente> findByCitta_Id(long citta_id);
Page<Cliente> findByEsercizioCommercialeList_Id(long esercizio_id, Pageable pageable);
}
