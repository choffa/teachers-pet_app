package no.teacherspet.mainapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Date;

import backend.Lecture;
import no.teacherspet.mainapplication.R;

/**
 * Created by eirik on 22.04.2017.
 */

public class TodaysLectures extends LectureListSuper {


    @Override
    public void setLecturesArray(ArrayList<Lecture> lecturesArray) {
        ArrayList<Lecture> resList = new ArrayList<>();
        for (Lecture L : lecturesArray) {
            if(isToday(L.getDate())){
                resList.add(L);
            }
        }
        super.setLecturesArray(resList);
    }

    /**
     * Checks whether the lecture is currently active, is yet to start or is done.
     * @param lectureDate Date of the Lecture
     * @return returns true if lecture is today, false otherwise.
     */
    private boolean isToday(Date lectureDate){
        Date now = new Date();
        if(lectureDate.getYear()<now.getYear()|| lectureDate.getYear()>now.getYear()){
            return false;
        }else{
            if(lectureDate.getMonth()<now.getMonth()||(lectureDate.getMonth()>now.getMonth())){
                return false;
            }else{
                if(lectureDate.getDate()<now.getDate()|| lectureDate.getDate()>now.getDate()){
                    return false;
                }else{
                    return true;
                }
            }
        }
    }
}

