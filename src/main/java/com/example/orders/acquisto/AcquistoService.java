package com.example.orders.acquisto;

import com.example.orders.cliente.ClienteRepository;
import com.example.orders.enums.TipoPagamento;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.esercizioCommerciale.EsericizioCommercialeRepository;
import com.example.orders.payloads.entities.AcquistoDTO;
import com.example.orders.prodotto.Prodotto;
import com.example.orders.prodotto.ProdottoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AcquistoService {
    @Autowired
    AcquistoRepository acquistoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;
    @Autowired
    ProdottoRepository prodottoRepository;

    public Acquisto save(AcquistoDTO acquistoDTO){
        Acquisto acquisto = new Acquisto();
        acquisto.setAnno(acquistoDTO.anno());
        acquisto.setMese(acquistoDTO.mese());
        acquisto.setGiorno(acquistoDTO.giorno());
        acquisto.setTipoPagamento(TipoPagamento.valueOf(acquistoDTO.tipo_pagamento()));
        acquisto.setCliente(clienteRepository.findById(acquistoDTO.cliente_id()).get());
        acquisto.setEsercizioCommerciale(esericizioCommercialeRepository.findById(acquistoDTO.esercizio_id()).get());
        List<Prodotto> prodottos = new ArrayList<>();
        for(Long l : acquistoDTO.prodotto_id()){
            prodottos.add(prodottoRepository.findById(l).get());
        }
    acquisto.setProdottos(prodottos);
        double totale= 0;
        for(Prodotto p : acquisto.getProdottos()){
            totale+=p.getPrezzo();
        }
        totale+=10;
        acquisto.setTotale(totale);
        return acquistoRepository.save(acquisto);
    }
    public Acquisto getById(long id){
        return acquistoRepository.findById(id).get();
    }
    public boolean deleteById(long id) {
        try {
            acquistoRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
        public Acquisto putById(long id,AcquistoDTO acquistoDTO){
        Acquisto acquisto = acquistoRepository.findById(id).get();
            acquisto.setAnno(acquistoDTO.anno());
            acquisto.setMese(acquistoDTO.mese());
            acquisto.setGiorno(acquistoDTO.giorno());
            acquisto.setTipoPagamento(TipoPagamento.valueOf(acquistoDTO.tipo_pagamento()));
            acquisto.setCliente(clienteRepository.findById(acquistoDTO.cliente_id()).get());
            acquisto.setEsercizioCommerciale(esericizioCommercialeRepository.findById(acquistoDTO.esercizio_id()).get());
            List<Prodotto> prodottos = new ArrayList<>();
            for(Long l : acquistoDTO.prodotto_id()){
                prodottos.add(prodottoRepository.findById(l).get());
            }
            acquisto.setProdottos(prodottos);
            double totale= 0;
            for(Prodotto p : acquisto.getProdottos()){
                totale+=p.getPrezzo();
            }
            totale+=10;
            acquisto.setTotale(totale);
            return acquistoRepository.save(acquisto);
        }

        public List<Acquisto> getByAnno(int anno){
        return acquistoRepository.findByAnno(anno);
        }
    public Page<Acquisto> getAll(int page, int size ,String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return acquistoRepository.findAll(pageable);
    }
    public List<Acquisto> getByMese(int mese){
        return acquistoRepository.findByMese(mese);
    }
    public List<Acquisto> getByGiorno(int giorno){
        return acquistoRepository.findByGiorno(giorno);
    }
    public List<Acquisto> getByClienteAndAnno(long id,int anno){
        return acquistoRepository.findByCliente_IdAndAnno(id,anno);
    }
    public List<Acquisto> getByEsercizioAndAnno(long id,int anno){
        return acquistoRepository.findByEsercizioCommerciale_IdAndAnno(id,anno);
    }
    public List<Acquisto> getByEsercizio(long id){
        return acquistoRepository.findByEsercizioCommerciale_Id(id);
    }
    public List<Acquisto> getByCliente(long id){
        return acquistoRepository.findByCliente_Id(id);
    }
    public List<Acquisto> getByClienteAndAnnoAndMeseAndGiorno(long id,int anno,int mese,int giorno){
        return acquistoRepository.findByCliente_IdAndAnnoAndMeseAndGiorno(id,anno,mese,giorno);
    }
    public List<Acquisto> getByEsercizioAndAnnoAndMeseAndGiorno(long id,int anno,int mese,int giorno){
        return acquistoRepository.findByEsercizioCommerciale_IdAndAnnoAndMeseAndGiorno(id,anno,mese,giorno);
    }
    public List<Acquisto> getByClienteAndAnnoAndMese(long id,int anno,int mese){
        return acquistoRepository.findByCliente_IdAndAnnoAndMese(id,anno,mese);
    }
    public List<Acquisto> getByEsercizioAndAnnoAndMese(long id,int anno,int mese){
        return acquistoRepository.findByEsercizioCommerciale_IdAndAnnoAndMese(id,anno,mese);
    }
    public List<Acquisto> findByTotaleGreaterThan(double totale){
        return  acquistoRepository.findByTotaleGreaterThan(totale);
    }
    public List<Acquisto> findByTotaleLessThan(double totale){
        return  acquistoRepository.findByTotaleLessThan(totale);
    }
    public List<Acquisto> findByTotaleBetween(double totale,double totale1){
        return  acquistoRepository.findByTotaleBetween(totale,totale1);
    }
}
