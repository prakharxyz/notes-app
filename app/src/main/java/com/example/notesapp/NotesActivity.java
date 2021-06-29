package com.example.notesapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import java.util.HashSet;

public class NotesActivity extends AppCompatActivity {
    int noteIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        EditText editText = (EditText) findViewById(R.id.editText);
        //get noteIndex from MainActivity
        Intent intent = getIntent();
        noteIndex = intent.getIntExtra("noteIndex", -1);
        if (noteIndex != -1) {
            editText.setText(MainActivity.notesArray.get(noteIndex));
        }
        else {
            MainActivity.notesArray.add("");
            noteIndex = MainActivity.notesArray.size() - 1;
        }
        //to detect/listen change in editText
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //in static arrayList notesArray update noteIndex with charSequence & notify adapter about about the change
                MainActivity.notesArray.set(noteIndex, String.valueOf(s));
                MainActivity.arrayAdapter.notifyDataSetChanged();
                //create object of SP & store notesArray in hashSet & then save hashSet in SP
                SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("com.example.notesapp", Context.MODE_PRIVATE);
                HashSet<String> set = new HashSet<String>(MainActivity.notesArray);
                sharedPreferences.edit().putStringSet("notes", set).apply();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}