package com.projectweb.api;

//Exemple de POJO
public class Message {
    private final String token;

    public Message(String token) {
        this.token = token;

    }

    public String getToken() {
        return token;
    }
}
