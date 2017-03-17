package no.teacherspet.mainapplication;

import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class TimeSetter extends AppCompatActivity {
    TimePicker clock;
    Button done;

    public void onCreate(Bundle SavedInstanceState){

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.timesetter);
        done= (Button) findViewById(R.id.okbtn);
        clock = (TimePicker) findViewById(R.id.timePicker);
        clock.setIs24HourView(true);
        if(CreateLecture.getIsStart()&&CreateLecture.getStart()!=-1){
            clock.setCurrentHour(CreateLecture.getStart());
        }else if(!CreateLecture.getIsStart()&&CreateLecture.getEnd()!=-1){
            clock.setCurrentHour(CreateLecture.getEnd());
        }

        done.setOnClickListener(buttonPresser);
    }
    View.OnClickListener buttonPresser=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent finished=new Intent(getApplicationContext(),CreateLecture.class);
            if(CreateLecture.getIsStart()){
                if(CreateLecture.getEnd()!= -1 && CreateLecture.getEnd()<clock.getCurrentHour()) {
                    Toast.makeText(getApplicationContext(),"Start time cannot be after End time (" + CreateLecture.getEnd() + ")", Toast.LENGTH_LONG).show();
                }else if(CreateLecture.getEnd()!= -1 && CreateLecture.getEnd()==clock.getCurrentHour()){
                    Toast.makeText(getApplicationContext(),"Start time cannot the same as End time", Toast.LENGTH_LONG).show();
                }else{
                    CreateLecture.setStart(clock.getCurrentHour());
                    finish();
                    CreateLecture.setButtonText("startTime", clock.getCurrentHour().toString());
                }

            }
            else{
                if (CreateLecture.getStart() != -1 && CreateLecture.getStart()>clock.getCurrentHour()){
                    Toast.makeText(getApplicationContext(), "End time cannot be before Start Time (" + CreateLecture.getStart() + ")" , Toast.LENGTH_LONG).show();

                }else if(CreateLecture.getStart()!= -1 && CreateLecture.getStart()==clock.getCurrentHour()){
                    Toast.makeText(getApplicationContext(),"End time cannot the same as Start time", Toast.LENGTH_LONG).show();
                }else{
                    CreateLecture.setEnd(clock.getCurrentHour());
                    finish();
                    CreateLecture.setButtonText("endTime", clock.getCurrentHour().toString());
                }
            }
            //startActivity(finished);
            //finish();
        }
    };
}
