package com.spring.professional.models;

import lombok.Data;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Document
@ToString
public class User {
    @Id
    private String id;
    @NotNull
    @NotEmpty
    @Indexed(unique=true)
    private String nick;
    @NotBlank
    @NotNull
    @Pattern(regexp = "123|321")
    private String password;
    @NotNull
    private boolean userStatus;


    public User() {
    }

    public User(String id, String nick, String password, boolean userStatus) {
        this.id = id;
        this.nick = nick;
        this.password = password;
        this.userStatus = userStatus;
    }
}
