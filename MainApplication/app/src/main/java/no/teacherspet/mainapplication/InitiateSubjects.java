package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import frontend.Subject;

/**
 * Created by eirik on 31.03.2017.
 */

public class InitiateSubjects extends AppCompatActivity{
    public static ArrayList<Subject> subjectArray = new ArrayList<>();
    public static ArrayAdapter<Subject> adapter;
    static int Position;
    static String Name;
    static String Comment;
    ListView list_view;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.initiate_subjects);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        list_view = (ListView) findViewById(R.id.subject_listview);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, subjectArray);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject SI= (Subject) list_view.getItemAtPosition(position);
                Position = position;
                Name = SI.getName();
                Comment = SI.getComment();
                startOnClickMethod();
            }
        });


    }

    /**
     * Change to AddSubject view with origin put as InitiateSubjects
     */
    public void startOnClickMethod(){
        Intent myIntent=new Intent(InitiateSubjects.this,AddSubject.class);
        myIntent.putExtra("origin", "InitiateSubjects");
        startActivity(myIntent);

    }

    /**
     * Handles the Add New Subject button
     * Resets the static fields before starting startOnClickMethod.
     * @param view
     */
    public void addNewSubjectClick(View view) {
        setPosition(-1);
        setName(null);
        setComment(null);
        startOnClickMethod();
    }

    /**
     * Adds new subject to the array, or edits an existing one, depending on action taken. Called from AddSubject.
     */
    public static void addToSubjectArray(){
        if(Position ==-1){
            subjectArray.add(new Subject(Name,Comment));
        }else{
            Subject curSub= subjectArray.get(Position);
            curSub.setName(Name);
            curSub.setComment(Comment);
        }
    }

    public void finishedClick(View view) {
        CreateLecture.setSubjectsArray(subjectArray);
        finish();
    }

    public static int getPosition() {
        return Position;
    }

    public static String getName() {
        return Name;
    }

    public static String getComment() {
        return Comment;
    }

    public static void setPosition(int position) {
        InitiateSubjects.Position = position;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static void setComment(String comment) {
        Comment = comment;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

}