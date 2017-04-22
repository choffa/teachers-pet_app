package no.teacherspet.mainapplication.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import backend.Lecture;
import no.teacherspet.mainapplication.EditLecture;
import no.teacherspet.mainapplication.LectureStatistics;
import no.teacherspet.mainapplication.ProfessorLectureList;
import no.teacherspet.mainapplication.ProfessorLive;
import no.teacherspet.mainapplication.R;

/**
 * Created by eirik on 22.04.2017.
 */

public class LectureListSuper extends Fragment {

    ArrayList<Lecture> lecturesArray = new ArrayList<>();
    static LayoutInflater inflater;
    private static final String ARG_TAB_NUMBER = "tab_number";
    private LectureRowAdapter adapter;

    public LectureListSuper(){

    }

    public static LectureListSuper newInstance(int tabNumber) {
        LectureListSuper fragment = new LectureListSuper();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_NUMBER, tabNumber);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Sets the onClick logic for listView's adapter listAdapter
     * @param listView The ListView to set the logic for.
     * @param listAdapter The ListAdapter to override onItemClick for.
     */
    private void initiateListAdapter(final ListView listView, ListAdapter listAdapter){
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent=new Intent(getActivity(),ProfessorLive.class);
                Lecture L= (Lecture) listView.getItemAtPosition(position);
                ProfessorLectureList.setID(L.getID());
                ProfessorLectureList.setName(L.getCourseID());
                switch (beforeNowAfter(L.getDate(),L.getStart(),L.getEnd())) {
                    case 0:
                        myIntent = new Intent(getActivity(), LectureStatistics.class);
                        break;
                    case 1:
                        myIntent = new Intent(getActivity(), ProfessorLive.class);
                        break;
                    case 2:
                        myIntent = new Intent(getActivity(), EditLecture.class);
                        break;
                }
                ProfessorLive.setID(ProfessorLectureList.getID());
                startActivity(myIntent);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        LectureListSuper.inflater = inflater;
        View rootView = inflater.inflate(R.layout.all_lectures, container, false);
        ListView listView = (ListView) rootView.findViewById(R.id.list_all_lectures);
        setLecturesArray(ProfessorLectureList.getLecturesArray());
        adapter = new LectureRowAdapter(getActivity(),lecturesArray);
        initiateListAdapter(listView,adapter);
        return rootView;
    }

    public void update(){
        setLecturesArray(ProfessorLectureList.getLecturesArray());
        adapter.notifyDataSetChanged();
    }

    public void setLecturesArray(ArrayList<Lecture> lecturesArray) {
        this.lecturesArray = lecturesArray;
    }

    private class LectureRowAdapter extends ArrayAdapter<Lecture> {
        LectureRowAdapter(Context context,ArrayList<Lecture> lecturesArray) {
            super(context, R.layout.row_lecture_list, lecturesArray);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            if(position>=lecturesArray.size()){
                return null;
            }
            View row=convertView;
            if(row==null) {
                LayoutInflater inflater=LectureListSuper.inflater;
                row=inflater.inflate(R.layout.row_lecture_list, parent, false);
            }
            TextView courseID=(TextView)row.findViewById(R.id.lectureName_textView);
            TextView room = (TextView) row.findViewById(R.id.roomTextView);
            TextView time = (TextView) row.findViewById(R.id.time_textView);
            TextView date = (TextView) row.findViewById(R.id.date_textView);
            courseID.setText(lecturesArray.get(position).getCourseID());
            room.setText(lecturesArray.get(position).getRoom());
            time.setText(lecturesArray.get(position).getStart()+":15 - " + lecturesArray.get(position).getEnd()+":00");
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEE dd.MM.yy", Locale.ENGLISH);
            Date lectureDate = lecturesArray.get(position).getDate();
            lectureDate.setYear(lectureDate.getYear());
            String text = dateFormat.format(lectureDate);
            date.setText(text);
            return(row);
        }
    }

    /**
     * Checks whether the lecture is currently active, is yet to start or is done.
     * @param lectureDate Date of the Lecture
     * @param start Start hour for the lecture
     * @param end End hour for the lecture
     * @return returns 0 if lecture is done, 1 if ongoing, 2 if not yet started
     */
    private int beforeNowAfter(Date lectureDate, int start, int end){
        Date now = new Date();
        if(lectureDate.getYear()<now.getYear()){
            return 0;
        }else if(lectureDate.getYear()>now.getYear()){
            return 2;
        }else{
            if(lectureDate.getMonth()<now.getMonth()){
                return 0;
            }else if(lectureDate.getMonth()>now.getMonth()){
                return 2;
            }else{
                if(lectureDate.getDate()<now.getDate()){
                    return 0;
                }else if(lectureDate.getDate()>now.getDate()){
                    return 2;
                }else{
                    if(end==now.getHours()&&now.getMinutes()>15){ //Lectures end officially after 15min past the end hour.
                        return 0;
                    }else if(end<now.getHours()){
                        return 0;
                    }else if(start>now.getHours()){
                        return 2;
                    }else{
                        return 1;
                    }
                }
            }
        }
    }

}
