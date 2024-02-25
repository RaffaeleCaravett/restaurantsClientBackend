package com.example.orders.security;

import com.example.orders.cliente.Cliente;
import com.example.orders.cliente.ClienteRepository;
import com.example.orders.esercizioCommerciale.EsericizioCommercialeRepository;
import com.example.orders.exceptions.UnauthorizedException;
import com.example.orders.payloads.entities.Token;
import com.example.orders.user.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${spring.jwt.secret}")
    private String secret;

    @Autowired
    ClienteRepository clienteRepository;

    @Autowired
    EsericizioCommercialeRepository esericizioCommercialeRepository;


    public Token createToken(User user){
        String accessToken = Jwts.builder().setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*45))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

        String refreshToken = Jwts.builder().setSubject(String.valueOf(user.getId()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*60*60 *24 *7))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes())).compact();

        Token token = new Token(accessToken,refreshToken);
        return token;

    }

public User verifyClienteToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId = claims.getSubject();
            return clienteRepository.findById(Long.valueOf(userId)).get();
        }catch (Exception e){
            throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
        }
}

public User verifyEsercizioToken(String token){
    try {
        Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parse(token).getBody();
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build()
                .parseClaimsJws(token)
                .getBody();
        String userId = claims.getSubject();
        return esericizioCommercialeRepository.findById(Long.valueOf(userId)).get();
    }catch (Exception e){
        throw new UnauthorizedException("Il token non è valido! Per favore effettua nuovamente il login!");
    }
}


public Token verifyClienteRefreshToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId= claims.getSubject();

            return  this.createToken(clienteRepository.findById(Long.valueOf(userId)).get());
        }catch (Exception e){
            throw new UnauthorizedException("Il refresh token non è valido. Accedi nuovamente.");
        }
}
    public Token verifyEsercizioRefreshToken(String token){
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build().parse(token).getBody();
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            String userId= claims.getSubject();

            return  this.createToken(esericizioCommercialeRepository.findById(Long.valueOf(userId)).get());
        }catch (Exception e){
            throw new UnauthorizedException("Il refresh token non è valido. Accedi nuovamente.");
        }
    }

public String extractIdFromToken(String token){
        return  Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseClaimsJws(token).getBody().getSubject();
}
}
