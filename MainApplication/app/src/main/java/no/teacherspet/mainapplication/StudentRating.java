package no.teacherspet.mainapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import backend.Lecture;
import frontend.Connection;

public class StudentRating extends AppCompatActivity {

    byte rating;
    int radioButtonID;
    private static int lectureID;
    RadioGroup tempo;
    private Connection c;
    HashMap<String,ArrayList<Integer>> savedLectures=new HashMap<>();
    TextView hello; //Textfield only for debugging purposes: shows the last two values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            lectureID = StudentLectureList.getID();
            c = new Connection();
            setContentView(R.layout.activity_student_rating);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(StudentLectureList.getName());
            tempo = (RadioGroup) findViewById(R.id.tempoRadioGroup);
            if (RoleSelect.saves.containsKey(StudentLectureList.getID())) {
                tempo.check(RoleSelect.saves.get(lectureID).get(0));
            }
            hello = (TextView) findViewById(R.id.textView2);
            //Sets the radiobuttons to send the new info to the server on every click.
            tempo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(RadioGroup tempo, int checkedId) {
                if(!isValidTime()){
                    Toast.makeText(getApplicationContext(),"This lecture is not active",Toast.LENGTH_LONG).show();
                    finish();

                    } else {
                        ArrayList<Integer> changes = new ArrayList<Integer>();
                        radioButtonID = tempo.getCheckedRadioButtonId();
                        changes.add(radioButtonID);
                        //RoleSelect.getStud().setButton(radioButtonID);
                        View radioButton = tempo.findViewById(radioButtonID);
                        int rating = tempo.indexOfChild(radioButton) + 1;
                        c.sendSpeedRating(StudentLectureList.getID(),RoleSelect.StudentId,rating);
                        changes.add(rating);
                        if (RoleSelect.saves.containsKey(StudentLectureList.getID())) {
                            changes.add(RoleSelect.saves.get(StudentLectureList.getID()).get(1));
                        } else {
                            changes.add(0);
                        }

                        //RoleSelect.changeStud((byte)rating);
                        if (RoleSelect.saves.containsKey(StudentLectureList.getID())) {
                            RoleSelect.saves.remove(StudentLectureList.getID());
                        }
                        RoleSelect.saves.put(lectureID, changes);
                        hello.setText(Integer.toString(RoleSelect.saves.get(lectureID).get(1)) + " , " + Integer.toString(RoleSelect.saves.get(lectureID).get(2)));
                    }
                }
            });
        }catch(IOException e){
            Toast.makeText(getApplicationContext(),"Noe gikk galt under lasting av siden",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private boolean isValidTime(){
            Lecture lecture = StudentLectureList.getL();
            Date lectureDate = lecture.getDate();
            int start = lecture.getStart();
            int end = lecture.getEnd();

            Date now = new Date();
            if(lectureDate.getYear()<now.getYear()||lectureDate.getYear()>now.getYear()){
                return false;
            }else{
                if(lectureDate.getMonth()<now.getMonth()||lectureDate.getMonth()>now.getMonth()){
                    return false;
                }else{
                    if(lectureDate.getDate()<now.getDate()||lectureDate.getDate()>now.getDate()){
                        return false;
                    }else{
                        if((end==now.getHours()&&now.getMinutes()>30)||end<now.getHours()||start>now.getHours()){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }
            }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /* Unnecessary as it creates a new instance of the RoleSelect page
        Intent myIntent = new Intent(getApplicationContext(), RoleSelect.class);
        startActivityForResult(myIntent, 0); */
        try {
            c.close();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }
}
