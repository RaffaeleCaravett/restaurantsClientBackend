package com.example.orders.ingrediente;

import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.IngredienteDTO;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredienteService {
    @Autowired
IngredienteRepository ingredienteRepository;


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

    public boolean deleteById(long id){
        try{
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
