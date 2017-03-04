package no.teacherspet.mainapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

import backend.Lecture;

/**
 * Created by magnus on 22.02.2017.
 */

public class StudentLectureList extends ListActivity {
//Ren copy/paste fra LectureList atm
    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    private EditText medit;
    private boolean isStart;
    Lecture L;
    private int start;
    private int end;
    private String name;
    private String room;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures_student);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        setListAdapter(adapter);
    }

    void setName(String name){
        Lecture.setFagkode(name);
    }

    public void addItems(View v){
        listItems.add(medit.getText().toString());
        adapter.notifyDataSetChanged();
    }
}
