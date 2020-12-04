package com.projectweb.api;

//import java.util.concurrent.atomic.AtomicLong;

import org.apache.maven.artifact.repository.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private UserRepository repository;

    @GetMapping(value = "/api/login")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/api/inscription")
    @ResponseBody
    public String someMethod(@RequestParam("uname") String uname, String password) {
        System.out.println(uname);
        repository.save(new User(uname, password, "vdsnf"));
        System.out.println(repository.findByFirstnameEquals(uname));

        return "inscription" + " " + "Utilisateur créé : " + uname;
    }

    //@PostMapping(value="/api/login")
   // public boolean login(@RequestBody User user, HttpServletRequest request) {
        /*Authentication auth = null;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUser(), user.getPassword());

        try {
            auth = authenticationProvider.authenticate(token);
            SecurityContextHolder.getContext().setAuthentication(auth);
            User user = (User) authentication.getPrincipal();
            user.setPassword(null);
            return true;
        }
        catch (BadCredentialsException exception) {
            // you can also log the exception message, for example using Logger
            return false;
        }*/
        //return "result";
    //}


    /*public User user(@RequestParam(value = "clement", defaultValue = "World") String userName) {
        return new User(counter.incrementAndGet(), String.format(template, userName), "aaa", "vnfikjd");
    }*/
}