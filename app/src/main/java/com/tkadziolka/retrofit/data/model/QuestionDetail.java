package com.tkadziolka.retrofit.data.model;

public class QuestionDetail {
    private int question_id;
    private String title;
    private String body;
    private Owner owner;

    public QuestionDetail(int question_id, String title, String body, Owner owner) {
        this.question_id = question_id;
        this.title = title;
        this.body = body;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public Owner getOwner() {
        return owner;
    }
}
