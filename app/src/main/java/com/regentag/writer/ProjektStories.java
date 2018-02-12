package com.regentag.writer;

/**
 * Created by Katharina on 12.02.2018.
 */

public class ProjektStories {
    private int id;
    private int projekt_id;
    private int story_id;

    public ProjektStories() {
    }

    public ProjektStories(int id, int projekt_id, int story_id) {
        this.id = id;
        this.projekt_id = projekt_id;
        this.story_id = story_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProjekt_id() {
        return projekt_id;
    }

    public void setProjekt_id(int projekt_id) {
        this.projekt_id = projekt_id;
    }

    public int getStory_id() {
        return story_id;
    }

    public void setStory_id(int story_id) {
        this.story_id = story_id;
    }
}
