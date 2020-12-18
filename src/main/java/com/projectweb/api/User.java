package com.projectweb.api;

import ch.qos.logback.core.encoder.EchoEncoder;

import javax.persistence.*;
import java.util.Objects;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Entity
public class User {

    @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;
    @Column(name = "userName")
    private String userName ;
    private String password;
    private String token;
    private Boolean active;

    private User() {}

    public User(String userName, String password, String token) {
        this.userName  = userName;
        this.password = password;
        this.token = token;
        this.active = false;
        //this.mySurvey
    }

    public User(String userName, String password, Boolean active) {
        this.userName  = userName;
        this.password = password;
        this.active = active;
    }

    public String getUser() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    public Boolean getActive() {
        return active;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(user, user.userName) &&
                Objects.equals(user, user.password) &&
                Objects.equals(user, user.token);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}