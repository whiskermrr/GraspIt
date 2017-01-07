package com.wiktorwolski.mrr.mobile_programming_final_project;


import java.util.Date;

public class Challenge {


    private String title;
    private String description;
    private int iconId;
    private String deadline;
    private int idOfUser;
    boolean done;

    public Challenge() {

        this.title = "";
        this.description = "";
        this.iconId = -1;
        this.deadline = null;
        this.idOfUser = -1;
        this.done = false;
    }

    public Challenge(String title, String description, int iconId, String deadline, int idOfUser, boolean done) {

        this.title = title;
        this.description = description;
        this.iconId = iconId;
        this.idOfUser = idOfUser;
        this.done = done;
        this.deadline = deadline;
    }



    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public int getIdOfUser() {
        return idOfUser;
    }

    public void setIdOfUser(int idOfUser) {
        this.idOfUser = idOfUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIconId() {
        return iconId;
    }

    public void setIconId(int iconId) {
        this.iconId = iconId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }

}


