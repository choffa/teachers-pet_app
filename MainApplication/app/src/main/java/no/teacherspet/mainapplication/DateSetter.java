package no.teacherspet.mainapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by magnus on 03.03.2017.
 */

public class DateSetter extends AppCompatActivity {
    DatePicker calendar;
    Button confirm;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.datesetter);
        confirm = (Button) findViewById(R.id.confirmDateBtn);
        calendar = (DatePicker) findViewById(R.id.datePicker);

        confirm.setOnClickListener(buttonPresser);

    }
    View.OnClickListener buttonPresser=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            CreateLecture.setDate(new Date(calendar.getYear(),calendar.getMonth(),calendar.getDayOfMonth()));
            finish();
        }
    };
}
