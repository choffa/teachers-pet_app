package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
    ArrayList<String> commentsArray = new ArrayList<>();
    Connection c;
    int lectureID;
    Button lectureCommentsBtn;
    TextView tempoTextview;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {

        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lecture_statistics);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(ProfessorLectureList.getName());
            lectureID = ProfessorLectureList.getID();
            c = new Connection();
            subjectArray = c.getSubjects(lectureID);
            for (Subject s: subjectArray) {
                subjectAvg.add(c.getAverageSubjectRating(s.getId()));
            }
            subject_list = (ListView) findViewById(R.id.subject_listview);
            StatisticsRowAdapter adapter = new StatisticsRowAdapter();
            subject_list.setAdapter(adapter);
            subject_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Subject curSub = subjectArray.get(position);
                    Intent toStatIntent = new Intent(LectureStatistics.this,StatisticsPopup.class);
                    toStatIntent.putExtra("subjectDistribution",getUpdatedSubjectRating(curSub.getId()));
                    toStatIntent.putExtra("SubjectName",curSub.getName());
                    startActivity(toStatIntent);
                }
            });
            String tempoString = ProfessorLive.getTempoText(c.getAverageSpeedRating(lectureID));
            tempoTextview = (TextView) findViewById(R.id.statistics_tempo_textview);
            tempoTextview.setText("Tempo of lecture:\n" + tempoString);
            if(subjectArray.size()==0){
                tempoTextview.setPadding(0,300,0,0);
            }
            commentsArray = c.getLectureComments(lectureID);
            lectureCommentsBtn = (Button) findViewById(R.id.lecturecomments_btn);
            lectureCommentsBtn.setText("Show Comments (" + commentsArray.size()+")");
            lectureCommentsBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent toCommentsIntent = new Intent(LectureStatistics.this,LectureCommentsPopup.class);
                    toCommentsIntent.putExtra("lectureComments",commentsArray);
                    toCommentsIntent.putExtra("lectureName",ProfessorLectureList.getName());
                    startActivity(toCommentsIntent);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    public int[] getUpdatedSubjectRating(int subjectID){
        return c.getSubjectStats(subjectID);
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
