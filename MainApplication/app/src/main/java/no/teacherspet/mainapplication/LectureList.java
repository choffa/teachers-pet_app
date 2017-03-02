package no.teacherspet.mainapplication;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import java.util.ArrayList;

/**
 * Created by magnus on 22.02.2017.
 */

public class LectureList extends ListActivity {

    ArrayList<String> listItems=new ArrayList<>();
    ArrayAdapter<String> adapter;
    private EditText medit;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lectures);
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,listItems);
        setListAdapter(adapter);
        medit= (EditText) findViewById(R.id.lecture_name);
    }

    public void addItems(View v){
        listItems.add(medit.getText().toString());
        adapter.notifyDataSetChanged();
    }
}
