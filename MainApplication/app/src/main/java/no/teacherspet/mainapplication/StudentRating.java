package no.teacherspet.mainapplication;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;

import backend.Lecture;
import backend.StudentInfo;

public class StudentRating extends AppCompatActivity {

    byte rating;
    int radioButtonID;
    private static String lectureID;
    RadioGroup tempo;
    TextView hello; //Textfield only for debugging purposes: shows the last two values
    StudentInfo stud=RoleSelect.getStud(); //no longer necessary

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        lectureID= StudentLectureList.getID();
        setContentView(R.layout.activity_student_rating);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(StudentLectureList.getName());
        tempo = (RadioGroup) findViewById(R.id.tempoRadioGroup);
        if(RoleSelect.saves.containsKey(StudentLectureList.getID())){
            tempo.check(RoleSelect.saves.get(lectureID).get(0));
        }
        hello = (TextView) findViewById(R.id.textView2);
        //Sets the radiobuttons to send the new info to the server on every click.
        tempo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup tempo, int checkedId) {
                if(!isValidTime()){
                    Toast.makeText(getApplicationContext(),"Denne forelesningen er over",Toast.LENGTH_LONG).show();
                    finish();
                }
                else {
                    ArrayList<Integer> changes = new ArrayList<Integer>();
                    radioButtonID = tempo.getCheckedRadioButtonId();
                    changes.add(radioButtonID);
                    //RoleSelect.getStud().setButton(radioButtonID);
                    View radioButton = tempo.findViewById(radioButtonID);
                    int rating = tempo.indexOfChild(radioButton) + 1;
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
    }

    private boolean isValidTime(){
        Lecture parent = StudentLectureList.getL();
        Date now = new Date();
        Date lectureEnd = new Date(parent.getDate().getYear(), parent.getDate().getMonth(), parent.getDate().getDay(), parent.getEnd(), 30);
        if(now.after(lectureEnd)){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        /* Unnecessary as it creates a new instance of the RoleSelect page
        Intent myIntent = new Intent(getApplicationContext(), RoleSelect.class);
        startActivityForResult(myIntent, 0); */
        finish();
        return true;

    }
}
