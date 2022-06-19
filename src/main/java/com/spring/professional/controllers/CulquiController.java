package com.spring.professional.controllers;

import com.culqi.Culqi;
import com.culqi.util.CurrencyCode;
import com.spring.professional.dto.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/culqui")
@RequiredArgsConstructor
public class CulquiController {


    //private final SateliteService sateliteService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping()
    public Mono<?> getUser() throws Exception {

        Culqi culqi = new Culqi();
        culqi.public_key = "pk_test_6e548fd65b8c6fac";
        culqi.secret_key =  "sk_test_a4dec678a40b6fce";

        log.info("-----------------------------------");

        Map<String, Object> token = new HashMap<String, Object>();
        token.put("card_number", "4111111111111111");
        token.put("cvv", "123");
        token.put("email", "wm@wm.com");
        token.put("expiration_month", 9);
        token.put("expiration_year", 2020);
        Map<String, Object> token_created = culqi.token.create(token);


        log.info("-----------------------------------");

        Map<String, Object> charge = new HashMap<String, Object>();
        Map<String, Object> antifraudDetails = new HashMap<String, Object>();
        antifraudDetails.put("address", "Calle Narciso de Colina 421 Miraflores");
        antifraudDetails.put("address_city", "LIMA");
        antifraudDetails.put("country_code", "PE");
        antifraudDetails.put("first_name", "Willy");
        antifraudDetails.put("last_name", "Aguirre");
        antifraudDetails.put("phone_number", "012767623");

        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("oder_id", "124");
        charge.put("amount",1000);
        charge.put("capture", true);
        charge.put("currency_code", CurrencyCode.PEN);
        charge.put("description","Venta de prueba");
        charge.put("email","test@culqi.com");
        charge.put("installments", 0);
        charge.put("antifraud_details", antifraudDetails);
        charge.put("metadata", metadata);
        charge.put("source_id", token_created.get("id").toString());
        Map<String, Object> charge_created = culqi.charge.create(charge);

        Map<String, Object> customer = new HashMap<String, Object>();
        customer.put("address","Av Lima 123");
        customer.put("address_city","Lima");
        customer.put("country_code","PE");
        customer.put("email","tst@culqi.com");
        customer.put("first_name","Test");
        customer.put("last_name","Cuqli");
        customer.put("phone_number",99004356);
        Map<String, Object> customer_created = culqi.customer.create(customer);

        Map<String, Object> card = new HashMap<String, Object>();
        card.put("customer_id",customer_created.get("id").toString());
        card.put("token_id",token_created.get("id").toString());
        Map<String, Object> card_created = culqi.card.create(card);

        return Mono.empty();
    }



    @RequestMapping(value= "/charges", method = RequestMethod.POST, produces = "application/json")
    public Map<String, Object> charges(@RequestParam("token") String token, @RequestParam("installments") int installments) throws Exception {

        Culqi culqi = new Culqi();
        culqi.public_key = "pk_test_6e548fd65b8c6fac";
        culqi.secret_key =  "sk_test_a4dec678a40b6fce";

        Map<String, Object> charge = new HashMap<String, Object>();

        Map<String, Object> antifraudDetails = new HashMap<String, Object>();
        antifraudDetails.put("address", "Calle Narciso de Colina 421 Miraflores");
        antifraudDetails.put("address_city", "LIMA");
        antifraudDetails.put("country_code", "PE");
        antifraudDetails.put("first_name", "Willy");
        antifraudDetails.put("last_name", "Aguirre");
        antifraudDetails.put("phone_number", "012767623");

        Map<String, Object> metadata = new HashMap<String, Object>();
        metadata.put("oder_id", "124");

        charge.put("amount",1000);
        charge.put("capture", true);
        charge.put("currency_code", CurrencyCode.PEN);
        charge.put("description","Venta de prueba");
        charge.put("email","willy.aguirre@culqi.com");
        charge.put("installments", installments);
        charge.put("antifraud_details", antifraudDetails);
        charge.put("metadata", metadata);
        charge.put("source_id", token);

        return culqi.charge.create(charge);

    }

    @GetMapping("/satelite/{id}")
    public Root getSatelite(@PathVariable int id){
        String uri = "https://api.wheretheiss.at/v1/satellites/"+id;
        RestTemplate restTemplate = new RestTemplate();
        var result = restTemplate.getForObject(uri,Root.class);
        return result;
        //return sateliteService.getSatelite(id);
    }

    @GetMapping("/satelite/2/{id}")
    public Mono<Root> getSatelite2(@PathVariable int id){
        String uri = "https://api.wheretheiss.at/v1/satellites/"+id;
        Mono<Root> statelite = WebClient.create()
                .get()
                .uri(uri)
                .retrieve()
                .bodyToMono(Root.class);
        return statelite;
    }


}
