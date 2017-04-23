package no.teacherspet.mainapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;

import frontend.Connection;
import frontend.Subject;

/**
 * Created by eirik on 21.03.2017.
 */

public class LectureStatistics extends AppCompatActivity {
    ListView subject_list;
    ArrayList<Subject> subjectArray = new ArrayList<>();
    ArrayList<Float> subjectAvg = new ArrayList<>();
    Connection c;
    int lectureID;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lecture_statistics);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            lectureID = ProfessorLectureList.getID();
            c = new Connection();
            subjectArray = c.getSubjects(lectureID);
            for (Subject s: subjectArray) {
                subjectAvg.add(c.getAverageSubjectRating(s.getId()));
            }
            subject_list = (ListView) findViewById(R.id.subject_listview);
            StatisticsRowAdapter adapter = new StatisticsRowAdapter();
            subject_list.setAdapter(adapter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void onListItemClick(ListView parent, View v, int position, long id) {
        //TODO show the statistics for the chosen subject
    }

    /**
     * The adapter for the custom statistics rows.
     */
    public class StatisticsRowAdapter extends ArrayAdapter<Subject> {
        StatisticsRowAdapter() {
            super(LectureStatistics.this, R.layout.lecture_stat_row, subjectArray);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row=convertView;
            if(row==null) {
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.lecture_stat_row, parent, false);
            }
            TextView subjectName=(TextView)row.findViewById(R.id.label);
            subjectName.setText(subjectArray.get(position).getName());
            RatingBar ratingIndicator = (RatingBar) row.findViewById(R.id.sub_avg_ratingbar);
            ratingIndicator.setRating(subjectAvg.get(position));
            return(row);
        }
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
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
