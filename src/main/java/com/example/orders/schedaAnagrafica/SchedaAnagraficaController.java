package com.example.orders.schedaAnagrafica;

import com.example.orders.payloads.entities.SchedaAnagraficaDTO;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/schedaAnagrafica")
public class SchedaAnagraficaController {
    @Autowired
    SchedaAnagraficaService schedaAnagraficaService;


    @GetMapping("")
    public List<SchedaAnagrafica> getAll(){
        return schedaAnagraficaService.getAll();
    }

    @PutMapping("/me")
    public SchedaAnagrafica putById(@AuthenticationPrincipal SchedaAnagrafica currentUser, SchedaAnagraficaDTO schedaAnagraficaDTO) throws BadRequestException {
        return schedaAnagraficaService.putById(currentUser.getId(),schedaAnagraficaDTO);
    }

    @GetMapping("/me")
    public SchedaAnagrafica getById(@AuthenticationPrincipal SchedaAnagrafica schedaAnagrafica){
        return schedaAnagraficaService.getById(schedaAnagrafica.getId());
    }

    @GetMapping("/{id}")
    public SchedaAnagrafica getById(@PathVariable long id){
        return schedaAnagraficaService.getById(id);
    }

    @DeleteMapping("/me")
    public boolean deleteById(@AuthenticationPrincipal SchedaAnagrafica schedaAnagrafica){
        return schedaAnagraficaService.deleteById(schedaAnagrafica.getId());
    }

    @GetMapping("/pIva/{pIva}")
    public List<SchedaAnagrafica> findByPIvaContaining(@PathVariable String pIva){
        return schedaAnagraficaService.findByPIvaContaining(pIva);
    }
}
