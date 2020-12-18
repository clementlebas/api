package com.projectweb.api;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;


@Controller
public class HomeController {

    private static final int EXPIRY_DAYS = 90;

    @Autowired
    private UserRepository repository;

    @Autowired
    private Surveyrepository surveyrepository;

    @GetMapping(value = "/api")
    public String index() {
        User connectingUser = repository.findByActiveTrue();
        System.out.println("connectingUser" + connectingUser);
        if (connectingUser != null) {
            connectingUser.setActive(false);
            connectingUser.setToken("");
            repository.save(connectingUser);

        }
        return "index";
    }

    @GetMapping(value = "/api/survey")
    public ModelAndView getSurvey() throws NoSuchAlgorithmException {
        ModelAndView modelAndView;
        User u = repository.findByActiveTrue();

        JWebToken encodedToken = new JWebToken(u.getToken());
        User connectingUser = repository.findById(u.id).get();

        if (encodedToken.isValid()) {
            modelAndView = new ModelAndView("survey");
            modelAndView.addObject("welcome", "Bienvenue " + encodedToken.getSubject());
        } else {
            modelAndView = new ModelAndView("invalidtoken");
            modelAndView.addObject("invalidtoken", "Session expiré ou token invalide");
            u.setActive(false);
            u.setToken("");
            repository.save(connectingUser);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/api/survey/create", method = RequestMethod.POST, consumes="application/json")
    @ResponseBody
    public ModelAndView getCreatedSurvey(@RequestHeader("authorization") String token, @RequestBody Map<String, Object> surveyJSON) throws NoSuchAlgorithmException {
        ModelAndView modelAndView = new ModelAndView("survey");
        JWebToken encodedToken = new JWebToken(token);
        User connectingUser = repository.findByActiveTrue();

        // control token
        if (encodedToken.isValid() && connectingUser.getToken().equals(token)) {

            //survey decomposition
            Map<String, Object> survey = (Map<String, Object>)surveyJSON.get("survey");
            String surveyName = survey.get("name").toString();
            String surveyDate = survey.get("date").toString();

            Map<String, Object> surveyQuestions= (Map<String, Object>)survey.get("questions");
            System.out.println(surveyQuestions + " surveyQuestions");

            ArrayList<String> surveyQuestionsArray = new ArrayList<>();

            for (Map.Entry mapentry  : surveyQuestions.entrySet()) {
                surveyQuestionsArray.add(mapentry.getValue().toString());
            }

            // save new survey
            surveyrepository.save(new Survey(surveyName, surveyDate, surveyQuestionsArray,connectingUser.id));



        } else {
            connectingUser.setActive(false);
            connectingUser.setToken("");
            repository.save(connectingUser);
        }
        return modelAndView;
    }

    @RequestMapping(value = "/api/survey/mysurvey", method = RequestMethod.GET)
    @ResponseBody
    public void getUserSurvey(@RequestHeader("authorization") String token, HttpServletResponse response) throws NoSuchAlgorithmException, IOException {
        JWebToken encodedToken = new JWebToken(token);
        User connectingUser = repository.findByActiveTrue();

        // control token
        if (encodedToken.isValid() && connectingUser.getToken().equals(token)) {
            JSONObject jsonResponseWrapper = new JSONObject();

            for (Survey s : surveyrepository.findByUserid(connectingUser.id)) {
                JSONObject jsonResponse = new JSONObject();

                JSONArray questions = new JSONArray(s.getQuestions());

                jsonResponse.put("date", s.getSurveydate());
                jsonResponse.put("name", s.getSurveyname());
                jsonResponse.put("questions", questions.toString());
                System.out.println(s.getSurveyname() + " findByUserid");
                System.out.println(jsonResponse + " jsonResponse");

                jsonResponseWrapper.accumulate("survey", jsonResponse);
            }

            System.out.println(jsonResponseWrapper + " jsonResponseWrapper");
            response.setContentType("application/json");
            PrintWriter out = response.getWriter();

            out.print(jsonResponseWrapper);

            out.flush();
        } else {
            connectingUser.setActive(false);
            connectingUser.setToken("");
            repository.save(connectingUser);
        }
    }


    @RequestMapping(value = "/api/login",  method = RequestMethod.POST)
    @ResponseBody
    public void generateTokenAndConnect(HttpServletResponse response, @RequestBody Map<String, Object> payload) throws IOException {
        String uname = payload.get("uname").toString();
        String password = payload.get("password").toString();

        User alreadyConnectedUser = repository.findByActiveTrue();
        System.out.println("alreadyConnectedUser" + alreadyConnectedUser);
        if (alreadyConnectedUser != null) {
            alreadyConnectedUser.setActive(false);
            alreadyConnectedUser.setToken("");
            repository.save(alreadyConnectedUser);
        }


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

    @GetMapping(value = "/api/disconnect")
    public String disconnect() {
        User connectingUser = repository.findByActiveTrue();
        System.out.println("connectingUser" + connectingUser);
        if (connectingUser != null) {
            connectingUser.setActive(false);
            connectingUser.setToken("");
            repository.save(connectingUser);
        }

        System.out.println("repository.findByActiveTrue()" + repository.findByActiveTrue());
        return "index";
    }
}