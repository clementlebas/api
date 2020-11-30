package com.projectweb.api;

import ch.qos.logback.core.encoder.EchoEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    private @Id
    @GeneratedValue
    Long id;
    private String userName;
    private String password;
    private String token;

    private User() {}

    public User(long id, String userName, String password, String token) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.token = token;
    }

    public long getId() {
        return id;
    }

    public String getUser() {
        return userName;
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