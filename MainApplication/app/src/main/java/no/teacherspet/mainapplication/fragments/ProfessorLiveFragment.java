package no.teacherspet.mainapplication.fragments;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Locale;

import no.teacherspet.mainapplication.ProfessorLive;
import no.teacherspet.mainapplication.R;

/**
 * Created by eirik on 23.04.2017.
 */

public class ProfessorLiveFragment extends Fragment {

    private int mInterval = 2000;
    RelativeLayout layout;
    private TextView text;
    private TextView studNum;
    private Handler mHandler;

    public ProfessorLiveFragment(){
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.professor_live, container, false);
        layout = (RelativeLayout) rootView.findViewById(R.id.profRelLayout);
        text = (TextView) rootView.findViewById(R.id.treKommaFem);
        studNum= (TextView) rootView.findViewById(R.id.current_rating_num);
        mHandler = new Handler();
        Button updateBtn = (Button) rootView.findViewById(R.id.updateLiveBtn);
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(ProfessorLive.c.getAverageSpeedRating(ProfessorLive.ID));
            }
        });
        startRepeatingTask();
        return rootView;
    }

    @Override
    public void onDestroyView() {
        stopRepeatingTask();
        super.onDestroyView();
    }

    /**
     * Updates the activity every mInterval milliseconds.
     */
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                update(ProfessorLive.c.getAverageSpeedRating(ProfessorLive.ID));
            } finally {
                mHandler.postDelayed(mStatusChecker, mInterval);
            }
        }
    };

    /**
     * Starts the live updates.
     */
    void startRepeatingTask() {
        mStatusChecker.run();
    }

    /**
     * Stops the live updates.
     */
    void stopRepeatingTask() {
        mHandler.removeCallbacks(mStatusChecker);
    }



    /**
     * Updates all info on the ProfessorLive view: Background and ActionBar color, and the number on the text view.
     * @param average Float between 1 and 5 with input from the tempo RadioButtons from the associated lecture
     */
    protected void update(float average){

        if(average<1||average>5){
            average= (float) 3.0;
        }
        int parsedColor=Color.parseColor(ProfessorLive.translateColor(average));
        layout.setBackgroundColor(parsedColor);
        ProfessorLive.actionBar.setBackgroundDrawable(new ColorDrawable(ProfessorLive.darker(parsedColor, (float) 0.8)));
        ProfessorLive.tabLayout.setBackgroundDrawable(new ColorDrawable(ProfessorLive.darker(parsedColor, (float) 0.8)));
        text.setText(String.format(Locale.ENGLISH,"%.1f",average-3));
        studNum.setText(Integer.toString(ProfessorLive.c.getTempoVotesInLecture(ProfessorLive.ID)));
    }

}
