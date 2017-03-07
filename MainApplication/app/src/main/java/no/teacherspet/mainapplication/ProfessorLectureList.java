package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import backend.Lecture;

/**
 * Created by magnus on 22.02.2017.
 */

public class ProfessorLectureList extends AppCompatActivity {

    static ArrayList<Lecture> lecturesArray =new ArrayList<>();
    public static ArrayAdapter<Lecture> adapter;
    private int start;
    private int end;
    private String name;
    private String room;
    private static String ID;
    private static String Name;
    Button btn;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);
        ListView list_view = (ListView) findViewById(android.R.id.list);
        adapter=new ArrayAdapter<Lecture>(this,android.R.layout.simple_list_item_1,lecturesArray);
        list_view.setAdapter(adapter);
        btn= (Button) findViewById(R.id.createbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creatingLecture = new Intent(getApplicationContext(),CreateLecture.class);
                startActivity(creatingLecture);

            }
        });

       list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Lecture L= (Lecture) list_view.getItemAtPosition(position);
               ID=L.getID();
               Name=L.getFagkode();
               Intent myIntent=new Intent(getApplicationContext(),ProfessorLive.class);
               ProfessorLive.setID(ID);
               startActivity(myIntent);
           }
       });
    }


    public void addItems(View v){
        lecturesArray.add(new Lecture(name,start,end,room));
        adapter.notifyDataSetChanged();
    }
    public static String getID(){
        return ProfessorLectureList.ID;
    }

    public static String getName() {
        return ProfessorLectureList.Name;
    }
}
