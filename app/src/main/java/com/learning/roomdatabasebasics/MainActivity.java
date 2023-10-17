package com.learning.roomdatabasebasics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    //declaring itemViews
    EditText nameEdit;
    EditText ageEdit;

    Button saveButton, getDataButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing out ItemViews
        nameEdit = findViewById(R.id.nameEdit);
        ageEdit = findViewById(R.id.ageEdit);
        saveButton = findViewById(R.id.saveButton);
        getDataButton = findViewById(R.id.getDataButton);



    }
}