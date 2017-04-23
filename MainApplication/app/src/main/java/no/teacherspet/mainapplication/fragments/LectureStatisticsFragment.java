package no.teacherspet.mainapplication.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import frontend.Connection;
import frontend.Subject;
import no.teacherspet.mainapplication.LectureStatistics;
import no.teacherspet.mainapplication.ProfessorLectureList;
import no.teacherspet.mainapplication.ProfessorLive;
import no.teacherspet.mainapplication.R;

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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.lecture_statistics,container,false);
        this.inflater = inflater;
        lectureID = ProfessorLectureList.getID();
        c=ProfessorLive.c;
        subjectArray = c.getSubjects(lectureID);
        for (Subject s: subjectArray) {
            subjectAvg.add(c.getAverageSubjectRating(s.getId()));
        }
        subject_list = (ListView) rootView.findViewById(R.id.subject_listview);
        StatisticsRowAdapter adapter = new StatisticsRowAdapter(getActivity(),subjectArray);
        subject_list.setAdapter(adapter);
        return rootView;
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
            ratingIndicator.setRating(subjectAvg.get(position));
            return(row);
        }

        public void onListItemClick(ListView parent, View v, int position, long id) {
            //TODO show the statistics for the chosen subject
    }
    }
}
