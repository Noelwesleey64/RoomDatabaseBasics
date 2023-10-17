package com.learning.roomdatabasebasics;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

        //setting an Onclick Listener to our save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the value the user inputs in nameEdit EditText
                String name = nameEdit.getText().toString();
                //get the value the user inputs in age EditText
                String age = ageEdit.getText().toString();


            }
        });



    }
}