package com.example.orders.esercizioCommerciale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EsericizioCommercialeRepository extends JpaRepository<EsercizioCommerciale,Long> {
}
