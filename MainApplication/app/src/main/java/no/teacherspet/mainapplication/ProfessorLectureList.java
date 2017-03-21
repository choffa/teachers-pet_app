package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Date;

import backend.Lecture;

/**
 * Created by magnus on 22.02.2017.
 */

public class ProfessorLectureList extends AppCompatActivity {

 //   static ArrayList<String> listItems=new ArrayList<>();
    static ArrayList<Lecture> lecturesArray =new ArrayList<>();
    public static ArrayAdapter<Lecture> adapter;
    private int start;
    private int end;
    private static String Name;
    private String room;
    private Date date;
    private static int ID;
    Button btn;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
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
               Intent myIntent=new Intent(getApplicationContext(),ProfessorLive.class);
               Lecture L= (Lecture) list_view.getItemAtPosition(position);
               ID=L.getID();
               Name = L.getCourseID();
               switch (beforeNowAfter(L.getDate(),L.getStart(),L.getEnd())){
                   case 0:
                       myIntent=new Intent(getApplicationContext(),LectureStatistics.class);
                       break;
                   case 1:
                       myIntent=new Intent(getApplicationContext(),ProfessorLive.class);
                       break;
                   case 2:
                       myIntent=new Intent(getApplicationContext(),EditLecture.class);
                       break;
               }

               ProfessorLive.setID(ID);
               startActivity(myIntent);
           }
       });
    }

    /**
     * returns 0 if lecture is done, 1 if ongoing, 2 if not yet started
     * @param lectureDate
     * @param start
     * @param end
     * @return
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
                    if(end<=now.getHours()&&now.getMinutes()>15){
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

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

}
