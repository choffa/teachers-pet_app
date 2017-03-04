package no.teacherspet.mainapplication;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;

import java.util.ArrayList;

import backend.Lecture;

/**
 * Created by magnus on 22.02.2017.
 */

public class LectureList extends ListActivity {

    static ArrayList<String> listItems=new ArrayList<>();
    static ArrayList<Lecture> lectures=new ArrayList<>();
    public static ArrayAdapter<String> adapter;
    private int start;
    private int end;
    private String name;
    private String room;
    private static String ID;
    Button btn;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);
        ListView lectures = (ListView) findViewById(android.R.id.list);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        lectures.setAdapter(adapter);
        btn= (Button) findViewById(R.id.createbtn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent creatingLecture = new Intent(getApplicationContext(),CreateLecture.class);
                startActivity(creatingLecture);

            }
        });

       lectures.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Lecture L= (Lecture) lectures.getItemAtPosition(position);
               ID=L.getID();
               Intent myIntent=new Intent(getApplicationContext(),StudentRating.class);
               startActivity(myIntent);
           }
       });
    }

    void setName(String name){
        Lecture.setFagkode(name);
    }

    public void addItems(View v){
        listItems.add(name);
        lectures.add(new Lecture(name,start,end,room));
        adapter.notifyDataSetChanged();
    }
    public static String getID(){
        return ID;
    }
}
