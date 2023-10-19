 package com.learning.roomdatabasebasics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {


    //declaring itemViews
    EditText nameEdit;
    EditText ageEdit;

    Button saveButton, getDataButton;

    //Declaring our Database containing person table
    PersonDatabase personDB;

    List<Person> PersonList;

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

                addPersonInBackground(p1);



            }
        });

        //displaying data after clicking get data
        getDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                 getPersonListInBackground();


            }
        });





    }

    //This function is responsible for adding a Person object to the database in a background thread to avoid blocking the main UI thread
    public void addPersonInBackground(Person person){
        //create a single-threaded ExecutorService to manage background tasks.
        //It creates an ExecutorService with a single background thread, ensuring that database operations do not block the main UI thread.
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // is creating a Handler object associated with the main (UI) thread's Looper.
        //Looper is a class in Android that manages message processing within a thread.
        //Looper.getMainLooper() retrieves the Looper for the main thread.
        //A Handler is a class that is used to communicate between background threads and the main (UI) thread in Android.
        // It allows you to post Runnable objects for execution on the main thread.


        Handler handler = new Handler(Looper.getMainLooper());

        //he Handler is used to post a Runnable on the main thread once the background database operation is complete.
       //This code starts the execution of a background task using an ExecutorService.
        executorService.execute(new Runnable() {
            //his method contains the code that will be executed in the background thread.
            @Override
            public void run() {
                //background task
                //The getPersonDAO is a method which returns an instance of the PersonDAO interface.
                //The addPerson method, as defined in the PersonDAO interface, is used to insert the p1 object into the database.
                // It is a method that performs an "insert" operation in the database table associated with the Person entity, adding a new record with the provided person's data

                personDB.getPersonDAO().addPerson(person);

                //on finishing task
                //After the database operation is completed, this code posts a new Runnable to a handler.
                //he handler is typically used to perform actions on the main (UI) thread from a background thread.
               //In this case, it is used to show a toast message.
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Added to Database", Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
// this code asynchronously retrieves a list of Person records from a database in the background,
// formats this data into a readable string,
// and then displays it as a toast message on the main UI thread.
    public void getPersonListInBackground(){
        //It creates an ExecutorService with a single thread. This service is responsible for executing tasks in the background.
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        //It creates a Handler associated with the main (UI) thread.
        Handler handler = new Handler(Looper.getMainLooper());

        //It submits a background task to the ExecutorService
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                //background task
                //Get all the records for person object from the database and store it on a list
                PersonList = personDB.getPersonDAO().getAllPerson();

                //on finishing task
                //After finishing the database operation, it posts a new task on the main thread
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //StringBuilder is used for efficiently building strings, especially when you need to concatenate multiple strings together.
                        StringBuilder sb = new StringBuilder();

                        //this loop iterates over each person object in the PersonList
                        for(Person p : PersonList){

                            //Inside the loop, the code appends the name and age of each Person to the StringBuilder sb.
                            sb.append(p.getName()+": " + p.getAge());

                            //After each name and age entry, a newline character (\n) is appended to sb to separate each person's information on a new line.
                            sb.append("\n");



                        }

                        //After the loop is finished, the StringBuilder content is converted to a single String, which is stored in the finalData variable.
                        String finalData = sb.toString();
                        //We create a toast message displaying th string
                        Toast.makeText(MainActivity.this, ""+finalData, Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });
    }
}