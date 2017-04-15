package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import backend.Lecture;
import frontend.Connection;

/**
 * Created by magnus on 22.02.2017.
 */

public class ProfessorLectureList extends AppCompatActivity {

    static ArrayList<Lecture> lecturesArray=new ArrayList<>();
    public static ArrayAdapter<Lecture> adapter;
    private static String Name;
    private static int ID;
    Thread thread;
    protected Connection c;
    ListView list_view;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = new Connection();
                } catch (IOException e) {
                    Toast.makeText(ProfessorLectureList.this, "Noe gikk galt under lasting av siden", Toast.LENGTH_SHORT).show();
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
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        lecturesArray=c.getLectures(RoleSelect.ProfessorID);
        list_view = (ListView) findViewById(android.R.id.list);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, lecturesArray);
        initiateListAdapter(list_view,adapter);
    }

    /**
     * Sets the onClick logic for listView's adapter listAdapter
     * @param listView The ListView to set the logic for.
     * @param listAdapter The ListAdapter to override onItemClick for.
     */
    private void initiateListAdapter(ListView listView, ListAdapter listAdapter){
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent myIntent=new Intent(ProfessorLectureList.this,ProfessorLive.class);
                Lecture L= (Lecture) listView.getItemAtPosition(position);
                ID=L.getID();
                Name = L.getCourseID();
                switch (beforeNowAfter(L.getDate(),L.getStart(),L.getEnd())) {
                    case 0:
                        myIntent = new Intent(ProfessorLectureList.this, LectureStatistics.class);
                        break;
                    case 1:
                        myIntent = new Intent(ProfessorLectureList.this, ProfessorLive.class);
                        break;
                    case 2:
                        myIntent = new Intent(ProfessorLectureList.this, EditLecture.class);
                        break;
                }
                ProfessorLive.setID(ID);
                startActivity(myIntent);
            }
        });
    }

    public void createBtnClicked(View v){
        Intent creatingLecture = new Intent(getApplicationContext(), CreateLecture.class);
        startActivity(creatingLecture);
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

    /**
     * Updates the list to check for new lectures.
     */
    private void update(){
        lecturesArray.clear();
        lecturesArray.addAll(c.getLectures(RoleSelect.ProfessorID));
        adapter.notifyDataSetChanged();
    }

    public static int getID(){
        return ProfessorLectureList.ID;
    }

    public static String getName() {
        return ProfessorLectureList.Name;
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
