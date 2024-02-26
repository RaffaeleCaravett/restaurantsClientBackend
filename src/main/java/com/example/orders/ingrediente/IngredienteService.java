package com.example.orders.ingrediente;

import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.IngredienteDTO;
import com.example.orders.prodotto.Prodotto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {
    @Autowired
IngredienteRepository ingredienteRepository;
    @PersistenceContext
    private EntityManager entityManager;

    public Ingrediente save (IngredienteDTO ingredienteDTO){
        if(!ingredienteRepository.findByNomeIgnoreCase(ingredienteDTO.nome()).isPresent()){
            Ingrediente ingrediente = new Ingrediente();
            ingrediente.setNome(ingredienteDTO.nome());
            ingrediente.setPrezzo(ingredienteDTO.prezzo());
            return ingredienteRepository.save(ingrediente);
        }else{
            throw new BadRequestException("Ingrediente già presente");
        }
    }

    public Ingrediente putIngredienteById(long id, IngredienteDTO ingredienteDTO){
        Ingrediente ingrediente = ingredienteRepository.findById(id).get();
        ingrediente.setPrezzo(ingredienteDTO.prezzo());
ingrediente.setNome(ingredienteDTO.nome());
    return  ingredienteRepository.save(ingrediente);
    }
    @Transactional
    public void deleteIngrediente(Ingrediente ingrediente) {

        Query deleteIngredienteQuery = entityManager.createNativeQuery("DELETE FROM prodotto_ingrediente WHERE ingrediente_id = ?");
        deleteIngredienteQuery.setParameter(1, ingrediente.getId());
        deleteIngredienteQuery.executeUpdate();
    }
    @Transactional
    public boolean deleteById(long id){
        try{
            deleteIngrediente(ingredienteRepository.findById(id).get());
        ingredienteRepository.deleteById(id);
    return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<Ingrediente> getAll(){
        return ingredienteRepository.findAll();
    }
    public Ingrediente getById(long id){
        return ingredienteRepository.findById(id).get();
    }
    public List<Ingrediente> getByProdottoId(long id){
        return ingredienteRepository.findByProdottosId(id);
    }
}
