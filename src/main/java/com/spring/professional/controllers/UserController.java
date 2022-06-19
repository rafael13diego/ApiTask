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

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public Flux<User> getAllUsers(){
        return service.findAll();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping("{idUser}")
    public Mono<User> getUser(@Valid @PathVariable String idUser){
        return service.findUserById(idUser)
                .switchIfEmpty(Mono.error(new NotFoundResourceException("ERR-450", "exist user: "+idUser)));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public Mono<User> addUser(@Valid @RequestBody UserRequest userRequest){
        String name3 = userRequest.getNick();
        var name = userRequest.getNick();
        return service.existsUserByNick(name)
                .flatMap( exists -> service.saveUser(userRequest))
                .switchIfEmpty(Mono.error(new ExistsResourceException(name)));
    }

    @ResponseStatus(HttpStatus.ALREADY_REPORTED)
    @PutMapping("/{idUser}")
    public Mono<User> updateUser(@Valid @PathVariable String idUser,
                                                       @Valid @RequestBody UserRequest userRequest) {
        return service.findUserById(idUser)
                .flatMap(oldUser -> service.updateUser(oldUser,userRequest))
                .switchIfEmpty(Mono.error(new BusinessException("ERR-800","Not Found  User")));
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{idUser}")
    public Mono<User> updatePassword(@Valid @PathVariable String idUser,
                                     @RequestBody UserRequest userRequest){
        return service.findUserById(idUser)
                .flatMap( user -> {
                    return service.updatePassword(user,userRequest);
                }).switchIfEmpty(Mono.error(new ExistsResourceException("Existe el recurso:"+idUser)));
    }

   @ResponseStatus(HttpStatus.NO_CONTENT)
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public Mono<?> eliminar(@PathVariable String id){
        return service.findUserById(id)
                .map(p -> service.deleteUser(id))
                .defaultIfEmpty(Mono.error(new NotFoundResourceException("ERR-800","Not found User")));
    }


}
