package no.teacherspet.mainapplication;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.DisplayMetrics;
import android.widget.TextView;

/**
 * Created by eirik on 13.04.2017.
 */

public class CommentsPopup extends Activity {

    TextView subjectName;
    TextView subjectComment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comments_popup);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width =dm.widthPixels;
        int height = dm.heightPixels;
        getWindow().setLayout((int)(width*0.7),(int)(height*0.5));
        subjectName = (TextView) findViewById(R.id.subject_name_textview);
        subjectComment = (TextView) findViewById(R.id.subject_comments_textview);
        subjectName.setText(StudentRating.currentSub.getName());
        subjectComment.setText(StudentRating.currentSub.getComment());
    }
}
