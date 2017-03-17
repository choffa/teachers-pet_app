package no.teacherspet.mainapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;

import backend.Lecture;


public class CreateLecture extends AppCompatActivity {

    TextView lectName;
    TextView roomName;
    EditText lecture;
    EditText room;
    static Button startTime;
    static Button endTime;
    Button done;
    Button cancel;
    static Button dateBtn;
    private static Date date;
    public static Date getDate() {
        return date;
    }
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
        end=i;
    }
    public static void setDate(Date date){
        CreateLecture.date=date;
    }
    View.OnClickListener handler = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){

                case(R.id.dateBtn):
                    dateSelect();
                    break;

                case(R.id.startbtn):
                    isStart = true;
                    timeSelect();

                    break;

                case(R.id.endbtn):
                    isStart=false;
                    timeSelect();

                    break;
                case(R.id.doneBtn):
                    if(lecture==null || room==null || start==-1 || end==-1 || date == null){
                        Toast.makeText(getApplicationContext(), "Du mangler noe for å opprette en forelesning",Toast.LENGTH_LONG).show();
                    }
                    else {
 //                       ProfessorLectureList.listItems.add(lecture.getText().toString());
                        ProfessorLectureList.adapter.notifyDataSetChanged();
                        ProfessorLectureList.lecturesArray.add(new Lecture(lecture.getText().toString(), start, end, room.getText().toString(),date));
                        finish();
                    }
                    break;

                case(R.id.cancelBtn):
                    finish();
                    break;
            }
        }
    };

    @SuppressLint("SetTextI18n")
    public static void setButtonText(String buttonID, String text){
        switch (buttonID){
            case "dateBtn":
                dateBtn.setText("Date of lecture: " + text);
            break;
            case "startTime":
                startTime.setText("Start Time: " + text);
            break;
            case "endTime":
                endTime.setText("End Time: " + text);
            break;
        }
    }

    public static int getStart() {
        return start;
    }

    public static int getEnd() {
        return end;
    }

    @SuppressLint("SetTextI18n")
    private void timeSelect() {
        Intent getClock=new Intent(getApplicationContext(),TimeSetter.class);
        startActivity(getClock);
        if(start!=-1){
            startTime.setText("Start Time: " + start);
        }
        if(end!=-1){
            endTime.setText("End Time: " + end);
        }
    }

    private void dateSelect() {
        Intent getCalendar=new Intent(getApplicationContext(),DateSetter.class);
        startActivity(getCalendar);

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
        done = (Button) findViewById(R.id.doneBtn);
        cancel = (Button) findViewById(R.id.cancelBtn);
        dateBtn = (Button) findViewById(R.id.dateBtn);
        startTime.setOnClickListener(handler);
        endTime.setOnClickListener(handler);
        done.setOnClickListener(handler);
        cancel.setOnClickListener(handler);
        dateBtn.setOnClickListener(handler);
        start=-1;
        end=-1;
        date = null;
    }

}