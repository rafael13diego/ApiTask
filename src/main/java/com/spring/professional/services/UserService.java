package com.spring.professional.services;

import com.spring.professional.dto.UserRequest;
import com.spring.professional.exceptions.BusinessException;
import com.spring.professional.mapper.UserRequestToUser;
import com.spring.professional.models.User;
import com.spring.professional.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRequestToUser mapper;
    public Mono<User> saveUser(UserRequest userRequest){
        User user = mapper.map(userRequest);
        return userRepository.save(user);
    }

    public Mono<User> findUserById(String idUser){
        return userRepository.findUserById(idUser);
    };

    public Mono<User> findUserByNick(String nick){
        return userRepository.findUserByNick(nick);
    };

    public Mono<Boolean> existsUserByNick(String nick){
        return userRepository.existsUserByNick(nick);
    }
    public Mono<Boolean> existsUserById(String id){
        return userRepository.existsUserById(id);
    }

    public Flux<User> findAll() {
        return userRepository.findAll();
    }

    public Mono<User> updateUser(User oldUser, UserRequest userRequest) {
        //return findUserById(idUser).flatMap( user -> {
        oldUser.setNick(userRequest.getNick());
        oldUser.setPassword(userRequest.getPassword());
        return userRepository.save(oldUser);
        //});
    }

    public Mono<User> updatePassword(User user, UserRequest userRequest) {
        user.setPassword(userRequest.getPassword());
        return userRepository.save(user);
    }

    public Mono<Void> deleteUser(String userId) {
        return userRepository.deleteById(userId);
    }

    /*


     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        //devolver userdetail : min  (user - password) -

        var user= userRepository.findUserByNick(username).map(userObj -> userObj);

        if (user!=null){
            var usernameMono = user.map( mUser -> {
                return mUser.getNick();
            });
            var passwordMono = user.map(mUser -> {
                return mUser.getPassword();
            });
            return new org.springframework.security.core.userdetails.User(usernameMono.toString() , passwordMono.toString(),
                    false, false,false, false, Collections.EMPTY_LIST);
        }else{
            throw new BusinessException("ERR-801","Your credential are incorrects");
        }


    }
}