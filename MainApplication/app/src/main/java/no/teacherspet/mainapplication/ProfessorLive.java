package no.teacherspet.mainapplication;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.RelativeLayout;
import android.widget.Toast;
import java.io.IOException;
import java.util.ArrayList;

import frontend.Connection;
import no.teacherspet.mainapplication.fragments.LectureStatisticsFragment;
import no.teacherspet.mainapplication.fragments.ProfessorLiveFragment;

/**
 * Created by magnus on 17.02.2017.
 */


public class ProfessorLive extends AppCompatActivity {
    public static int ID;
    public static Connection c;
    protected Thread thread;
    private ViewPager mViewPager;
    public static TabLayout tabLayout;
    public static void setID(int ID) {
        ProfessorLive.ID = ID;
    }
    public static ActionBar actionBar;
    RelativeLayout layout;
    private SectionsPagerAdapter mSectionsPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.professor_live_container);
        actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(ProfessorLectureList.getName());
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
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.live_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabLayout = (TabLayout) findViewById(R.id.live_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    /**
     * Translates a float between 1 and 5 to RGB code in hex scaling from blue to red.
     * @param average Float between 1 and 5
     * @return Color in form of hex-string "#XXXXXX"
     */
    public static String translateColor(float average){

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

    public static String getTempoText(float avg){
        if(avg==-1){
            return "No votes registered";
        } else if(avg<1.8){
            return "Very slow";
        }else if(avg<2.6){
            return "Slow";
        }else if(avg<3.4){
            return "Perfect";
        }else if(avg<4.2){
            return "Fast";
        }else{
            return "Very Fast";
        }
    }

    public static int[] getUpdatedSubjectRating(int subjectID){
        return c.getSubjectStats(subjectID);
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    ProfessorLiveFragment profLive = new ProfessorLiveFragment();
                    return profLive;
                case 1:
                    LectureStatisticsFragment stat = new LectureStatisticsFragment();
                    return stat;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Live View";
                case 1:
                    return "Statistics";

            }
            return null;
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
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

