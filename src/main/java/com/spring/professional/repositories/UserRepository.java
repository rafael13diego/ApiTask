package com.spring.professional.repositories;

import com.spring.professional.models.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.util.Optional;
@Repository
public interface UserRepository extends ReactiveMongoRepository<User, String> {

    //QueryMethod <-
    //JPQL
    //QueryNative - JPA Specification  (Dynamic Query)
    //Proyecciones
    Mono<Optional<User>> findFirstByNick(String nick);
    Mono<User> findUserByNick(String nick);

    Mono<Boolean> existsUserByNick(String nick);

    Mono<User> findUserById(String idUser);

    Mono<Boolean> existsUserById(String id);
}
