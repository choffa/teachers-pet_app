package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import backend.Lecture;
import frontend.Connection;
import no.teacherspet.mainapplication.fragments.AllLectures;
import no.teacherspet.mainapplication.fragments.LectureListSuper;
import no.teacherspet.mainapplication.fragments.TodaysLectures;

/**
 * Created by magnus on 22.02.2017.
 */

public class ProfessorLectureList extends AppCompatActivity {

    public static HashMap<String,LectureListSuper> fragmentHashMap = new HashMap<>();
    static ArrayList<Lecture> lecturesArray=new ArrayList<>();
    private static String Name;
    private static int ID;
    Thread thread;
    protected Connection c;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = new Connection();
                } catch (IOException e) {
                    Toast.makeText(ProfessorLectureList.this, "Error while loading page", Toast.LENGTH_SHORT).show();
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
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 1:
                    AllLectures allLectures = new AllLectures();
                    return allLectures;
                case 0:
                    TodaysLectures todaysLectures = new TodaysLectures();
                    return todaysLectures;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 1:
                    return "History";
                case 0:
                    return "Today";

            }
            return null;
        }
    }


    public void createBtnClicked(View v){
        Intent creatingLecture = new Intent(getApplicationContext(), CreateLecture.class);
        startActivity(creatingLecture);
    }



    /**
     * Updates the list to check for new lectures.
     */
    private void update(){
        lecturesArray.clear();
        lecturesArray.addAll(c.getLectures(RoleSelect.ProfessorID));
        mSectionsPagerAdapter.notifyDataSetChanged();
    }

    public static int getID(){
        return ProfessorLectureList.ID;
    }

    public static void setID(int ID) {
        ProfessorLectureList.ID = ID;
    }

    public static void setName(String name) {
        Name = name;
    }

    public static String getName() {
        return ProfessorLectureList.Name;
    }

    public static ArrayList<Lecture> getLecturesArray() {
        return lecturesArray;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        update();
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
