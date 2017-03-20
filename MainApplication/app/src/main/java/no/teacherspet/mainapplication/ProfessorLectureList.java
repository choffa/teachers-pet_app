package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

 //   static ArrayList<String> listItems=new ArrayList<>();
    static ArrayList<Lecture> lecturesArray;
    public static ArrayAdapter<Lecture> adapter;
    private int start;
    private int end;
    private static String Name;
    private String room;
    private Date date;
    private static int ID;
    Button btn;
    private Connection c;

    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lectures);
            c = new Connection();
            lecturesArray=c.getLectures(RoleSelect.ProfessorID);
            ListView list_view = (ListView) findViewById(android.R.id.list);
            adapter = new ArrayAdapter<Lecture>(this, android.R.layout.simple_list_item_1, lecturesArray);
            list_view.setAdapter(adapter);
            btn = (Button) findViewById(R.id.createbtn);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent creatingLecture = new Intent(getApplicationContext(), CreateLecture.class);
                        startActivity(creatingLecture);
                        c.close();
                    }catch (IOException e){
                        Toast.makeText(getApplicationContext(),"Noe gikk galt med lukking av kobling til server",Toast.LENGTH_LONG).show();
                    }

                }
            });

            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                /**
                 * controls which element in the listview gets pressed.
                 */
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Lecture L = (Lecture) list_view.getItemAtPosition(position);
                    ID = L.getID();
                    Name = L.getCourseID();
                    Intent myIntent = new Intent(getApplicationContext(), ProfessorLive.class);
                    ProfessorLive.setID(ID);
                    try {
                        c.close();
                        startActivity(myIntent);
                    }catch (IOException e){
                        Toast.makeText(getApplicationContext(),"Noe gikk galt med lukking av kobling",Toast.LENGTH_LONG).show();
                    }
                }
            });
        }catch(IOException e){
            Toast.makeText(getApplicationContext(),"Noe gikk galt under lasting av siden",Toast.LENGTH_LONG).show();
            finish();
        }
    }

    /**
     * adds an item to the listview
     * @param v: the view that the item will be added to.
     */
    public void addItems(View v){
      //  listItems.add(Name);
        lecturesArray.add(new Lecture(Name,start,end,room,date));
        adapter.notifyDataSetChanged();
    }
    public static int getID(){
        return ProfessorLectureList.ID;
    }

    public static String getName() {
        return ProfessorLectureList.Name;
    }
}
