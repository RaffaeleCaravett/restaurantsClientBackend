package com.example.orders.esercizioCommerciale;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.orders.citta.CittaRepository;
import com.example.orders.cliente.Cliente;
import com.example.orders.cliente.ClienteRepository;
import com.example.orders.enums.TipoEsercizio;
import com.example.orders.ingrediente.Ingrediente;
import com.example.orders.payloads.entities.EsercizioCommercialeDTO;
import com.example.orders.prodotto.Prodotto;
import com.example.orders.prodotto.ProdottoRepository;
import com.example.orders.schedaAnagrafica.SchedaAnagrafica;
import com.example.orders.schedaAnagrafica.SchedaAnagraficaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
public class EsercizioCommercialeService {

    @Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    CittaRepository cittaRepository;
    @Autowired
    SchedaAnagraficaRepository schedaAnagraficaRepository;
    @Autowired
    ProdottoRepository prodottoRepository;
    @PersistenceContext
    private EntityManager entityManager;
public EsercizioCommerciale putById(long id, EsercizioCommercialeDTO esercizioCommercialeDTO) throws BadRequestException {
    if(esericizioCommercialeRepository.findById(id).isPresent()){
        EsercizioCommerciale esercizioCommerciale= esericizioCommercialeRepository.findById(id).get();
        if(!esercizioCommerciale.getEmail().equals(esercizioCommercialeDTO.email())){
            if(esericizioCommercialeRepository.findByEmail(esercizioCommercialeDTO.email()).isPresent()){
                throw new BadRequestException("Email " + esercizioCommercialeDTO.email() + " già in uso");
            }
        }
        esercizioCommerciale.setEmail(esercizioCommercialeDTO.email());
esercizioCommerciale.setTipoEsercizio(TipoEsercizio.valueOf(esercizioCommercialeDTO.tipoEsercizio()));
        esercizioCommerciale.setIndirizzo(esercizioCommercialeDTO.indirizzo());
        esercizioCommerciale.setNome(esercizioCommercialeDTO.nome());
        esercizioCommerciale.setCitta(cittaRepository.findById(esercizioCommercialeDTO.citta_id()).get());
        return esericizioCommercialeRepository.save(esercizioCommerciale);
    }else{
        throw new BadRequestException("Esercizio con id " + id + " non presente in db.");
    }
}
@Transactional
    public void deleteCliente(Cliente cliente) {

        Query deleteIngredienteQuery = entityManager.createNativeQuery("DELETE FROM cliente_esercizio WHERE user_id = ?");
        deleteIngredienteQuery.setParameter(1, cliente.getId());
        deleteIngredienteQuery.executeUpdate();
    }
    @Transactional
public boolean deleteById(long id){
    try {
        EsercizioCommerciale esercizioCommerciale = esericizioCommercialeRepository.findById(id).get();
        schedaAnagraficaRepository.deleteById(esercizioCommerciale.getSchedaAnagrafica().getId());
       for(Prodotto p : esercizioCommerciale.getProdottos()){
           prodottoRepository.deleteById(p.getId());
       }
    for(Cliente cliente:esercizioCommerciale.getClienteList()){
        deleteCliente(cliente);
    }
       for (Prodotto p: esercizioCommerciale.getProdottos()){
           prodottoRepository.deleteById(p.getId());
       }
        esericizioCommercialeRepository.deleteById(id);
        return true;
    }catch (Exception e){
        return false;
    }
}

public EsercizioCommerciale getById(long id) throws BadRequestException {
    try {
        return esericizioCommercialeRepository.findById(id).get();
    }catch (Exception e){
        throw new BadRequestException(e.getMessage());
    }
}

public List<Cliente> getClientiByEsercizioId(long id) throws BadRequestException {
    if(esericizioCommercialeRepository.findById(id).isPresent()){
        return esericizioCommercialeRepository.findById(id).get().getClienteList();
    }else{
        throw new BadRequestException("Esercizio con id " + id + " non trovato");
    }
}

public Page<EsercizioCommerciale> getAllPaginated(Pageable pageable){
    return esericizioCommercialeRepository.findAll(pageable);
}

    public List<EsercizioCommerciale> getAll(){
        return esericizioCommercialeRepository.findAll();
    }

    public EsercizioCommerciale updateImage(long id, MultipartFile file) throws BadRequestException {
        if(esericizioCommercialeRepository.findById(id).isPresent()){
            EsercizioCommerciale cliente = esericizioCommercialeRepository.findById(id).get();
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");
                cliente.setImmagine_profilo(imageUrl);
                return esericizioCommercialeRepository.save(cliente);
            } catch (IOException e) {
                throw new RuntimeException("Impossibile caricare l'immagine", e);
            }
        }else{
            throw new BadRequestException("Utente con id " + id + " non trovato");
        }
    }
    public List<EsercizioCommerciale> findByCittaIdAndNomeContaining(long citta_id,String nome){
    return esericizioCommercialeRepository.findByCitta_IdAndNomeContaining(citta_id,nome);
    }
}
