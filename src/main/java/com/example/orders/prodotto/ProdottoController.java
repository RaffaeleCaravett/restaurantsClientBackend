package com.example.orders.prodotto;

import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.ProdottoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/prodotto")
public class ProdottoController {
@Autowired
    ProdottoService prodottoService;


@PostMapping("")
    public Prodotto save(@RequestBody @Validated ProdottoDTO prodottoDTO, BindingResult validation){
    if(validation.hasErrors()){
        throw new BadRequestException(validation.getAllErrors());
    }
    return prodottoService.save(prodottoDTO);
}
@DeleteMapping("/{esercizioId}/{id}")
    public boolean deleteById(@PathVariable long esercizioId, @PathVariable long id){
    return prodottoService.deleteById(esercizioId,id);
}
@GetMapping("")
    public List<Prodotto> getAll(){
    return prodottoService.getAll();
}
@GetMapping("/{id}")
    public Prodotto getById(@PathVariable long id){
    return prodottoService.getById(id);
}
    @PutMapping("/{id}")
    public Prodotto putById(@PathVariable long id, @RequestBody ProdottoDTO prodottoDTO){
        return prodottoService.putById(id,prodottoDTO);
    }

}
