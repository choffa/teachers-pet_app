package no.teacherspet.mainapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import frontend.Connection;
import frontend.Subject;


public class CreateLecture extends AppCompatActivity {

    TextView lectName;
    Thread thread;
    TextView roomName;
    EditText lecture;
    EditText room;
    static Button startTime;
    static Button endTime;
    private static ArrayList<Subject> subjectsArray = new ArrayList<>();
    Button done;
    Button cancel;
    static Button dateBtn;
    private static Date date;
    public static Date getDate() {
        return date;
    }
    private static int start=-1;
    private static int end=-1;
    private static boolean isStart;
    private Connection c;
    public static boolean getIsStart(){
        return isStart;
    }
    public static void setStart(int i){
        start=i;
    }
    public static void setEnd(int i){
        end=i;
    }
    public static void setDate(Date date){
        CreateLecture.date=date;
    }


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lecture);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = new Connection();
                }
                catch (IOException e){
                    Toast.makeText(CreateLecture.this, "Noe gikk galt under lasting av siden", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lectName = (TextView) findViewById(R.id.lecture_header);
        roomName = (TextView) findViewById(R.id.room_header);
        lecture = (EditText) findViewById(R.id.nametxt);
        room = (EditText) findViewById(R.id.roomtxt);
        startTime = (Button) findViewById(R.id.startbtn);
        endTime = (Button) findViewById(R.id.endbtn);
        done = (Button) findViewById(R.id.donebtn);
        cancel = (Button) findViewById(R.id.cancelbtn);
        dateBtn = (Button) findViewById(R.id.datebtn);
        startTime.setOnClickListener(handler);
        endTime.setOnClickListener(handler);
        done.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
        dateBtn.setOnClickListener(handler);
        start = -1;
        end = -1;
    }


    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        /**
         * Handles the different buttons and initiates the different activity methods
         */
        public void onClick(View v) {
            switch(v.getId()){

                case(R.id.datebtn):
                    dateSelect();
                    break;

                case(R.id.startbtn):
                    isStart = true;
                    timeSelect();

                    break;

                case(R.id.endbtn):
                    isStart=false;
                    timeSelect();

                    break;
                case(R.id.donebtn):
                    if(lecture==null || room==null || start==-1 || end==-1 || date == null || lecture.getText().length()<1 || room.getText().length()<1){
                        Toast.makeText(getApplicationContext(), "Fields lack input",Toast.LENGTH_SHORT).show();
                    }
                    else {
                        int lectureID = c.createLecture(RoleSelect.ProfessorID,lecture.getText().toString().trim(),date, start, end, room.getText().toString().trim());
                        Toast.makeText(CreateLecture.this, "Lecture created", Toast.LENGTH_SHORT).show();
                        int counter=0;
                        for (Subject s : subjectsArray) {
                            c.createSubject(lectureID,s.getName(),s.getComment());
                            counter++;
                        }
                        Toast.makeText(CreateLecture.this, counter+" subjects added", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    break;
                case(R.id.cancelbtn):
                    finish();
                    break;
            }
        }
    };

    /**
     * Updates the text on the button after setting a new value
     * @param buttonID the String representation of the button Position.
     * @param text the new info for the button
     */
    @SuppressLint("SetTextI18n")
    public static void setButtonText(String buttonID, String text){
        switch (buttonID){
            case "dateBtn":
                dateBtn.setText("Date of lecture: " + text);
            break;
            case "startTime":
                startTime.setText("Start Time: " + text);
            break;
            case "endTime":
                endTime.setText("End Time: " + text);
            break;
        }
    }

    public static int getStart() {
        return start;
    }

    public static int getEnd() {
        return end;
    }

    @SuppressLint("SetTextI18n")
  
    /**
     * sets the activity to be the timesetter.
     */
    private void timeSelect() {
        Intent getClock=new Intent(getApplicationContext(),TimeSetter.class);
        startActivity(getClock);
        if(start!=-1){
            startTime.setText("Start Time: " + start);
        }
        if(end!=-1){
            endTime.setText("End Time: " + end);
        }
    }

    /**
     * Sets the activity to be the datesetter
     */
    private void dateSelect() {
        Intent getCalendar=new Intent(getApplicationContext(),DateSetter.class);
        startActivity(getCalendar);

    }
    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        InitiateSubjects.subjectArray = new ArrayList<>();
        return true;
    }

    public static ArrayList<Subject> getSubjectsArray() {
        return subjectsArray;
    }

    public static void setSubjectsArray(ArrayList<Subject> subjectsArray) {
        CreateLecture.subjectsArray = subjectsArray;
    }

    /**
     * Handles the Add Subject button.
     * @param view
     */
    public void addSubjectClick(View view) {
        Intent intent = new Intent(CreateLecture.this, InitiateSubjects.class);
        startActivity(intent);
    }

    @Override
    public void onDestroy(){
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    c.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        InitiateSubjects.subjectArray.clear();
        date = null;
        start = -1;
        end = -1;
        super.onDestroy();
    }
}