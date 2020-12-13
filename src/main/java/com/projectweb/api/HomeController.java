package com.projectweb.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;



@Controller
public class HomeController {


    private static final int EXPIRY_DAYS = 90;

    @Autowired
    private UserRepository repository;

    @GetMapping(value = "/api")
    public String index() {
        return "index";
    }

    @GetMapping(value = "/api/sondage")
    public String getSondage() {

        return "sondage";
    }

    @RequestMapping(value = "/api/login",  method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Message generateToken(@RequestBody Map<String, Object> payload) {
        String uname = payload.get("uname").toString();
        String password = payload.get("password").toString();

        System.out.println(uname + " uname");
        System.out.println(password + " password");
        SecurityContext securityContext;

        Authentication auth = new UsernamePasswordAuthenticationToken(uname, password);
        auth.isAuthenticated();
        SecurityContextHolder.getContext().setAuthentication(auth);
        System.out.println("auth" + auth);

        for (User u : repository.findByUserName(uname)) {

            if (u.getPassword().equals(password)) {
                System.out.println(u.getUser() + " connection");


                JSONObject jwtPayload = new JSONObject();
                jwtPayload.put("status", 0);

                JSONArray audArray = new JSONArray();
                audArray.put("admin");
                jwtPayload.put("sub", u.getUser());

                jwtPayload.put("aud", audArray);
                LocalDateTime ldt = LocalDateTime.now().plusDays(EXPIRY_DAYS);
                jwtPayload.put("exp", ldt.toEpochSecond(ZoneOffset.UTC));

                JWebToken token = new JWebToken(jwtPayload);
                u.setToken(token.toString());

                token.isValid();

                System.out.println(token);

                return new Message(u.getToken());
            }
        }
        return new Message("Not found");
    }

    /*@RequestMapping(value = "/api/login")
    public String connection(String uname, String password) {
        return "sondage";
    }*/


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
        repository.save(new User(uname, password));
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