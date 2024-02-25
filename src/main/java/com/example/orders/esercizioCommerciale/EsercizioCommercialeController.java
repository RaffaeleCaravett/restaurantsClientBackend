package com.example.orders.esercizioCommerciale;

import com.example.orders.cliente.Cliente;
import com.example.orders.payloads.entities.EsercizioCommercialeDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController("")
@RequestMapping("/esercizio")
public class EsercizioCommercialeController {

@Autowired
EsercizioCommercialeService esercizioCommercialeService;
    @GetMapping("/me")
    public EsercizioCommerciale getById(@AuthenticationPrincipal EsercizioCommerciale currentUser) throws BadRequestException {
        return esercizioCommercialeService.getById(currentUser.getId());
    }
    @GetMapping("/{id}")
    public EsercizioCommerciale getById(@PathVariable long id) throws BadRequestException {
        return esercizioCommercialeService.getById(id);
    }
    @GetMapping("/paginated")
    public Page<EsercizioCommerciale> getAllPaginated(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy ) throws BadRequestException {
       Pageable pageable = PageRequest.of(page,size, Sort.by(orderBy));
        return esercizioCommercialeService.getAllPaginated(pageable);
    }
    @GetMapping("")
    public List<EsercizioCommerciale> getAll() throws BadRequestException {
        return esercizioCommercialeService.getAll();
    }
    @DeleteMapping("/me")
    public boolean deleteById(@AuthenticationPrincipal EsercizioCommerciale currentUser) throws BadRequestException {
        return esercizioCommercialeService.deleteById(currentUser.getId());
    }
    @PutMapping("/{id}")
    public EsercizioCommerciale putById(@PathVariable long id, @RequestBody EsercizioCommercialeDTO esercizioCommercialeDTO) throws BadRequestException {
        return esercizioCommercialeService.putById(id,esercizioCommercialeDTO);
    }
    @PutMapping("/image/{id}")
    public EsercizioCommerciale updateImageById(@PathVariable long id, MultipartFile file) throws BadRequestException {
        return esercizioCommercialeService.updateImage(id,file);
    }
    @GetMapping("/esercizio/{id}")
    public List<EsercizioCommerciale> getByCittaId(@PathVariable long citta_id){
        return esercizioCommercialeService.findByCittaId(citta_id);
    }
    @GetMapping("/clienti/{id}")
    public List<Cliente> getAll(@PathVariable long id) throws BadRequestException {
        return esercizioCommercialeService.getClientiByEsercizioId(id);
    }
}
