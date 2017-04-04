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
    static ArrayList<Lecture> lecturesArray=new ArrayList<>();
    public static ArrayAdapter<Lecture> adapter;
    private int start;
    private int end;
    private static String Name;
    private String room;
    private Date date;
    private static int ID;
    Button btn;
    protected Connection c;

    protected void onCreate(Bundle savedInstanceState){
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.lectures);
            c = createConnection();
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            lecturesArray=c.getLectures(RoleSelect.ProfessorID);
            ListView list_view = (ListView) findViewById(android.R.id.list);
            adapter = new ArrayAdapter<Lecture>(this, android.R.layout.simple_list_item_1, lecturesArray);
            list_view.setAdapter(adapter);
            list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Intent myIntent=new Intent(ProfessorLectureList.this,ProfessorLive.class);
               Lecture L= (Lecture) list_view.getItemAtPosition(position);
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

    public void createBtnClicked(){
            try {
                Intent creatingLecture = new Intent(getApplicationContext(), CreateLecture.class);
                startActivity(creatingLecture);
                c.close();
            }catch (IOException e){
                Toast.makeText(getApplicationContext(),"Noe gikk galt med lukking av kobling til server",Toast.LENGTH_LONG).show();
            }
    }

    public Connection createConnection()throws IOException{return new Connection();}

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
                    if(end==now.getHours()&&now.getMinutes()>15){
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

    public boolean onOptionsItemSelected(MenuItem item) {
        try{
            c.close();
            finish();
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }


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
