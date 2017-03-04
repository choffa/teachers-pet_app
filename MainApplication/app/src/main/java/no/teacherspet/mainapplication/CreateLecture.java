package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import backend.Lecture;

import static no.teacherspet.mainapplication.R.id.cancelbtn;

/**
 * Created by magnus on 03.03.2017.
 */

public class CreateLecture extends AppCompatActivity {

    TextView lectName;
    TextView roomName;
    EditText lecture;
    EditText room;
    Button startTime;
    Button endTime;
    Button done;
    Button cancel;
    private static int start=-1;
    private static int end=-1;
    private static boolean isStart;
    public static boolean getIsStart(){
        return isStart;
    }
    public static void setStart(int i){
        start=i;
    }
    public static void setEnd(int i){
        end=1;
    }
    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case(R.id.startbtn):
                    isStart = true;
                    timeSelect();
                    break;

                case(R.id.endbtn):
                    isStart=false;
                    timeSelect();
                    break;
                case(R.id.ferdigbtn):
                    if(lecture==null || room==null || start==-1 || end==-1){
                        Toast.makeText(getApplicationContext(), "Du mangler noe for å opprette en forelesning",Toast.LENGTH_LONG).show();
                    }
                    else {
 //                       LectureList.listItems.add(lecture.getText().toString());
                        LectureList.adapter.notifyDataSetChanged();
                        LectureList.lecturesArray.add(new Lecture(lecture.getText().toString(), start, end, room.getText().toString()));
                        finish();
                    }
                    break;

                case(R.id.cancelbtn):
                    finish();
                    break;
            }
        }
    };

    private void timeSelect() {
        Intent getClock=new Intent(getApplicationContext(),TimeSetter.class);
        startActivity(getClock);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_lecture);
        lectName = (TextView) findViewById(R.id.lecture_header);
        roomName = (TextView) findViewById(R.id.room_header);
        lecture = (EditText) findViewById(R.id.nametxt);
        room = (EditText) findViewById(R.id.roomtxt);
        startTime = (Button) findViewById(R.id.startbtn);
        endTime = (Button) findViewById(R.id.endbtn);
        done = (Button) findViewById(R.id.ferdigbtn);
        cancel = (Button) findViewById(R.id.cancelbtn);
        startTime.setOnClickListener(handler);
        endTime.setOnClickListener(handler);
        done.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
    }

}