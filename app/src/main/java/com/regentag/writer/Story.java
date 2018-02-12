package com.regentag.writer;

/**
 * Created by Katharina on 12.02.2018.
 *
 * Interne Darstellung der eingegebenen Texte
 */

public class Story {

    private int id;
    private int wordcount;
    private String inhalt;
    private String titel;

    public Story()
    {   }

    public Story(int id, String inhalt, String titel, int wordcount) {
        this.id = id;
        this.wordcount = wordcount;
        this.inhalt = inhalt;
        this.titel = titel;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getWordcount() {
        return wordcount;
    }

    public void setWordcount(int wordcount) {
        this.wordcount = wordcount;
    }

    public String getInhalt() {
        return inhalt;
    }

    public void setInhalt(String inhalt) {
        this.inhalt = inhalt;
    }

    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
