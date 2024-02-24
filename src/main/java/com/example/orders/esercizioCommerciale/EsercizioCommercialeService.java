package com.example.orders.esercizioCommerciale;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.orders.cliente.Cliente;
import com.example.orders.enums.TipoEsercizio;
import com.example.orders.payloads.entities.EsercizioCommercialeDTO;
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
        return esericizioCommercialeRepository.save(esercizioCommerciale);
    }else{
        throw new BadRequestException("Esercizio con id " + id + " non presente in db.");
    }
}
public boolean deleteById(long id){
    try {
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
                return cliente;
            } catch (IOException e) {
                throw new RuntimeException("Impossibile caricare l'immagine", e);
            }
        }else{
            throw new BadRequestException("Utente con id " + id + " non trovato");
        }
    }
}
