package no.teacherspet.mainapplication;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import backend.StudentInfo;

public class StudentRating extends AppCompatActivity {

    byte rating;
    int radioButtonID;
    RadioGroup tempo;
    TextView hello; //Textfield only for debugging purposes: shows the last two values
    StudentInfo stud=RoleSelect.getStud();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_rating);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        tempo = (RadioGroup) findViewById(R.id.tempoRadioGroup);
        if(RoleSelect.getStud().getID()!=0){
            tempo.check(RoleSelect.getStud().getID());
        }
        hello = (TextView) findViewById(R.id.textView2);
        //Sets the radiobuttons to send the new info to the server on every click.
        tempo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(RadioGroup tempo, int checkedId) {
                radioButtonID = tempo.getCheckedRadioButtonId();
                RoleSelect.getStud().setID(tempo.getCheckedRadioButtonId());
                View radioButton = tempo.findViewById(radioButtonID);
                int rating = tempo.indexOfChild(radioButton) +1;
                RoleSelect.changeStud((byte)rating);
                hello.setText(Integer.toString(RoleSelect.getStud().getRank())+" , "+Integer.toString(RoleSelect.getStud().getOldRank()));
            }
        });
    }
    public boolean onOptionsItemSelected(MenuItem item){
        /* Unnecessary as it creates a new instance of the RoleSelect page
        Intent myIntent = new Intent(getApplicationContext(), RoleSelect.class);
        startActivityForResult(myIntent, 0); */
        finish();
        return true;

    }
}
