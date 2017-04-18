package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import backend.Lecture;
import frontend.Connection;


public class StudentLectureList extends AppCompatActivity {

    static ArrayList<Lecture> lecturesArray = ProfessorLectureList.lecturesArray;
    public static LectureRowAdapter adapter;
    private static int ID;
    private static Lecture L;
    private static String Name;
    private Connection c;
    protected Thread thread;
    boolean noConnection;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures_student);
        noConnection = false;
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = new Connection();
                } catch (IOException e) {
                    Toast.makeText(StudentLectureList.this, "Error occurred while loading page", Toast.LENGTH_SHORT).show();
                    noConnection = false;
                    finish();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        ListView list_view = (ListView) findViewById(android.R.id.list);
        lecturesArray = c.getLectures();
        adapter = new LectureRowAdapter();
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                L = (Lecture) list_view.getItemAtPosition(position);
                ID = L.getID();
                Name = L.getCourseID();
                Intent myIntent = new Intent(StudentLectureList.this, StudentRating.class);
                startActivity(myIntent);
            }
        });
    }

    public static Lecture getL() {
        return StudentLectureList.L;
    }

    public static String getName() {
        return StudentLectureList.Name;
    }

    public static int getID() {
        return StudentLectureList.ID;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    private class LectureRowAdapter extends ArrayAdapter<Lecture> {
        LectureRowAdapter() {
            super(StudentLectureList.this, R.layout.row_lecture_list, lecturesArray);
        }

        public View getView(int position, View convertView,
                            ViewGroup parent) {
            View row=convertView;
            if(row==null) {
                LayoutInflater inflater=getLayoutInflater();
                row=inflater.inflate(R.layout.row_lecture_list, parent, false);
            }
            TextView courseID=(TextView)row.findViewById(R.id.lectureName_textView);
            TextView room = (TextView) row.findViewById(R.id.roomTextView);
            TextView time = (TextView) row.findViewById(R.id.time_textView);
            TextView date = (TextView) row.findViewById(R.id.date_textView);
            courseID.setText(lecturesArray.get(position).getCourseID());
            room.setText(lecturesArray.get(position).getRoom());
            time.setText(lecturesArray.get(position).getStart()+":15 - " + lecturesArray.get(position).getEnd());
            SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE dd MMM yyyy", Locale.ENGLISH);
            Date lectureDate = lecturesArray.get(position).getDate();
            lectureDate.setYear(lectureDate.getYear()-1900);
            String text = dateFormat.format(lectureDate);
            date.setText(text);
            return(row);
        }
    }

    @Override
    public void onDestroy() {
        try {
            if (!noConnection) {
                c.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
