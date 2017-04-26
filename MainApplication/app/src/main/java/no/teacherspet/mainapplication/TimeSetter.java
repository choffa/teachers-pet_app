package no.teacherspet.mainapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class TimeSetter extends AppCompatActivity {
    TimePicker clock;

    public void onCreate(Bundle SavedInstanceState) {

        super.onCreate(SavedInstanceState);
        setContentView(R.layout.timesetter);
        clock = (TimePicker) findViewById(R.id.timePicker);
        clock.setIs24HourView(true);
        if (CreateLecture.getIsStart() && CreateLecture.getStart() != -1) {
            clock.setCurrentHour(CreateLecture.getStart());
        } else if (!CreateLecture.getIsStart() && CreateLecture.getEnd() != -1) {
            clock.setCurrentHour(CreateLecture.getEnd());
        }
    }

    public void okBtnClick(View v) {
        Integer currentHour = clock.getCurrentHour();
        if (CreateLecture.getIsStart()) {
            int checkEnd = CreateLecture.getEnd();
            if (checkEnd != -1 && checkEnd != 0 && checkEnd < currentHour) {
                Toast.makeText(getApplicationContext(), "Start time cannot be after End time (" + checkEnd + ")", Toast.LENGTH_LONG).show();
            } else if (checkEnd != -1 && checkEnd == currentHour) {
                Toast.makeText(getApplicationContext(), "Start time cannot the same as End time", Toast.LENGTH_LONG).show();
            } else {
                CreateLecture.setStart(currentHour);
                finish();
                CreateLecture.setButtonText("startTime", currentHour.toString());
            }

        } else {
            int checkStart = CreateLecture.getStart();
            if (checkStart != -1 && currentHour != 0 && checkStart > currentHour) {
                Toast.makeText(getApplicationContext(), "End time cannot be before Start Time (" + checkStart + ")", Toast.LENGTH_LONG).show();

            } else if (checkStart != -1 && checkStart == currentHour) {
                Toast.makeText(getApplicationContext(), "End time cannot the same as Start time", Toast.LENGTH_LONG).show();
            } else {
                CreateLecture.setEnd(currentHour);
                finish();
                CreateLecture.setButtonText("endTime", currentHour.toString());
            }
        }
    }

}
