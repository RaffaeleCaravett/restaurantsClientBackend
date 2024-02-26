package com.example.orders.cliente;

import com.cloudinary.Cloudinary;
import com.example.orders.citta.CittaRepository;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.esercizioCommerciale.EsercizioCommercialeService;
import com.example.orders.esercizioCommerciale.EsericizioCommercialeRepository;
import com.example.orders.payloads.entities.ClienteDTO;
import com.example.orders.prodotto.Prodotto;
import com.example.orders.prodotto.ProdottoRepository;
import com.example.orders.prodotto.ProdottoService;
import com.example.orders.user.User;
import jakarta.transaction.Transactional;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.io.IOException;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;
    @Autowired
    ProdottoRepository prodottoRepository;
    @Autowired
    ProdottoService prodottoService;
    @Autowired
    EsercizioCommercialeService esercizioCommercialeService;
@Autowired
    CittaRepository cittaRepository;

@Transactional
    public boolean deleteById(long id){
        try{
            esercizioCommercialeService.deleteCliente(clienteRepository.findById(id).get());
            prodottoService.deleteCliente(clienteRepository.findById(id).get());
            clienteRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Cliente updateById(long id, ClienteDTO clienteDTO) throws BadRequestException {
        if(clienteRepository.findById(id).isPresent()){
            Cliente cliente = clienteRepository.findById(id).get();
            cliente.setEta(clienteDTO.eta());
            cliente.setNome(clienteDTO.nome());
            cliente.setCognome(clienteDTO.cognome());
            cliente.setEmail(clienteDTO.email());
            cliente.setCitta(cittaRepository.findById(clienteDTO.citta_id()).get());
            return cliente;
        }else {
            throw new BadRequestException("Impossibile trovare il cliente con id " + id);
        }
    }

    public Cliente getById(long id) throws BadRequestException {
        return clienteRepository.findById(id).orElseThrow(() -> new BadRequestException("Utente con id " + id + " non trovato"));
    }

    public List<Cliente> getAll(){
    return clienteRepository.findAll();
    }
    public Page<Cliente> getAllPaginated(Pageable pageable){
        return clienteRepository.findAll(pageable);
    }

public Cliente updateImage(long id,MultipartFile file) throws BadRequestException {
        if(clienteRepository.findById(id).isPresent()){
            Cliente cliente = clienteRepository.findById(id).get();
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");
                cliente.setImmagine_profilo(imageUrl);
                return cliente;
            } catch (IOException e) {
                throw new RuntimeException("Impossibile caricare l'immagine", e);
            }
        }else{
            throw new BadRequestException("Utente con id " + id + " non trovato");
        }
}

public List<Cliente> findByCittaId(long citta_id){
        return clienteRepository.findByCitta_Id(citta_id);
}

public EsercizioCommerciale addToBusiness(long cliente_id,long esercizio_id) throws BadRequestException {
        if(esericizioCommercialeRepository.findById(esercizio_id).isPresent()&&clienteRepository.findById(cliente_id).isPresent()){

           Cliente cliente= clienteRepository.findById(cliente_id).get();
           cliente.getEsercizioCommercialeList().add(esericizioCommercialeRepository.findById(esercizio_id).get());
clienteRepository.save(cliente);
return esericizioCommercialeRepository.findById(esercizio_id).get();
        }else {
            throw new BadRequestException("Cliente o esercizio non presenti a db");
        }
}

    public Prodotto addToProdotti(long cliente_id, long prodotto_id) throws BadRequestException {
        if(prodottoRepository.findById(prodotto_id).isPresent()&&clienteRepository.findById(cliente_id).isPresent()){

            Cliente cliente= clienteRepository.findById(cliente_id).get();
            cliente.getProdotti().add(prodottoRepository.findById(prodotto_id).get());
            clienteRepository.save(cliente);
            return prodottoRepository.findById(prodotto_id).get();
        }else {
            throw new BadRequestException("Cliente o prodotto non presenti a db");
        }
    }
}
