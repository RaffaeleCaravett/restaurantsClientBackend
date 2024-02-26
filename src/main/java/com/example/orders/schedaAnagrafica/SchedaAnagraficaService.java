package com.example.orders.schedaAnagrafica;

import com.example.orders.payloads.entities.SchedaAnagraficaDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SchedaAnagraficaService {

@Autowired
    SchedaAnagraficaRepository schedaAnagraficaRepository;


public SchedaAnagrafica putById(long id, SchedaAnagraficaDTO schedaAnagraficaDTO) throws BadRequestException {
    if(schedaAnagraficaRepository.findById(id).isPresent()){
        SchedaAnagrafica  schedaAnagrafica = schedaAnagraficaRepository.findById(id).get();

        if(!schedaAnagrafica.getPartitaIva().equals(schedaAnagraficaDTO.pIva())){
            if(schedaAnagraficaRepository.findByPartitaIva(schedaAnagraficaDTO.pIva()).isPresent()){
                throw new BadRequestException("Scheda con pIva " + schedaAnagraficaDTO.pIva() + " già presente.");
            }
            schedaAnagrafica.setPartitaIva(schedaAnagraficaDTO.pIva());
        }
       schedaAnagrafica.setRappresentante(schedaAnagraficaDTO.rappresentante());
        schedaAnagrafica.setCapitaleSociale(schedaAnagraficaDTO.capitaleSociale());
return schedaAnagraficaRepository.save(schedaAnagrafica);
    }else {
        throw new BadRequestException("Scheda anagrafica con id " + id + " non presente");
    }
}

public boolean deleteById(long id){
    try {
        schedaAnagraficaRepository.deleteById(id);
        return true;
    }catch (Exception e){
        return false;
    }
}

public SchedaAnagrafica getById(long id){
    return  schedaAnagraficaRepository.findById(id).get();
}

public List<SchedaAnagrafica> findByPIvaContaining(String pIva){
    return schedaAnagraficaRepository.findByPartitaIvaContaining(pIva);
}

public List<SchedaAnagrafica> getAll(){
    return  schedaAnagraficaRepository.findAll();
}
}
