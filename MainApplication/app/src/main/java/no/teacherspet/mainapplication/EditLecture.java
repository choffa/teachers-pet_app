package no.teacherspet.mainapplication;


import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.IOException;

import frontend.Connection;

/**
 * Created by eirik on 21.03.2017.
 */

public class EditLecture extends AppCompatActivity {
    private Connection c;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_edit);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(ProfessorLectureList.getName());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
/*
    @Override
    public void onDestroy(){
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
*/
}
