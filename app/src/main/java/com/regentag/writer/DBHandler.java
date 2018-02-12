package com.regentag.writer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by Katharina on 12.02.2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "writerInfos";
    // Stories table name
    private static final String TABLE_STORIES = "stories";
    // Stories Table Columns names
    private static final String STORIES_KEY_ID = "id";
    private static final String STORIES_KEY_INHALT = "inhalt";
    private static final String STORIES_KEY_TITLE = "title";
    private static final String STORIES_KEY_WORDCOUNT = "wordcount";

    //Projekt Table name
    private static final String TABLE_PROJEKT = "projekts";
    //Projekt table Columns names
    private static final String PROJEKT_KEY_ID = "id";
    private static final String PROJEKT_TITLE = "title";

    //Projekt Story Zuordnung Table name
    private static final String TABLE_PS = "projekts";
    //Projekt Story Zuordnung table Columns names
    private static final String PS_KEY_ID = "id";
    private static final String PS_PROJEKT = "projekt";
    private static final String PS_STORY = "story";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createStoriesTable(db);
        createProjektTable(db);
        createPSTable(db);
    }

    private void createStoriesTable(SQLiteDatabase db){
        String CREATE_STORIES_TABLE = "CREATE TABLE " + TABLE_STORIES + "("
                + STORIES_KEY_ID + " INTEGER PRIMARY KEY," + STORIES_KEY_TITLE + " TEXT,"
                + STORIES_KEY_INHALT + " TEXT," + STORIES_KEY_WORDCOUNT + " INTEGER)";
        db.execSQL(CREATE_STORIES_TABLE);
    }

    private void createProjektTable(SQLiteDatabase db){
        String CREATE_PROJEKT_TABLE = "CREATE TABLE " + TABLE_PROJEKT + "("
                + PROJEKT_KEY_ID + " INTEGER PRIMARY KEY," + PROJEKT_TITLE + " TEXT)";
        db.execSQL(CREATE_PROJEKT_TABLE);
    }

    private void createPSTable(SQLiteDatabase db){
        String CREATE_PS_TABLE = "CREATE TABLE " + TABLE_PS + "("
                + PS_KEY_ID + " INTEGER PRIMARY KEY," + PS_PROJEKT + " INTEGER PRIMARY KEY," + PS_STORY + " INTEGER PRIMARY KEY)";
        db.execSQL(CREATE_PS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJEKT);
        // Creating tables again
        onCreate(db);

    }

    //Adding new Story
    public void addStory(Story story){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STORIES_KEY_TITLE, story.getTitel());
        values.put(STORIES_KEY_INHALT, story.getInhalt());
        values.put(STORIES_KEY_WORDCOUNT, story.getWordcount());

        db.insert(TABLE_STORIES, null, values);
        db.close();

    }

    //Get one Story
    public Story getStory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STORIES, new String[]{STORIES_KEY_ID,
                        STORIES_KEY_TITLE, STORIES_KEY_INHALT, STORIES_KEY_WORDCOUNT}, STORIES_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Story contact = new Story(parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), parseInt(cursor.getString(3)));
        return contact;
    }

    // Get all Stories
    public List<Story> getAllStories() {
        List<Story> storyList = new ArrayList<Story>();
        // Select All Query
        String selectQuery = "SELECT * FROM " + TABLE_STORIES;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                Story story = new Story();
                story.setId(parseInt(cursor.getString(0)));
                story.setTitel(cursor.getString(1));
                story.setInhalt(cursor.getString(2));
                story.setWordcount(parseInt(cursor.getString(3)));
                // Adding contact to list
                storyList.add(story);
            } while (cursor.moveToNext());
        }
        // return contact list
        return storyList;
    }

    // Getting stories Count
    public int getStoriesCount() {
        String countQuery = "SELECT * FROM " + TABLE_STORIES;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

    // Updating a story
    public int updateStory(Story story) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(STORIES_KEY_TITLE, story.getTitel());
        values.put(STORIES_KEY_INHALT, story.getInhalt());
        values.put(STORIES_KEY_WORDCOUNT, story.getWordcount());
        // updating row
        return db.update(TABLE_STORIES, values, STORIES_KEY_ID + " = ?",
                new String[]{String.valueOf(story.getId())});
    }

    // Deleting a story
    public void deleteStory(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STORIES, STORIES_KEY_ID + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }


    public void addProjekt(Projekt projekt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROJEKT_KEY_ID, projekt.getId());
        values.put(PROJEKT_TITLE, projekt.getTitle());

        db.insert(TABLE_PROJEKT, null, values);
        db.close();
    }

    public Projekt getProjekt(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PROJEKT, new String[]{PROJEKT_KEY_ID, PROJEKT_TITLE}, PROJEKT_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Projekt projekt = new Projekt(parseInt(cursor.getString(0)), cursor.getString(1));
        return projekt;
    }

    public List<Projekt> getAllProjekts(){
        List<Projekt> projektList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PROJEKT;
        //TODO wirklich Writeable?
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                Projekt projekt = new Projekt();
                projekt.setId(parseInt(cursor.getString(0)));
                projekt.setTitle(cursor.getString(1));
                projektList.add(projekt);
            } while (cursor.moveToNext());
        }
        return projektList;
    }

    public int getProjektCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM "+TABLE_PROJEKT;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updateProjekt(Projekt projekt){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PROJEKT_KEY_ID, projekt.getId());
        values.put(PROJEKT_TITLE, projekt.getTitle());

        return db.update(TABLE_PROJEKT, values, PROJEKT_KEY_ID + " =?", new String[]{String.valueOf(projekt.getId())});
    }

    public void deleteProjekt(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PROJEKT, PROJEKT_KEY_ID + " =?", new String[]{String.valueOf(id)});
    }

    public void addPS(ProjektStories ps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PS_KEY_ID, ps.getId());
        values.put(PS_PROJEKT, ps.getProjekt_id());
        values.put(PS_STORY, ps.getStory_id());

        db.insert(TABLE_PS, null, values);
        db.close();
    }

    public ProjektStories getPS (int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_PS, new String[]{PS_KEY_ID, PS_PROJEKT, PS_STORY}, PS_KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        ProjektStories ps = new ProjektStories(parseInt(cursor.getString(0)), parseInt(cursor.getString(1)), parseInt(cursor.getString(2)));
        return ps;
    }

    public List<ProjektStories> getAllPS(){
        List<ProjektStories> psList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PS;
        //TODO wirklich Writeable?
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()){
            do {
                ProjektStories ps = new ProjektStories();
                ps.setId(parseInt(cursor.getString(0)));
                ps.setProjekt_id(parseInt(cursor.getString(1)));
                ps.setStory_id(parseInt(cursor.getString(2)));
                psList.add(ps);
            } while (cursor.moveToNext());
        }
        return psList;
    }

    public int getPSCount(){
        SQLiteDatabase db = this.getReadableDatabase();
        String countQuery = "SELECT * FROM "+TABLE_PS;
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

    public int updatePS(ProjektStories ps){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PS_KEY_ID, ps.getId());
        values.put(PS_PROJEKT, ps.getProjekt_id());
        values.put(PS_STORY, ps.getStory_id());

        return db.update(TABLE_PS, values, PS_KEY_ID + " =?", new String[]{String.valueOf(ps.getId())});
    }

    public void deletePS(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PS, PS_KEY_ID + " =?", new String[]{String.valueOf(id)});
    }

}
