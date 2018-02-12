package com.regentag.writer;

/**
 * Created by Katharina on 12.02.2018.
 */

public class Projekt {

    private int id;
    private String title;

    public Projekt() {
    }

    public Projekt(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
