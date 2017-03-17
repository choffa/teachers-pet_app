package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

/**
 * Created by magnus on 03.03.2017.
 */

public class TimeSetter extends AppCompatActivity {
    TimePicker clock;
    Button done;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.timesetter);
        done= (Button) findViewById(R.id.okbtn);
        clock = (TimePicker) findViewById(R.id.timePicker);
        clock.setIs24HourView(true);
        done.setOnClickListener(buttonPresser);
    }
    View.OnClickListener buttonPresser=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            //Intent finished=new Intent(getApplicationContext(),CreateLecture.class);
            if(CreateLecture.getIsStart()){
                if(CreateLecture.getEnd()!= -1 && CreateLecture.getEnd()<clock.getCurrentHour()) {
                    Toast.makeText(getApplicationContext(),"Start time cannot be after End time", Toast.LENGTH_LONG).show();
                }else if(CreateLecture.getEnd()!= -1 && CreateLecture.getEnd()==clock.getCurrentHour()){
                    Toast.makeText(getApplicationContext(),"Start time cannot the same as End time", Toast.LENGTH_LONG).show();
                }else{
                    CreateLecture.setStart(clock.getCurrentHour());
                    finish();
                }
            }
            else{
                if (CreateLecture.getStart() != -1 && CreateLecture.getStart()>clock.getCurrentHour()) {
                    Toast.makeText(getApplicationContext(), "End time cannot be before Start Time", Toast.LENGTH_LONG).show();

                }else if(CreateLecture.getStart()!= -1 && CreateLecture.getStart()==clock.getCurrentHour()){
                    Toast.makeText(getApplicationContext(),"End time cannot the same as Start time", Toast.LENGTH_LONG).show();
                }else{
                    CreateLecture.setEnd(clock.getCurrentHour());
                    finish();
                }
            }
            //startActivity(finished);
            //finish();
        }
    };
}
