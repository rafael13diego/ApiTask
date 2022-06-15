package com.spring.professional.controllers;

import com.spring.professional.dto.UserRequest;
import com.spring.professional.exceptions.BusinessException;
import com.spring.professional.exceptions.ExistsResourceException;
import com.spring.professional.exceptions.NotFoundResourceException;
import com.spring.professional.models.User;
import com.spring.professional.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public Mono<ResponseEntity<Flux<User>>> lista(){
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(service.findAll())
        );
    }
    @GetMapping("{idUser}")
    public Mono<User> getUser(@Valid @PathVariable String idUser){
        return service.findUserById(idUser)
                .switchIfEmpty(Mono.error(new NotFoundResourceException("ERR-450", "exist user: "+idUser)));
    }
    @PostMapping
    public Mono<ResponseEntity<Mono<User>>> addUser(@Valid @RequestBody UserRequest userRequest){
        String name3 = userRequest.getNick();
        var name = userRequest.getNick();
        return service.existsUserByNick(name)
                .handle((exists, asyncError) -> {
                    if (exists){
                        log.debug("Resultado bool?"+exists);
                        asyncError.error(new NotFoundResourceException("ERR-450", "exist user: "+name));
                    }else{
                        asyncError.next(exists);
                    }
                }).map( resp -> new ResponseEntity(service.saveUser(userRequest), HttpStatus.CREATED));
    }

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @PutMapping("/{idUser}")
    public Mono<ResponseEntity<Mono<User>>> updateUser(@Valid @PathVariable String idUser,
                                                       @Valid @RequestBody UserRequest userRequest) {
        return service.findUserById(idUser)
                .handle((exists, asyncOpe) -> {
                    if (exists == null) {
                        asyncOpe.error(new BusinessException("ERR-800", "Not found user"));
                    } else {
                        asyncOpe.next(new ResponseEntity<>(service.updateUser(exists, userRequest), HttpStatus.ACCEPTED));
                    }
                });

    }

    @PatchMapping("/{idUser}")
    //public Mono<ResponseEntity<Mono<User>>> updatePassword(@Valid @PathVariable String idUser,
    public Mono<User> updatePassword(@Valid @PathVariable String idUser,
                                     @RequestBody UserRequest userRequest){
        return service.findUserById(idUser)
                .flatMap( user -> {
                    return service.updatePassword(user,userRequest);
                    //return new ResponseEntity<>(service.updatePassword(user,userRequest),HttpStatus.OK);
                }).switchIfEmpty(Mono.error(new ExistsResourceException("Existe el recurso:"+idUser)));
        /*return service.findUserById(idUser).map( user -> {
            return new ResponseEntity(service.updatePassword(user, userRequest), HttpStatus.OK);
        });*/
    }

    @DeleteMapping("/delete/{idUser}")
    public Mono<ResponseEntity<Mono<?>>> deleteUser(@Valid @PathVariable String idUser) {
        return service.existsUserById(idUser)
                .handle((exists, asyncOpe) -> {
                    if (!exists) {
                        asyncOpe.error(new NotFoundResourceException("ERR-800", "Not found user"));
                    } else {
                        asyncOpe.next(new ResponseEntity(service.deleteUser(idUser), HttpStatus.ACCEPTED));
                    }
                });
    }
    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> eliminar(@PathVariable String id){
        return service.findUserById(id).flatMap(p -> service.deleteUser(id)
                .then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT))))
                .defaultIfEmpty(new ResponseEntity(service.findUserById(id),HttpStatus.NOT_FOUND));
        /*
        return service.findUserById(id).flatMap(p ->{
            return service.deleteUser(id).then(Mono.just(new ResponseEntity<>(HttpStatus.NO_CONTENT)));
        }).defaultIfEmpty(new ResponseEntity(service.findUserById(id),HttpStatus.NOT_FOUND));
         */
    }


}
