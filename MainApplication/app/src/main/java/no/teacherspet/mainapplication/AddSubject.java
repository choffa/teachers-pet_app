package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by eirik on 31.03.2017.
 */

public class AddSubject extends AppCompatActivity {
    static int ID;
    String name;
    String comment;
    TextView nameTextview;
    TextView commentTextview;
    Intent intent;

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
        intent = getIntent();
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
        if(intent!=null){
            String origin = intent.getStringExtra("origin");
            if(origin.equals("InitiateSubjects")){
                InitiateSubjects.setName(nameTextview.getText().toString());
                InitiateSubjects.setComment(commentTextview.getText().toString());
                InitiateSubjects.addToSubjectArray();
                InitiateSubjects.adapter.notifyDataSetChanged();
                finish();
            }else if (origin.equals("EditLecture")){
                EditLecture.setName(nameTextview.getText().toString());
                EditLecture.setComment(commentTextview.getText().toString());
                EditLecture.addToSubjectArray();
                EditLecture.adapter.notifyDataSetChanged();
                finish();
            }else{
                Toast.makeText(AddSubject.this, "Error in origin", Toast.LENGTH_LONG).show();
            }
        }else{
            Toast.makeText(AddSubject.this, "Intent is NULL", Toast.LENGTH_LONG).show();
        }

    }

    public void cancelClick(View view) {
        finish();
    }
}
