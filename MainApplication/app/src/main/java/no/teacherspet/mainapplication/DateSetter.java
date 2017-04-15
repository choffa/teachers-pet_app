package no.teacherspet.mainapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@SuppressWarnings({"deprecation", "UnnecessaryLocalVariable"})
public class DateSetter extends AppCompatActivity {
    DatePicker calendar;

    public void onCreate(Bundle SavedInstanceState){
        super.onCreate(SavedInstanceState);
        setContentView(R.layout.datesetter);
        calendar = (DatePicker) findViewById(R.id.datePicker);
        if(CreateLecture.getDate()!=null){
            Date currentCreatedDate = CreateLecture.getDate();
            int currentYear = currentCreatedDate.getYear()+1900;
            int currentMonth = currentCreatedDate.getMonth();
            int currentDay = currentCreatedDate.getDate();
            calendar.updateDate(currentYear,currentMonth,currentDay);
        }
    }

    public void dateSetterDone(View v){
        CreateLecture.setDate(new Date(calendar.getYear(),calendar.getMonth(),calendar.getDayOfMonth()));
        Date date = CreateLecture.getDate();
        if(date!=null){
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.ENGLISH);
            Date dateView = date;
            dateView.setYear(dateView.getYear()-1900);
            String text = dateFormat.format(dateView);
            CreateLecture.setButtonText("dateBtn", text);
        }
        finish();
    }
}
