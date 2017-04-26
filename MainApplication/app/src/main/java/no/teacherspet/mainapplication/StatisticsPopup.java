package no.teacherspet.mainapplication;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;
import android.widget.Toast;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by eirik on 23.04.2017.
 */

public class StatisticsPopup extends Activity {

    List<BarModel> bars;
    Intent intent;
    String origin;
    BarChart barChart;
    TextView header;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(height*0.5));
        barChart = (BarChart) findViewById(R.id.barchart);
        header = (TextView) findViewById(R.id.statpopup_header);
        bars = new ArrayList<BarModel>(6);
        intent = getIntent();

        if(intent!=null) {
            int[] subjectDistribution = intent.getIntArrayExtra("subjectDistribution");
            for (int i=0; i<6;i++){
                bars.add(new BarModel(i+"★",subjectDistribution[i],R.color.colorAccent));
            }
            header.setText(intent.getStringExtra("SubjectName"));
        }else{
            Toast.makeText(this, "Error with Intent", Toast.LENGTH_SHORT).show();
        }

        barChart.addBarList(bars);
        barChart.startAnimation();
    }
}
