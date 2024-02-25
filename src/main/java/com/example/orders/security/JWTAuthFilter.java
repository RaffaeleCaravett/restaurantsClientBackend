package com.example.orders.security;

import com.example.orders.cliente.Cliente;
import com.example.orders.cliente.ClienteService;
import com.example.orders.esercizioCommerciale.EsercizioCommerciale;
import com.example.orders.esercizioCommerciale.EsercizioCommercialeService;
import com.example.orders.exceptions.UnauthorizedException;
import com.example.orders.user.User;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {

@Autowired
    private JWTTools jwtTools;
@Autowired
    private ClienteService clienteService;
@Autowired
    private EsercizioCommercialeService esercizioCommercialeService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 0. Questo metodo verrà eseguito per ogni request che richieda di essere autenticati
        // 1. Verifichiamo se c'è l'header Authorization e estraiamo il token da esso
        String authHeader = request.getHeader("Authorization"); // authHeader --> Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIyIiwiaWF0IjoxNjk5ODczNTI3LCJleHAiOjE3MDA0NzgzMjd9.bCJaensC-bddAiDfU6Jt6JNN8Wooo6lEzypQkylEnUY
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new UnauthorizedException("Per favore passa il Bearer Token nell'Authorization header");
        } else {
            String token = authHeader.substring(7);
//            System.out.println("TOKEN -> " + token);
            // 2. Verifico che il token non sia nè scaduto nè sia stato manipolato

            String requestURI = request.getRequestURI();

            boolean isClienteEndpoint = requestURI.startsWith("/cliente/");
            boolean isEsercizioEndpoint = requestURI.startsWith("/esercizio/");

            if (isClienteEndpoint) {
                jwtTools.verifyClienteToken(token);
            } else if (isEsercizioEndpoint) {
                jwtTools.verifyEsercizioToken(token);
            } else {
                throw new UnauthorizedException("Accesso negato");
            }

            // 3. Se è tutto OK
            // 3.1 Cerco l'utente nel database tramite id (l'id sta nel payload del token, quindi devo estrarlo da lì)
//                String id = jwtTools.extractIdFromToken(token);
//                Utente currentUser = usersService.findById(Integer.parseInt(id));
            // 3.2 Segnalo a Spring Security che l'utente ha il permesso di procedere
            // Se non facciamo questa procedura, ci verrà comunque tornato 403
//                System.out.println(currentUser);

//                Authentication authentication = new UsernamePasswordAuthenticationToken(currentUser, null, authorities);
//                SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3.3 Procediamo (vuol dire andare al prossimo blocco della filter chain)
//                filterChain.doFilter(request, response);

            // 4. Se non è OK -> 401

            String id = jwtTools.extractIdFromToken(token);
            if(isClienteEndpoint){
                Cliente cliente = clienteService.getById(Long.parseLong(id));
                Authentication authentication = new UsernamePasswordAuthenticationToken(cliente, null, cliente.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }else {
                EsercizioCommerciale esercizioCommerciale = esercizioCommercialeService.getById(Long.parseLong(id));
                Authentication authentication = new UsernamePasswordAuthenticationToken(esercizioCommerciale, null, esercizioCommerciale.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // currentUtente.getAuthorities().forEach(System.out::println);




            filterChain.doFilter(request, response);
        }

    }
}
