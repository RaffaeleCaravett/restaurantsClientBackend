package com.example.orders.acquisto;

import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.AcquistoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/acquisto")
public class AcquistoController {
    @Autowired
    AcquistoService acquistoService;

    @PostMapping("")
    public Acquisto save(@RequestBody @Validated AcquistoDTO acquistoDTO, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        }
        return acquistoService.save(acquistoDTO);
    }
    @GetMapping("")
    public Page<Acquisto> getAll(@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "10") int size,@RequestParam(defaultValue = "id") String orderBy){
        return acquistoService.getAll(page,size,orderBy);
    }
    @GetMapping("/anno/{anno}")
    public List<Acquisto> getByAnno(@PathVariable int anno){
        return acquistoService.getByAnno(anno);
    }
    @GetMapping("/mese/{mese}")
    public List<Acquisto> getByMese(@PathVariable int mese){
        return acquistoService.getByMese(mese);
    }
    @GetMapping("/giorno/{giorno}")
    public List<Acquisto> getByGiorno(@PathVariable int giorno){
        return acquistoService.getByGiorno(giorno);
    }
    @GetMapping("/annoAndCliente/{anno}/{cliente}")
    public List<Acquisto> getByClienteAndAnno(@PathVariable int anno, @PathVariable int cliente){
        return acquistoService.getByClienteAndAnno(anno,cliente);
    }
    @GetMapping("esercizioAndAnno/{esercizio}/{anno}")
    public List<Acquisto> getByEsercizioAndAnno(@PathVariable long esercizio,@PathVariable int anno){
        return acquistoService.getByEsercizioAndAnno(esercizio,anno);
    }
    @GetMapping("/cliente/{id}")
    public List<Acquisto> getByCliente(@PathVariable long id){
        return acquistoService.getByCliente(id);
    }
    @GetMapping("/esercizio/{id}")
    public List<Acquisto> getByEsercizio(@PathVariable long id){
        return acquistoService.getByEsercizio(id);
    }
    @GetMapping("/clienteAnnoMese/{id}/{anno}/{mese}")
    public List<Acquisto> getByClienteAndAnnoAndMese(@PathVariable long id,@PathVariable int anno,@PathVariable int mese){
        return acquistoService.getByClienteAndAnnoAndMese(id,anno,mese);
    }
    @GetMapping("/esercizioAnnoMese/{id}/{anno}/{mese}")
    public List<Acquisto> getByEsercizioAndAnnoAndMese(@PathVariable long id,@PathVariable int anno,@PathVariable int mese){
        return acquistoService.getByEsercizioAndAnnoAndMese(id,anno,mese);
    }

    @GetMapping("/clienteAnnoMeseGiorno/{id}/{anno}/{mese}/{giorno}")
    public List<Acquisto> getByClienteAndAnnoAndMeseAndGiorno(@PathVariable long id,@PathVariable int anno,@PathVariable int mese,@PathVariable int giorno){
        return acquistoService.getByClienteAndAnnoAndMeseAndGiorno(id,anno,mese,giorno);
    }
    @GetMapping("/esercizioAnnoMeseGiorno/{id}/{anno}/{mese}/{giorno}")
    public List<Acquisto> getByEsercizioAndAnnoAndMeseAndGiorno(@PathVariable long id,@PathVariable int anno,@PathVariable int mese,@PathVariable int giorno){
        return acquistoService.getByEsercizioAndAnnoAndMeseAndGiorno(id,anno,mese,giorno);
    }

@DeleteMapping("/{id}")
    public boolean deleteById(@PathVariable long id){
        return acquistoService.deleteById(id);
}

@PutMapping("/{id}")
    public Acquisto putById(@PathVariable long id,@RequestBody AcquistoDTO acquistoDTO){
        return acquistoService.putById(id,acquistoDTO);
}
}

