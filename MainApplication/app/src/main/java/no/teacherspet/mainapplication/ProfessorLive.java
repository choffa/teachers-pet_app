package no.teacherspet.mainapplication;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.Locale;

import frontend.Connection;

/**
 * Created by magnus on 17.02.2017.
 */


public class ProfessorLive extends AppCompatActivity {
    private int mInterval = 2000;
    private Handler mHandler;
    private static int ID;
    protected Connection c;
    protected Thread thread;
    public static void setID(int ID) {
        ProfessorLive.ID = ID;
    }
    ActionBar actionBar;
    RelativeLayout layout;
    TextView text;
    TextView studNum;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_live);
        layout = (RelativeLayout) findViewById(R.id.profRelLayout);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(ProfessorLectureList.getName());
        text = (TextView) findViewById(R.id.treKommaFem);
        studNum= (TextView) findViewById(R.id.current_rating_num);
        setID(ProfessorLectureList.getID());
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = new Connection();
                }
                catch (IOException e){
                    Toast.makeText(ProfessorLive.this, "Error occurred when loading page.", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mHandler = new Handler();
        startRepeatingTask();
    }

    /**
     * Updates the activity every mInterval milliseconds.
     */
    Runnable mStatusChecker = new Runnable() {
        @Override
        public void run() {
            try {
                update(c.getAverageSpeedRating(ID));
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
     * Updates the activity, if the giver interval is not sufficient.
     * @param v The button view.
     */
    public void updateButtonClick(View v){
        update(c.getAverageSpeedRating(ID));
    }

    public void changeToStat(View v){
        Intent myIntent=new Intent(ProfessorLive.this,LectureStatistics.class);
        startActivity(myIntent);
    }

    /**
     * Translates a float between 1 and 5 to RGB code in hex scaling from blue to red.
     * @param average Float between 1 and 5
     * @return Color in form of hex-string "#XXXXXX"
     */
    protected String translateColor(float average){

        String ratingColor;
        String opacityColor;

        //gives a number between -250 and 250 respectively,depending on distance to "perfect tempo".
        int opacity = (int) ((average-3)*100)*250/200;
        //Translates opacity into hex
        opacityColor = Integer.toHexString(255-Math.abs(opacity));

        if (opacity == 0) {
            ratingColor = "#ffffff";
        } else if (opacity > 255 - 16) {
            ratingColor = "#ff0" + opacityColor + "0" + opacityColor; //Sterk roed
        } else if (opacity > 0) {
            ratingColor = "#ff" + opacityColor + opacityColor;
        } else if (opacity < 16 - 255) {
            ratingColor = "#0" + opacityColor + "0" + opacityColor + "ff";
        } else {
            ratingColor = "#" + opacityColor + opacityColor + "ff";
        }
        return ratingColor;
    }

    /**
     * Gives a darker hue of the chosen color, based on the factor used (0.8 means 20% darker)
     * @param color the color you wish to change the hue of.
     * @param factor float between 0 and 1.
     * @return int
     */
    public static int darker (int color, float factor) {
        int a = Color.alpha( color );
        int r = Color.red( color );
        int g = Color.green( color );
        int b = Color.blue( color );

        return Color.argb( a,
                Math.max( (int)(r * factor), 0 ),
                Math.max( (int)(g * factor), 0 ),
                Math.max( (int)(b * factor), 0 ) );
    }

    /**
     * Updates all info on the ProfessorLive view: Background and ActionBar color, and the number on the text view.
     * @param average Float between 1 and 5 with input from the tempo RadioButtons from the associated lecture
     */
    protected void update(float average){

        if(average<1||average>5){
            average= (float) 3.0;
        }
        layout.setBackgroundColor(Color.parseColor(translateColor(average)));
        actionBar.setBackgroundDrawable(new ColorDrawable(darker(Color.parseColor(translateColor(average)), (float) 0.8)));
        text.setText(String.format(Locale.ENGLISH,"%.1f",average-3));
        studNum.setText(Integer.toString(c.getTempoVotesInLecture(ID)));
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onDestroy(){
        try {
            stopRepeatingTask();
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();

    }


}

