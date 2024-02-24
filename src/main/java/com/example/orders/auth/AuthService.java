package com.example.orders.auth;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.orders.cliente.Cliente;
import com.example.orders.cliente.ClienteRepository;
import com.example.orders.enums.Role;
import com.example.orders.enums.TipoEsercizio;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.esercizioCommerciale.EsericizioCommercialeRepository;
import com.example.orders.payloads.entities.ClienteDTO;
import com.example.orders.payloads.entities.EsercizioCommercialeDTO;
import com.example.orders.payloads.entities.SchedaAnagraficaDTO;
import com.example.orders.schedaAnagrafica.SchedaAnagrafica;
import com.example.orders.schedaAnagrafica.SchedaAnagraficaRepository;
import com.example.orders.schedaAnagrafica.SchedaAnagraficaService;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;
import java.util.prefs.BackingStoreException;

@Service
public class AuthService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;
    @Autowired
    private Cloudinary cloudinary;

    @Autowired
    SchedaAnagraficaRepository schedaAnagraficaRepository;

    public Cliente save(ClienteDTO clienteDTO, MultipartFile file) throws BadRequestException {
        if(clienteRepository.findByEmail(clienteDTO.email()).isPresent()){
            throw new BadRequestException("Utente con email " + clienteDTO.email() + " già presente.");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.nome());
        cliente.setCognome(clienteDTO.cognome());
        cliente.setEmail(clienteDTO.email());
        cliente.setEta(clienteDTO.eta());
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            cliente.setImmagine_profilo(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }
        return cliente;
    }


    public EsercizioCommerciale saveEsercizio(EsercizioCommercialeDTO esercizioCommercialeDTO, MultipartFile file) throws BadRequestException {
        if(esericizioCommercialeRepository.findByEmail(esercizioCommercialeDTO.email()).isPresent()){
            throw new BadRequestException("Esercizio commerciale con email " + esercizioCommercialeDTO.email() + " già presente.");
        }
     EsercizioCommerciale esercizioCommerciale = new EsercizioCommerciale();
        esercizioCommerciale.setNome(esercizioCommercialeDTO.nome());
        esercizioCommerciale.setEmail(esercizioCommercialeDTO.email());
        esercizioCommerciale.setTipoEsercizio(TipoEsercizio.valueOf(esercizioCommercialeDTO.tipoEsercizio()));
esercizioCommerciale.setIndirizzo(esercizioCommercialeDTO.indirizzo());
esercizioCommerciale.setRole(Role.Attivita);
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            esercizioCommerciale.setImmagine_profilo(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }
        return esercizioCommerciale;
    }

    public SchedaAnagrafica saveAnagrafica(SchedaAnagraficaDTO schedaAnagraficaDTO) throws BadRequestException {
        SchedaAnagrafica schedaAnagrafica = new SchedaAnagrafica();
        schedaAnagrafica.setCapitaleSociale(schedaAnagraficaDTO.capitaleSociale());
        schedaAnagrafica.setPIva(schedaAnagraficaDTO.pIva());
        schedaAnagrafica.setRappresentante(schedaAnagraficaDTO.rappresentante());
        EsercizioCommerciale esercizioCommerciale = new EsercizioCommerciale();
        if(esericizioCommercialeRepository.findById(schedaAnagraficaDTO.esercizio_id()).isPresent()) {
   esercizioCommerciale = esericizioCommercialeRepository.findById(schedaAnagraficaDTO.esercizio_id()).get();
}    else {
    throw new BadRequestException("esercizio con id " + schedaAnagraficaDTO.esercizio_id() + " non presente");
}

    schedaAnagrafica.setEsercizioCommerciale(esercizioCommerciale);

        return schedaAnagraficaRepository.save(schedaAnagrafica);

    }


}
