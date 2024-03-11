package com.example.orders.recensione;

import com.example.orders.cliente.ClienteRepository;
import com.example.orders.esercizioCommerciale.EsercizioCommercialeService;
import com.example.orders.esercizioCommerciale.EsericizioCommercialeRepository;
import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.RecensioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecensioneService {

@Autowired
    RecensioneRepository recensioneRepository;
@Autowired
    ClienteRepository clienteRepository;
@Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;

public Recensione save(RecensioneDTO recensioneDTO){
    Recensione recensione = new Recensione();
    recensione.setVoto(recensione.getVoto());
recensione.setCliente(clienteRepository.findById(recensioneDTO.cliente_id()).get());
recensione.setEsercizioCommerciale(esericizioCommercialeRepository.findById(recensioneDTO.esercizio_id()).get());
return recensioneRepository.save(recensione);
}

public Recensione updateById(long id,RecensioneDTO recensioneDTO){
    if(recensioneRepository.findById(id).isPresent()){
        Recensione recensione = recensioneRepository.findById(id).get();
recensione.setVoto(recensioneDTO.voto());
return recensioneRepository.save(recensione);
    }else {
        throw new BadRequestException("Non ci sono recensioni con id " + id + ".");
    }
}

public boolean deleteById(long id){
    try {
        recensioneRepository.deleteById(id);
        return true;
    }catch (Exception e){
        return false;
    }
}
}
