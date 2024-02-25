package com.example.orders.ingrediente;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IngredienteRepository extends JpaRepository<Ingrediente,Long> {
    Optional<Ingrediente> findByNomeIgnoreCase(String nome);
    List<Ingrediente> findByProdottosId(long id);
}
