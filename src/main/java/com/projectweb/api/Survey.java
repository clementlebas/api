package com.projectweb.api;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class Survey {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO) long id;
    @Column(name = "surveyname")
    private String surveyname;
    private String question;
    private String choice;
    private String choiceType;
}
