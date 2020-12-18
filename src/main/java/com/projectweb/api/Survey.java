package com.projectweb.api;

import javax.persistence.*;
import java.util.ArrayList;

@Entity
public class Survey {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) long surveyid;
    @Column(name = "surveyname")
    private String surveyname;
    private String surveydate;
    private ArrayList<String> questions;
    private long userid;

    private Survey() {}

    public Survey(String surveyname, String surveydate, ArrayList<String> questions, long userid) {
        this.surveyname  = surveyname;
        this.surveydate = surveydate;
        this.questions = questions;
        this.userid = userid;
    }

    public String getSurveyname() {
        return surveyname;
    }

    public String getSurveydate() {
        return surveydate;
    }

    public Long getUserid() {
        return userid;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setSurveyname(String surveyname) {
        this.surveyname = surveyname;
    }

    public void setSurveydate(String surveydate) {
        this.surveydate = surveydate;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }
}
