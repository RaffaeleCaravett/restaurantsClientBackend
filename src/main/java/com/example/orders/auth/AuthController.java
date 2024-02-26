package com.example.orders.auth;

import com.example.orders.cliente.Cliente;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.exceptions.BadRequestException;
import com.example.orders.payloads.entities.*;
import com.example.orders.security.JWTTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;
@Autowired
    JWTTools jwtTools;
    @PostMapping("/clienteLogin")
    public UserLoginSuccessDTO clienteLogin (@RequestBody UserLoginDTO userLoginDTO)throws Exception {

        return new UserLoginSuccessDTO(authService.authenticateCliente(userLoginDTO));
    }

    @PostMapping("/esercizioLogin")
    public UserLoginSuccessDTO esercizioLogin (@RequestBody UserLoginDTO userLoginDTO)throws Exception {

        return new UserLoginSuccessDTO(authService.authenticateEsercizio(userLoginDTO));
    }
    @GetMapping("/cliente/{token}")
    @ResponseStatus(HttpStatus.OK)
    public Cliente verifyToken(@PathVariable String token){
        return (Cliente) jwtTools.verifyClienteToken(token);
    }
    @GetMapping("/esercizio/{token}")
    @ResponseStatus(HttpStatus.OK)
    public EsercizioCommerciale verifyEsercizioToken(@PathVariable String token){
        return (EsercizioCommerciale) jwtTools.verifyEsercizioToken(token);
    }
    @GetMapping("/refreshClienteToken/{refreshToken}")
    @ResponseStatus(HttpStatus.OK)
    public Token verifyClienteRefreshToken(@PathVariable String refreshToken){
        return jwtTools.verifyClienteRefreshToken(refreshToken);
    }

    @GetMapping("/refreshEsercizioToken/{refreshToken}")
    @ResponseStatus(HttpStatus.OK)
    public Token verifyEsercizioRefreshToken(@PathVariable String refreshToken){
        return jwtTools.verifyEsercizioRefreshToken(refreshToken);
    }
    @PostMapping("/esercizio/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public EsercizioCommerciale saveEsercizio(@RequestBody @Validated EsercizioCommercialeDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return authService.saveEsercizio(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @PostMapping("/cliente/register")
    @ResponseStatus(HttpStatus.CREATED) // <-- 201
    public Cliente saveEsercizio(@RequestBody @Validated ClienteDTO body, BindingResult validation){
        if(validation.hasErrors()){
            throw new BadRequestException(validation.getAllErrors());
        } else {
            try {
                return authService.save(body);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    
}
