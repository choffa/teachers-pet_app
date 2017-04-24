package no.teacherspet.mainapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import frontend.Connection;
import frontend.Subject;
import no.teacherspet.mainapplication.LectureCommentsPopup;
import no.teacherspet.mainapplication.LectureStatistics;
import no.teacherspet.mainapplication.ProfessorLectureList;
import no.teacherspet.mainapplication.ProfessorLive;
import no.teacherspet.mainapplication.R;
import no.teacherspet.mainapplication.StatisticsPopup;

/**
 * Created by eirik on 23.04.2017.
 */

public class LectureStatisticsFragment extends Fragment {

    private int lectureID;
    private ArrayList<Subject> subjectArray;
    ArrayList<Float> subjectAvg = new ArrayList<>();
    Connection c;
    private ListView subject_list;
    LayoutInflater inflater;
    private ArrayList<String> commentsArray;
    private Button lectureCommentsBtn;
    private StatisticsRowAdapter adapter;
    private Handler mHandler;
    private int mInterval = 2000;
    TextView tempoTextview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lecture_statistics,container,false);
        this.inflater = inflater;
        lectureID = ProfessorLectureList.getID();
        c=ProfessorLive.c;
        subjectArray = c.getSubjects(lectureID);
        for (Subject s: subjectArray) {
            s.setAverageRating(c.getAverageSubjectRating(s.getId()));
        }
        subject_list = (ListView) rootView.findViewById(R.id.subject_listview);
        adapter = new StatisticsRowAdapter(getActivity(),subjectArray);
        subject_list.setAdapter(adapter);
        subject_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Subject curSub = subjectArray.get(position);
                Intent myIntent = new Intent(getActivity(),StatisticsPopup.class);
                myIntent.putExtra("subjectDistribution",ProfessorLive.getUpdatedSubjectRating(curSub.getId()));
                myIntent.putExtra("SubjectName",curSub.getName());
                startActivity(myIntent);
            }
        });
        commentsArray = c.getLectureComments(lectureID);
        lectureCommentsBtn = (Button) rootView.findViewById(R.id.lecturecomments_btn);
        lectureCommentsBtn.setText("Show Comments (" + commentsArray.size()+")");
        lectureCommentsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCommentsIntent = new Intent(getActivity(),LectureCommentsPopup.class);
                toCommentsIntent.putExtra("lectureComments",commentsArray);
                toCommentsIntent.putExtra("lectureName",ProfessorLectureList.getName());
                startActivity(toCommentsIntent);
            }
        });
        tempoTextview = (TextView) rootView.findViewById(R.id.statistics_tempo_textview);
        tempoTextview.setVisibility(View.GONE);
        mHandler = new Handler();
        startRepeatingTask();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        stopRepeatingTask();
        super.onDestroyView();
    }

    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                update();
            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    void startRepeatingTask() {
        mStatusChecker.run();
    }

    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }

    protected void update(){
        commentsArray.clear();
        commentsArray.addAll(c.getLectureComments(lectureID));
        lectureCommentsBtn.setText("Show Comments (" + commentsArray.size()+")");
        for (Subject s : subjectArray) {
            s.setAverageRating(c.getAverageSubjectRating(s.getId()));
        }
        adapter.notifyDataSetChanged();
    }

    /**
     * The adapter for the custom statistics rows.
     */
    public class StatisticsRowAdapter extends ArrayAdapter<Subject> {
        StatisticsRowAdapter(Context context, ArrayList<Subject> subjectArray) {
            super(context, R.layout.lecture_stat_row, subjectArray);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row=convertView;
            if(row==null) {
                row=inflater.inflate(R.layout.lecture_stat_row, parent, false);
            }
            TextView subjectName=(TextView)row.findViewById(R.id.label);
            subjectName.setText(subjectArray.get(position).getName());
            RatingBar ratingIndicator = (RatingBar) row.findViewById(R.id.sub_avg_ratingbar);
            ratingIndicator.setRating(subjectArray.get(position).getAverageRating());
            return(row);
        }

    }
}
