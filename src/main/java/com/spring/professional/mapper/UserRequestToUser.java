package com.spring.professional.mapper;


import com.spring.professional.dto.UserRequest;
import com.spring.professional.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestToUser implements IMapper<UserRequest, User>{

    @Override
    public User map(UserRequest in) {
        User user = new User();
        user.setNick(in.getNick());
        user.setPassword(in.getPassword());
        user.setUserStatus(true);
        return user;
    }
}
