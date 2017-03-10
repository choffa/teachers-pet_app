package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

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
                CreateLecture.setStart(clock.getCurrentHour());
            }
            else{
                CreateLecture.setEnd(clock.getCurrentHour());
            }
            //startActivity(finished);
            finish();
        }
    };
}
