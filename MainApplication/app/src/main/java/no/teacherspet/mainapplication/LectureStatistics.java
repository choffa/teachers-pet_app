package no.teacherspet.mainapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

/**
 * Created by eirik on 21.03.2017.
 */

public class LectureStatistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_statistics);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(ProfessorLectureList.getName());
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }
}
