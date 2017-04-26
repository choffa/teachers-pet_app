package no.teacherspet.mainapplication.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import backend.Lecture;
import no.teacherspet.mainapplication.ProfessorLectureList;
import no.teacherspet.mainapplication.R;

/**
 * Created by eirik on 22.04.2017.
 */

public class AllLectures extends LectureListSuper {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ProfessorLectureList.fragmentHashMap.put("AllLectures",this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }
}
