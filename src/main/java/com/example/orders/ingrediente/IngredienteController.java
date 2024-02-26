package com.example.orders.ingrediente;

import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.IngredienteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ingrediente")
public class IngredienteController {
    @Autowired
    IngredienteService ingredienteService;


    @PostMapping("")
    public Ingrediente save(@RequestBody @Validated IngredienteDTO ingredienteDTO, BindingResult validations){
        if(validations.hasErrors()){
            throw new BadRequestException(validations.getAllErrors());
        }
        return ingredienteService.save(ingredienteDTO);
    }

    @GetMapping("")
    public List<Ingrediente> getAll(){
        return ingredienteService.getAll();
    }

    @PutMapping("/{id}")
    public Ingrediente putById(@PathVariable long id, @RequestBody IngredienteDTO ingredienteDTO){
        return  ingredienteService.putIngredienteById(id,ingredienteDTO);
    }

    @GetMapping("/{id}")
    public Ingrediente findById(@PathVariable long id){
        return ingredienteService.getById(id);
    }

    @GetMapping("/prodotto/{id}")
    public List<Ingrediente> findByProdottoId(@PathVariable long id){
        return ingredienteService.getByProdottoId(id);
    }
}
