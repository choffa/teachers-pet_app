package no.teacherspet.mainapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eirik on 24.04.2017.
 */

public class LectureCommentsPopup extends Activity {


    Intent intent;
    TextView header;
    ArrayList<String> comments = new ArrayList<>();
    ListView commentsList;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lecture_comment_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(height*0.5));
        commentsList = (ListView) findViewById(R.id.lecture_comments_listview);
        header = (TextView) findViewById(R.id.courseID_textview);
        intent = getIntent();

        if(intent!=null) {
            comments = intent.getStringArrayListExtra("lectureComments");
            header.setText(intent.getStringExtra("lectureName"));
        }else{
            Toast.makeText(this, "Error with Intent", Toast.LENGTH_SHORT).show();
        }
        adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,comments) {
        };
        commentsList.setAdapter(adapter);

    }

}
