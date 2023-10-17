package com.learning.roomdatabasebasics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {


    //declaring itemViews
    EditText nameEdit;
    EditText ageEdit;

    Button saveButton, getDataButton;

    //Declaring our Database containing person table
    PersonDatabase personDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initializing out ItemViews
        nameEdit = findViewById(R.id.nameEdit);
        ageEdit = findViewById(R.id.ageEdit);
        saveButton = findViewById(R.id.saveButton);
        getDataButton = findViewById(R.id.getDataButton);

        //RoomDatabase.Callback is an abstract class provided by the Room Persistence Library in Android.
        // It is used for defining callback methods that can be executed during various lifecycle events of the Room database,
        // such as when the database is created, opened, or when it's populated with data.
        //Inside the curly braces, you define an anonymous inner class. This means you're creating a subclass of RoomDatabase.Callback on the fly and
        // providing implementations for its callback methods within the body of the anonymous inner class.
        RoomDatabase.Callback myCallBack = new RoomDatabase.Callback() {
            //This method is called when the database is created for the first time.
            @Override
            public void onCreate(@NonNull SupportSQLiteDatabase db) {
                super.onCreate(db);
            }

            //This method is called when the database is opened.
            @Override
            public void onOpen(@NonNull SupportSQLiteDatabase db) {
                super.onOpen(db);
            }
        };

        //creating an instance of a Room database in an Android application
        //.addCallback(myCallBack) this part of the line adds a callback to the database builder.
        //This allows you to perform custom actions at those points, as defined in your callback.
        // .build(): finalizes the creation of the Room database based on the provided configuration and returns an instance of the database.
        //The resulting database instance is stored in the personDB variable
        personDB = Room.databaseBuilder(getApplicationContext(), PersonDatabase.class, "PersonDB").addCallback(myCallBack).build();

        //setting an Onclick Listener to our save button
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get the value the user inputs in nameEdit EditText
                String name = nameEdit.getText().toString();
                //get the value the user inputs in age EditText
                String age = ageEdit.getText().toString();

                //When we click save button it creates a new person object,
                //the arguments are from the name and age variables which represents
                //data from corresponding EditText
                Person p1 = new Person(name, age);

                //The getPersonDAO is a method which returns an instance of the PersonDAO interface.
                //The addPerson method, as defined in the PersonDAO interface, is used to insert the p1 object into the database.
                // It is a method that performs an "insert" operation in the database table associated with the Person entity, adding a new record with the provided person's data
                personDB.getPersonDAO().addPerson(p1);

            }
        });



    }
}