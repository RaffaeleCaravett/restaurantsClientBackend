package com.example.orders.prodotto;

import com.example.orders.enums.TipoProdotto;
import com.example.orders.esercizioCommerciale.EsericizioCommercialeRepository;
import com.example.orders.exceptions.BadRequestException;
import com.example.orders.ingrediente.Ingrediente;
import com.example.orders.ingrediente.IngredienteRepository;
import com.example.orders.payloads.entities.ProdottoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdottoService {
    @Autowired
    ProdottoRepository prodottoRepository;
    @Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;
@Autowired
    IngredienteRepository ingredienteRepository;
    public Prodotto save(ProdottoDTO prodottoDTO){
        Prodotto prodotto = new Prodotto();
        prodotto.setNomeProdotto(prodottoDTO.nome());
        prodotto.setTipoProdotto(TipoProdotto.valueOf(prodottoDTO.tipoProdotto()));
        prodotto.setEsercizioCommerciale(esericizioCommercialeRepository.findById(prodottoDTO.esercizio_id()).get());
        List<Ingrediente> ingredientes=new ArrayList<>();
        for(Long l : prodottoDTO.ingredienti_id()){
            ingredientes.add(ingredienteRepository.findById(l).get());
        }
        prodotto.setIngredientes(ingredientes);
       double prezzo = 0;
        for(Ingrediente i : prodotto.getIngredientes()){
           prezzo+=i.getPrezzo();
       }
prodotto.setPrezzo(prezzo);
        return prodottoRepository.save(prodotto);
    };

    public List<Prodotto> getAll(){
        return prodottoRepository.findAll();
    }
    public Prodotto getById(long id){
        return prodottoRepository.findById(id).get();
    }

    public Prodotto putById(long id, ProdottoDTO prodottoDTO){
        Prodotto prodotto = prodottoRepository.findById(id).get();

        prodotto.setNomeProdotto(prodottoDTO.nome());
        prodotto.setTipoProdotto(TipoProdotto.valueOf(prodottoDTO.tipoProdotto()));
        prodotto.setEsercizioCommerciale(esericizioCommercialeRepository.findById(prodottoDTO.esercizio_id()).get());
        List<Ingrediente> ingredientes=new ArrayList<>();
        for(Long l : prodottoDTO.ingredienti_id()){
            ingredientes.add(ingredienteRepository.findById(l).get());
        }
        prodotto.setIngredientes(ingredientes);
        double prezzo = 0;
        for(Ingrediente i : prodotto.getIngredientes()){
            prezzo+=i.getPrezzo();
        }
        prodotto.setPrezzo(prezzo);
        return prodottoRepository.save(prodotto);

    }

    public boolean deleteById(long esercizio_id,long id){
        try {
            Prodotto prodotto = this.getById(id);
            if(prodotto.getEsercizioCommerciale().getId()==esercizio_id){
                prodottoRepository.deleteById(id);
                return true;
            }else {
                throw new BadRequestException("Non puoi eliminare i prodotti degli altri");
            }
        }catch (Exception e){
            return false;
        }
        }
}
