package com.example.orders.citta;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CittaRepository extends JpaRepository<Citta,Long> {
    @Query(value = "SELECT * FROM città ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Optional<Citta> findRandomCitta();
}
