package com.projectweb.api;

//import java.util.concurrent.atomic.AtomicLong;

import ch.qos.logback.classic.Logger;
import org.apache.maven.artifact.repository.Authentication;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
public class HomeController {

    @Autowired
    private UserRepository repository;

    @GetMapping(value = "/api")
    public String index() {
        return "index";
    }

    @RequestMapping(value = "/api/login")
    public String connection(String uname, String password) {
        for (User u : repository.findByUserName(uname)) {

            if (u.getPassword().equals(password)) {
                System.out.println(u.getUser() + " connection");
                return "sondage";
            }
        }
        return "index";
    }

    @RequestMapping(value = "/api/inscription")
    public ModelAndView someMethod(String uname, String password) {
        ModelAndView modelAndView = new ModelAndView("inscription");

        for (User u : repository.findByUserName(uname)) {
            if (u.getUser().equals(uname)) {
                modelAndView.addObject("message", "L'utilisateur existe déja !");
                System.out.println(u.getUser() + " already exist");
                return modelAndView;
            }
        }
        modelAndView.addObject("message", "Utilisateur créé ! Vous pouvez vous connecter");
        repository.save(new User(uname, password, "vdsnf"));
        return modelAndView;
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