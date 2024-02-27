package com.example.orders.auth;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.example.orders.citta.CittaRepository;
import com.example.orders.cliente.Cliente;
import com.example.orders.cliente.ClienteRepository;
import com.example.orders.enums.Role;
import com.example.orders.enums.TipoEsercizio;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.esercizioCommerciale.EsericizioCommercialeRepository;
import com.example.orders.exceptions.UnauthorizedException;
import com.example.orders.payloads.entities.*;
import com.example.orders.schedaAnagrafica.SchedaAnagrafica;
import com.example.orders.schedaAnagrafica.SchedaAnagraficaRepository;
import com.example.orders.security.JWTTools;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;
    @Autowired
    private Cloudinary cloudinary;
    @Autowired
    CittaRepository cittaRepository;
@Autowired
    JWTTools jwtTools;
    @Autowired
    SchedaAnagraficaRepository schedaAnagraficaRepository;
    @Autowired
    private PasswordEncoder bcrypt;

    public Cliente save(ClienteDTO clienteDTO, MultipartFile file) throws IOException  {
        if(clienteRepository.findByEmail(clienteDTO.email()).isPresent()){
            throw new com.example.orders.exceptions.BadRequestException("Utente con email " + clienteDTO.email() + " già presente.");
        }
        Cliente cliente = new Cliente();
        cliente.setNome(clienteDTO.nome());
        cliente.setCognome(clienteDTO.cognome());
        cliente.setEmail(clienteDTO.email());
        cliente.setEta(clienteDTO.eta());
        cliente.setCitta(cittaRepository.findById(clienteDTO.citta_id()).get());
        cliente.setPassword(bcrypt.encode(clienteDTO.password()));
        cliente.setProdotti(new ArrayList<>());
        cliente.setEsercizioCommercialeList(new ArrayList<>());
        if(file!=null){
            try {
                Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = (String) uploadResult.get("url");
                cliente.setImmagine_profilo(imageUrl);
            } catch (IOException e) {
                throw new RuntimeException("Impossibile caricare l'immagine", e);
            }
        }else{
            cliente.setImmagine_profilo("assets/forms/empty-avatar.webp");
        }

        return clienteRepository.save(cliente);
    }


    public EsercizioCommerciale saveEsercizio(EsercizioCommercialeDTO esercizioCommercialeDTO, MultipartFile file) throws IOException  {
        if(esericizioCommercialeRepository.findByEmail(esercizioCommercialeDTO.email()).isPresent()){
            throw new com.example.orders.exceptions.BadRequestException("Esercizio commerciale con email " + esercizioCommercialeDTO.email() + " già presente.");
        }
     EsercizioCommerciale esercizioCommerciale = new EsercizioCommerciale();
        esercizioCommerciale.setNome(esercizioCommercialeDTO.nome());
        esercizioCommerciale.setEmail(esercizioCommercialeDTO.email());
        esercizioCommerciale.setTipoEsercizio(TipoEsercizio.valueOf(esercizioCommercialeDTO.tipoEsercizio()));
esercizioCommerciale.setIndirizzo(esercizioCommercialeDTO.indirizzo());
esercizioCommerciale.setRole(Role.Attivita);
esercizioCommerciale.setCitta(cittaRepository.findById(esercizioCommercialeDTO.citta_id()).get());
esercizioCommerciale.setPassword(bcrypt.encode(esercizioCommercialeDTO.password()));
       if(file!=null){
        try {
            Map<String, Object> uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
            String imageUrl = (String) uploadResult.get("url");
            esercizioCommerciale.setImmagine_profilo(imageUrl);
        } catch (IOException e) {
            throw new RuntimeException("Impossibile caricare l'immagine", e);
        }}else{
        esercizioCommerciale.setImmagine_profilo("assets/forms/empty-avatar.webp");
    }
        return esericizioCommercialeRepository.save(esercizioCommerciale);
    }

    public SchedaAnagrafica saveAnagrafica(SchedaAnagraficaDTO schedaAnagraficaDTO) throws IOException {
        SchedaAnagrafica schedaAnagrafica = new SchedaAnagrafica();
        schedaAnagrafica.setCapitaleSociale(schedaAnagraficaDTO.capitaleSociale());
        schedaAnagrafica.setPartitaIva(schedaAnagraficaDTO.pIva());
        schedaAnagrafica.setRappresentante(schedaAnagraficaDTO.rappresentante());
        EsercizioCommerciale esercizioCommerciale = new EsercizioCommerciale();
        if(esericizioCommercialeRepository.findById(schedaAnagraficaDTO.esercizio_id()).isPresent()) {
   esercizioCommerciale = esericizioCommercialeRepository.findById(schedaAnagraficaDTO.esercizio_id()).get();
}    else {
    throw new com.example.orders.exceptions.BadRequestException("esercizio con id " + schedaAnagraficaDTO.esercizio_id() + " non presente");
}
    schedaAnagrafica.setEsercizioCommerciale(esercizioCommerciale);
        return schedaAnagraficaRepository.save(schedaAnagrafica);
    }
    public Token authenticateCliente(UserLoginDTO body) throws Exception {
        // 1. Verifichiamo che l'email dell'utente sia nel db
       Cliente user = clienteRepository.findByEmail(body.email()).get();
        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if(bcrypt.matches(body.password(), user.getPassword()))  {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
    public Token authenticateEsercizio(UserLoginDTO body) throws Exception {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        EsercizioCommerciale user = esericizioCommercialeRepository.findByEmail(body.email()).get();
        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if(bcrypt.matches(body.password(), user.getPassword()))  {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }
}
