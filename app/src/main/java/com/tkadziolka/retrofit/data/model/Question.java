package com.tkadziolka.retrofit.data.model;

import java.io.Serializable;

public class Question implements Serializable {
    private int question_id;
    private String title;
    private String link;
    private Boolean is_answered;

    public Question(int question_id, String title, String link, Boolean is_answered) {
        this.question_id = question_id;
        this.title = title;
        this.link = link;
        this.is_answered = is_answered;
    }

    public String getTitle() {
        return title;
    }

    public int getQuestion_id() {
        return question_id;
    }

    public Boolean getIs_answered() {
        return is_answered;
    }
}
