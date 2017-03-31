package no.teacherspet.mainapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

/**
 * Created by eirik on 31.03.2017.
 */

public class AddSubject extends AppCompatActivity {
    static int ID;
    String name;
    String comment;
    TextView nameTextview;
    TextView commentTextview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_subject);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        name = InitiateSubjects.getName();
        comment = InitiateSubjects.getComment();
        nameTextview = (TextView) findViewById(R.id.name_textview);
        commentTextview = (TextView) findViewById(R.id.comments_textview);
        nameTextview.setText(name);
        commentTextview.setText(comment);
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    public static int getID() {
        return ID;
    }

    public static void setID(int ID) {
        AddSubject.ID = ID;
    }

    public void finishedClick(View view) {
        InitiateSubjects.setName(nameTextview.getText().toString());
        InitiateSubjects.setComment(commentTextview.getText().toString());
        InitiateSubjects.addToSubjectArray();
        InitiateSubjects.adapter.notifyDataSetChanged();
        finish();
    }

    public void cancelClick(View view) {
        finish();
    }
}
