package com.example.orders.cliente;

import com.cloudinary.Cloudinary;
import com.example.orders.payloads.entities.ClienteDTO;
import com.example.orders.user.User;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;

import java.util.List;
import java.util.Map;
import java.io.IOException;

@Service
public class ClienteService {
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    private Cloudinary cloudinary;



    public boolean deleteById(long id){
        try{
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
}
