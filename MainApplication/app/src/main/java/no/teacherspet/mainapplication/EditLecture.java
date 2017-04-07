package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import frontend.Connection;
import frontend.Subject;

/**
 * Created by eirik on 31.03.2017.
 */

public class EditLecture extends AppCompatActivity{
    public static ArrayList<Subject> subjectArray = new ArrayList<>();
    public static ArrayAdapter<Subject> adapter;
    static int ID = -1;
    static String Name;
    static String Comment;
    ListView list_view;
    Connection c;


    protected void onCreate(Bundle savedInstanceState) {
        try{
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lecture_edit);
            c = new Connection();
            subjectArray = c.getSubjects(ProfessorLectureList.getID());
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            list_view = (ListView) findViewById(R.id.subject_listview);
            adapter = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, subjectArray);
            list_view.setAdapter(adapter);

            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Subject SI= (Subject) list_view.getItemAtPosition(position);
                    ID = position;
                    Name = SI.getName();
                    Comment = SI.getComment();
                    AddSubject.setID(ID);
                    startOnClickMethod();


                }
            });
        } catch (IOException e) {
            Toast.makeText(getApplicationContext(), "Error occured while loading page", Toast.LENGTH_LONG).show();
            finish();
        }

    }

    public static int getID() {
        return ID;
    }

    public static String getName() {
        return Name;
    }

    public static String getComment() {
        return Comment;
    }

    public static void setID(int ID) {
        EditLecture.ID = ID;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static void setComment(String comment) {
        Comment = comment;
    }

    public void addItems(View v){
        //  listItems.add(Name);
        subjectArray.add(new Subject(Name,Comment));
        adapter.notifyDataSetChanged();
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;


    }

    public void startOnClickMethod(){
        Intent myIntent=new Intent(EditLecture.this,AddSubject.class);
        myIntent.putExtra("origin", "EditLecture");
        EditLecture.this.startActivity(myIntent);

    }



    public void addNewSubjectClick(View view) {
        setID(-1);
        setName(null);
        setComment(null);
        startOnClickMethod();
    }

    public static void addToSubjectArray(){
        if(ID ==-1){
            subjectArray.add(new Subject(Name,Comment));
            // int lastIndex = subjectArray.size()-1;
            // subjectArray.get(lastIndex).setLocalID(lastIndex);
        }else{
            Subject curSub= subjectArray.get(ID);
            curSub.setName(Name);
            curSub.setComment(Comment);
        }
        //adapter.notifyDataSetChanged();
    }

    public void finishedClick(View view) {
        CreateLecture.setSubjectsArray(subjectArray);
        finish();
    }

    @Override
    public void onDestroy(){
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}