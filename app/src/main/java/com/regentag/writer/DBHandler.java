package com.regentag.writer;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Katharina on 12.02.2018.
 */

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "writerInfos";
    // Contacts table name
    private static final String TABLE_STORIES = "stories";
    // Shops Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_INHALT = "inhalt";
    private static final String KEY_TITLE = "story_title";
    private static final String KEY_WORDCOUNT = "wordcount";


    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_STORIES + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_INHALT + " TEXT,"
        + KEY_TITLE + " TEXT," + KEY_WORDCOUNT + " INTEGER)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STORIES);
        // Creating tables again
        onCreate(db);

    }

    //Adding new Story
    public void addStory(Story story){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, story.getTitel());
        values.put(KEY_INHALT, story.getInhalt());
        values.put(KEY_WORDCOUNT, story.getWordcount());

        db.insert(TABLE_STORIES, null, values);
        db.close();

    }

    //Get one Story
    public Story getStory(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_STORIES, new String[]{KEY_ID,
                        KEY_TITLE, KEY_INHALT, KEY_WORDCOUNT}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Story contact = new Story(Integer.parseInt(cursor.getString(0)), cursor.getString(1), cursor.getString(2), Integer.parseInt(cursor.getString(3)));
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
                story.setId(Integer.parseInt(cursor.getString(0)));
                story.setTitel(cursor.getString(1));
                story.setInhalt(cursor.getString(2));
                story.setWordcount(Integer.parseInt(cursor.getString(3)));
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
        values.put(KEY_TITLE, story.getTitel());
        values.put(KEY_INHALT, story.getInhalt());
        values.put(KEY_WORDCOUNT, story.getWordcount());
        // updating row
        return db.update(TABLE_STORIES, values, KEY_ID + " = ?",
                new String[]{String.valueOf(story.getId())});
    }


    // Deleting a story
    public void deleteShop(Story story) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_STORIES, KEY_ID + " = ?",
                new String[] { String.valueOf(story.getId()) });
        db.close();
    }
}
