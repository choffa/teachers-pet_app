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
    private static boolean[] changes;
    private int originalSize;
    static int Position = -1;
    static String Name;
    static String Comment;
    ListView list_view;
    Connection c;
    Thread thread;


    protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lecture_edit);
            thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        c = new Connection();
                    }
                    catch (IOException e){
                        Toast.makeText(EditLecture.this, "Noe gikk galt under lasting av siden", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                }
            });
            thread.start();
            try {
                thread.join();
            }catch(InterruptedException e){
                e.printStackTrace();
            }
            subjectArray = c.getSubjects(ProfessorLectureList.getID());
            originalSize =subjectArray.size();
            changes = new boolean[originalSize+10];
            android.support.v7.app.ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            list_view = (ListView) findViewById(R.id.subject_listview);
            adapter = new ArrayAdapter<Subject>(this, android.R.layout.simple_list_item_1, subjectArray);
            list_view.setAdapter(adapter);

            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    Subject SI= (Subject) list_view.getItemAtPosition(position);
                    Position = position;
                    Name = SI.getName();
                    Comment = SI.getComment();
                    AddSubject.setID(Position);
                    startOnClickMethod();


                }
            });
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
        EditLecture.Position = position;
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

    /**
     * Starts up the AddSubject Activity.
     */
    public void startOnClickMethod(){
        Intent myIntent=new Intent(EditLecture.this,AddSubject.class);
        myIntent.putExtra("origin", "EditLecture");
        EditLecture.this.startActivity(myIntent);
    }


    /**
     * Handles the "Add New Subject" button.
     * @param view The button-view.
     */
    public void addNewSubjectClick(View view) {
        setPosition(-1);
        setName(null);
        setComment(null);
        startOnClickMethod();
    }

    /**
     * Creates new subject or edits existing one locally with the static Name, Comment and Position fields.
     */
    public static void addToSubjectArray(){
        if(Position ==-1){
            subjectArray.add(new Subject(Name,Comment));
            int changesSize = subjectArray.size();
            if(changesSize==changes.length){

                boolean [] changesTemp = new boolean[changesSize+5];
                System.arraycopy(changes,0,changesTemp,0,changesSize-1);
                changes =changesTemp;
            }
            changes[changesSize-1] = true;
        }else{
            Subject curSub= subjectArray.get(Position);
            curSub.setName(Name);
            curSub.setComment(Comment);
            changes[Position]=true;
        }
    }

    /**
     * Handles the "Finish" button press. Updates all updates subjects, and creates all new subjects.
     * @param view the button view
     */
    public void finishedClick(View view) {
        int counter=0;
        for (Subject s:subjectArray) {
            if(changes[counter]&&counter<originalSize){
                c.updateSubject(s.getId(),s.getName(),s.getComment());
            }else if(changes[counter]){
                c.createSubject(ProfessorLectureList.getID(),s.getName(),s.getComment());
            }
            counter++;
        }
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