package com.example.orders.citta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CittaService {

@Autowired
    CittaRepository cittaRepository;


public Citta save(Citta citta){
    return  cittaRepository.save(citta);
}

public Citta getById(long id){
    return cittaRepository.findById(id).get();
}

public boolean deleteById(long id){
    try{
        cittaRepository.deleteById(id);
    return true;
    }catch (Exception e){
        return false;
    }
}

public List<Citta> getAll(){
    return cittaRepository.findAll();
}
}
