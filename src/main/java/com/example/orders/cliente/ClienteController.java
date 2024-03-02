package com.example.orders.cliente;

import com.example.orders.citta.Citta;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.payloads.entities.ClienteDTO;
import com.example.orders.payloads.entities.ProdottoDTO;
import com.example.orders.prodotto.Prodotto;
import lombok.Getter;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/cliente")
public class ClienteController {

@Autowired
    ClienteService clienteService;

@GetMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('Cliente','Attivita')")
    public Cliente getById(@PathVariable long id) throws BadRequestException {
    return clienteService.getById(id);
}

    @PutMapping("/me")
    @PreAuthorize("hasAnyAuthority('Cliente')")
    public Cliente putById(@AuthenticationPrincipal Cliente currentUser, @RequestBody ClienteDTO clienteDTO) throws BadRequestException {
        return clienteService.updateById(currentUser.getId(),clienteDTO);
    }
    @DeleteMapping("/me")
    @PreAuthorize("hasAnyAuthority('Cliente','Attivita')")
    public boolean deleteById(@AuthenticationPrincipal Cliente cliente){
    return clienteService.deleteById(cliente.getId());
    }

    @GetMapping("/paginated")
    @PreAuthorize("hasAnyAuthority('Cliente','Attivita')")
public Page<Cliente> getAllPaginated(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return clienteService.getAllPaginated(pageable);
    }
@GetMapping("/citta/{id}")
public List<Cliente> getByCittaId(@PathVariable long citta_id){
    return clienteService.findByCittaId(citta_id);
}
    @GetMapping("")
    @PreAuthorize("hasAnyAuthority('Cliente','Attivita')")
    public List<Cliente> getAll(){
        return clienteService.getAll();
    }
@PutMapping("image/{id}")
@PreAuthorize("hasAnyAuthority('Cliente')")
    public Cliente modifyImage(@PathVariable long id, MultipartFile file) throws BadRequestException {
    return clienteService.updateImage(id,file);
}
@GetMapping("addProdotto/{id}/{prodotto_id}")
    public Prodotto addToProdotti(@PathVariable long id, @PathVariable long prodotto_id) throws BadRequestException {
    return clienteService.addToProdotti(id,prodotto_id);
}
    @GetMapping("addEsercizio/{id}/{esercizio_id}")
    public EsercizioCommerciale addToEsercizi(@PathVariable long id, @PathVariable long esercizio_id) throws BadRequestException {
        return clienteService.addToBusiness(id,esercizio_id);
    }

    @GetMapping("byEsercizio/{id}")
    public Page<Cliente> getByEsercizio(@PathVariable long id, @RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy) throws BadRequestException {
        return clienteService.getByEsercizio(id,page,size,orderBy);
    }
}
