package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by magnus on 17.02.2017.
 */


public class RoleSelect extends AppCompatActivity {
    //Page for redirecting to the students' GUI or the Professors' GUI. Might be changed for a login page.
    public static HashMap<Integer, ArrayList<Integer>> saves=new HashMap<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_role);
        Button StudBtn= (Button) findViewById(R.id.studBtn);
        Button ProfBtn= (Button) findViewById(R.id.profBtn);
        StudBtn.setOnClickListener(handler);
        ProfBtn.setOnClickListener(handler);
        //Allows threads
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /**
     * Sends to the ProfessorLive view
     */
    public void selectProfessor(){
        Intent intent= new Intent(RoleSelect.this,ProfessorLectureList.class);
        startActivity(intent);
    }

    /**
     * Sends to the StudentRating view
     */
    public void selectStudent(){

        Intent intent= new Intent(RoleSelect.this,StudentLectureList.class);
        startActivity(intent);
    }

    /**
     * Checks weither the user clicks the professor or the student button.
     */
    View.OnClickListener handler= new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.studBtn:

                    selectStudent();
                    break;
                case R.id.profBtn:

                    selectProfessor();
                    break;
            }
        }
    };




}