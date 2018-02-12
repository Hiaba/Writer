package com.regentag.writer;

//TODO: abspeichern

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

public class WritingActivity extends AppCompatActivity {

    EditText userInput;
    TextView wordCounter;
    ProgressBar wordProgress;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_writing);
        init();


           /* DBHandler db = new DBHandler(this);

        // Inserting Stories/Rows
            Log.d("Insert: ", "Inserting ..");
            db.addStory(new Story(1, "neu1", "",0));
            db.addStory(new Story(2, "neu2", "",0));

        // Reading all stories
            Log.d("Reading: ", "Reading all shops..");
            List<Story> stories = db.getAllStories();

            for (Story story : stories) {
                String log = "Id: " + story.getId() + " ,Titel: " + story.getTitel() + ", Inhalt: "+story.getInhalt() + ", wordcount: "+story.getWordcount();
        // Writing stories to log
                Log.d("Story: : ", log);
            }*/
        }

    private void init() {
        userInput = (EditText) findViewById(R.id.userInput);
        wordCounter = (TextView) findViewById(R.id.wordCounterText);
        wordProgress = (ProgressBar) findViewById(R.id.wordProgressBar);

        wordCounter.setText("0");
        wordProgress.setMax(getResources().getInteger(R.integer.daily_value));

        userInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence text, int start, int before, int count) {
                int numberOfWords = wordcount(text.toString());
                wordCounter.setText(String.valueOf(numberOfWords));
                wordProgress.setProgress(numberOfWords);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    //TODO: wordcount überarbeiten
    private static int wordcount(String text){
        int numWords = 0;
        int index = 0;
        boolean prevWhiteSpace = true;
        while (index < text.length()) {
            char c = text.charAt(index++);
            boolean currWhiteSpace = Character.isWhitespace(c);
            if (prevWhiteSpace && !currWhiteSpace) {
                numWords++;
            }
            prevWhiteSpace = currWhiteSpace;
        }
        return numWords;
    }



}
