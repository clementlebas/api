package com.projectweb.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.security.NoSuchAlgorithmException;
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
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


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
    public ModelAndView getSondage() throws NoSuchAlgorithmException {
        /*Query query = this.sessionFactory.getCurrentSession().createQuery("SELECT COUNT(*) FROM Users WHERE emailID = :email_ID OR mobileNo = :mobile_No");
        query.setString("email_ID", user.getEmailID());
        query.setString("mobile_No", user.getMobileNo());*/
        System.out.println("sondage");

        ModelAndView modelAndView = new ModelAndView("sondage");
        for (User u : repository.findByActiveTrue()) {
            JWebToken decodedToken = new JWebToken(u.getToken());

            System.out.println(u.toString() + " toString");
            modelAndView.addObject("welcome", "Bienvenue " + u.getUser());
        }





        return modelAndView;
    }

    @RequestMapping(value = "/api/login",  method = RequestMethod.POST)
    @ResponseBody
    public void generateTokenAndConnect(HttpServletResponse response, @RequestBody Map<String, Object> payload) throws IOException {
        String uname = payload.get("uname").toString();
        String password = payload.get("password").toString();

        System.out.println(uname + " uname");
        System.out.println(password + " password");
        SecurityContext securityContext;

        Authentication auth = new UsernamePasswordAuthenticationToken(uname, password);
        auth.isAuthenticated();
        SecurityContextHolder.getContext().setAuthentication(auth);

        for (User u : repository.findByUserName(uname)) {

            if (u.getPassword().equals(password)) {
                System.out.println(u.getUser() + " connection");
                User connectingUser = repository.findById(u.id).get();
                u.setActive(true);



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

                repository.save(connectingUser);


                JSONObject jsonResponse = new JSONObject();
                jsonResponse.put("token", token.toString());

                response.setContentType("application/json");
                PrintWriter out = response.getWriter();

                out.print(jsonResponse);

                out.flush();

            }

        }
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
        repository.save(new User(uname, password, false));
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