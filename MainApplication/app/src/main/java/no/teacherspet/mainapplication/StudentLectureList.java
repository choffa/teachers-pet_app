package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import backend.Lecture;

/**
 * Created by magnus on 22.02.2017.
 */

public class StudentLectureList extends AppCompatActivity {

    static ArrayList<Lecture> lecturesArray = ProfessorLectureList.lecturesArray;
    public static ArrayAdapter<Lecture> adapter;
    private static String ID;
    private static String Name;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures_student);
        ListView list_view = (ListView) findViewById(android.R.id.list);
        adapter=new ArrayAdapter<Lecture>(this,android.R.layout.simple_list_item_1,lecturesArray);
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Lecture L= (Lecture) list_view.getItemAtPosition(position);
                ID=L.getID();
                Name=L.getFagkode();
                Intent myIntent=new Intent(getApplicationContext(),StudentRating.class);
                startActivity(myIntent);
            }
        });
    }

    public static String getID() {
        return StudentLectureList.ID;
    }

    public static String getName() {
        return StudentLectureList.Name;
    }
}
