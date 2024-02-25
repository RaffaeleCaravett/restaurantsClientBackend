package com.example.orders.citta;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citta")
public class CittaController {

    @Autowired
    CittaService cittaService;

    @PostMapping("")
    public Citta save(@RequestBody Citta citta){
        return cittaService.save(citta);
    }

    @GetMapping("")
    public List<Citta> getAll(){
        return  cittaService.getAll();
    }

    @GetMapping("/{id}")
    public Citta getById(@PathVariable long id){
        return cittaService.getById(id);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable long id){
        return cittaService.deleteById(id);
    }
}
