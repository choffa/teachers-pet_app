package no.teacherspet.mainapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;

import backend.StudentInfo;

public class StudentRating extends AppCompatActivity {

    byte rating;
    int radioButtonID;
    RadioGroup tempo;
    TextView hello;
    HashMap<String,ArrayList<Integer>> savedLectures=new HashMap<>();
    StudentInfo stud=RoleSelect.getStud();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_rating);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tempo = (RadioGroup) findViewById(R.id.tempoRadioGroup);
        if(RoleSelect.saves.containsKey(LectureList.getID())){
            tempo.check((RoleSelect.saves.get(LectureList.getID())).get(2));
        }
        hello = (TextView) findViewById(R.id.textView2);
        tempo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup tempo, int checkedId) {
                ArrayList<Integer> changes=new ArrayList<Integer>();
                radioButtonID = tempo.getCheckedRadioButtonId();
                changes.add(radioButtonID);
                RoleSelect.getStud().setButton(tempo.getCheckedRadioButtonId());
                View radioButton = tempo.findViewById(radioButtonID);
                int rating = tempo.indexOfChild(radioButton) +1;
                changes.add(rating);
                if(RoleSelect.saves.containsKey(LectureList.getID())){
                    changes.add(RoleSelect.saves.get(LectureList.getID()).get(1));
                }
                else{
                    changes.add(0);
                }

                RoleSelect.changeStud((byte)rating);
                if(RoleSelect.saves.containsKey(LectureList.getID())){
                    RoleSelect.saves.remove(LectureList.getID());
                }
                RoleSelect.saves.put(LectureList.getID(),changes);
                hello.setText(Integer.toString(RoleSelect.getStud().getRank())+" , "+Integer.toString(RoleSelect.getStud().getOldRank()));
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), RoleSelect.class);
        startActivityForResult(myIntent, 0);
        finish();
        return true;

    }
}
