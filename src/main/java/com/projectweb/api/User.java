package com.projectweb.api;

import ch.qos.logback.core.encoder.EchoEncoder;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class User {

    @Id @GeneratedValue(strategy=GenerationType.AUTO) long id;
    @Column(name = "firstname")
    private String firstname ;
    private String password;
    private String token;

    private User() {}

    public User(String userName, String password, String token) {
        this.firstname  = firstname;
        this.password = password;
        this.token = token;
    }

    public String getUser() {
        return firstname;
    }

    public String getPassword() {
        return password;
    }

    public String getToken() {
        return token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(user, user.firstname ) &&
                Objects.equals(user, user.password) &&
                Objects.equals(user, user.token);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userName='" + firstname + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}