package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import backend.Lecture;
import frontend.Connection;

/**
 * Created by magnus on 22.02.2017.
 */

public class StudentLectureList extends AppCompatActivity {

    static ArrayList<Lecture> lecturesArray = ProfessorLectureList.lecturesArray;
    public static ArrayAdapter<Lecture> adapter;
    private static int ID;
    private static Lecture L;
    private static String Name;
    private Connection c;
    protected Thread thread;
    boolean noConnection;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures_student);
        noConnection = false;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = new Connection();
                } catch (IOException e) {
                    Toast.makeText(StudentLectureList.this, "Noe gikk galt under lasting av siden", Toast.LENGTH_SHORT).show();
                    noConnection = false;
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
        final ListView list_view = (ListView) findViewById(android.R.id.list);
        lecturesArray = c.getLectures();
        adapter = new ArrayAdapter<Lecture>(this, android.R.layout.simple_list_item_1, lecturesArray);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L = (Lecture) list_view.getItemAtPosition(position);
                ID = L.getID();
                Name = L.getCourseID();
                Intent myIntent = new Intent(getApplicationContext(), StudentRating.class);
                startActivity(myIntent);
            }
        });
    }

    public static Lecture getL() {
        return StudentLectureList.L;
    }

    public static String getName() {
        return StudentLectureList.Name;
    }

    public static int getID() {
        return StudentLectureList.ID;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;


    }

    @Override
    public void onDestroy() {
        try {
            if (!noConnection) {
                c.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
