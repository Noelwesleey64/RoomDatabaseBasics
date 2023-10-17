package com.learning.roomdatabasebasics;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;


//annotated interface or abstract class that contains methods for performing database operations, such as
// inserting, updating, deleting, and querying data.
@Dao
public interface PersonDAO {

    //declare a method that inserts data into the database. In this case,
    // it's used to insert a "Person" object into the database.
    @Insert
    public void addPerson(Person person);

    //declare a method for updating existing data in the database.
    // The method updatePerson is used to update a "Person" record in the database.
    @Update
    public void updatePerson(Person person);

    //this annotation is used to declare a method for deleting data from the database.
    //The method deletePerson is used to delete a "Person" record from the database.
    @Delete
    public void deletePerson(Person person);

    //it selects all records from a table named "person."
    @Query("select * from person")
    // The getAllPerson method is used to execute this query and return
    // a list of "Person" objects.
    public List<Person>  getAllPerson();

    //retrieves a specific "Person" record from the database based on a parameter.
    @Query("select * from person where person_id==:person_id")
    public Person getPerson(int person_id);
}
