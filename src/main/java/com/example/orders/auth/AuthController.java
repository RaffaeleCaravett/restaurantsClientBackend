package com.example.orders.auth;

import com.example.orders.cliente.Cliente;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.payloads.entities.Token;
import com.example.orders.payloads.entities.UserLoginDTO;
import com.example.orders.payloads.entities.UserLoginSuccessDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

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
        return jwtTools.verifyToken(token);
    }
    @GetMapping("/esercizio/{token}")
    @ResponseStatus(HttpStatus.OK)
    public EsercizioCommerciale verifyEsercizioToken(@PathVariable String token){
        return jwtTools.verifyToken(token);
    }
    @GetMapping("/refreshToken/{refreshToken}")
    @ResponseStatus(HttpStatus.OK)
    public Token verifyRefreshToken(@PathVariable String refreshToken){
        return jwtTools.verifyRefreshToken(refreshToken);
    }
}
