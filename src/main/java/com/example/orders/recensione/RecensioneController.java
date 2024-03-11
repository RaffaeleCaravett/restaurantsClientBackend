package com.example.orders.recensione;

import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.RecensioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/recensione")
public class RecensioneController {
    @Autowired
    RecensioneService recensioneService;

    @PostMapping("")
    public Recensione save(@RequestBody @Validated RecensioneDTO recensioneDTO, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return recensioneService.save(recensioneDTO);
    }

    @PutMapping("/{id}")
    public Recensione updateById(@PathVariable long id,@RequestBody RecensioneDTO recensioneDTO){
        return recensioneService.updateById(id,recensioneDTO);
    }

    @DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable long id){
        return recensioneService.deleteById(id);
    }
}
