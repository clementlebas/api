package com.projectweb.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.*;

import java.util.ArrayList;

@Component
public class DatabaseLoader implements CommandLineRunner {

    private final UserRepository repository;
    private final Surveyrepository surveyrepository;

    @Autowired
    public DatabaseLoader(UserRepository repository, Surveyrepository surveyrepository) {
        this.repository = repository;
        this.surveyrepository = surveyrepository;
    }

    @Override
    public void run(String... strings) throws Exception {
        this.repository.save(new User("toto", "toto", false));
        this.surveyrepository.save(new Survey("surveyToto", "dateTiti", new ArrayList<String>(), 0));
    }
}