package no.teacherspet.mainapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import backend.Lecture;
import frontend.Connection;
import frontend.Subject;

public class StudentRating extends AppCompatActivity{

    int radioButtonID;
    private static int lectureID;
    static Subject currentSub;
    RadioGroup tempo;
    ArrayList<Subject> subjects;
    ArrayList<String> subjectNames= new ArrayList<>();
    ArrayList<String> subjectComments = new ArrayList<>();
    ArrayList<Integer> subjectIDs = new ArrayList<>();
    private Connection c;
    protected Thread thread;
    ListView ratingList;
    public boolean isOnCreate;
    TextView commentsTxtView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        isOnCreate =true;
        super.onCreate(savedInstanceState);
        lectureID = StudentLectureList.getID();
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = new Connection();
                } catch (IOException e) {
                    Toast.makeText(StudentRating.this, "Error while loading page", Toast.LENGTH_SHORT).show();
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
        subjects = c.getSubjects(StudentLectureList.getID());
        ArrayList list = new ArrayList();
        for (Subject s : subjects) {
            subjectNames.add(s.getName());
            subjectComments.add(s.getComment());
            subjectIDs.add(s.getId());
            list.add(new RowModel(s));
        }
        setContentView(R.layout.activity_student_rating);
        commentsTxtView = (TextView) findViewById(R.id.studentrating_comments_textview);
        ratingList = (ListView) findViewById(android.R.id.list);
        ratingList.setAdapter(new SubjectRatingAdapter(list));
        RelativeLayout.LayoutParams mParam = (RelativeLayout.LayoutParams) ratingList.getLayoutParams();
        mParam.height = (calculateHeight(ratingList));
        ratingList.setLayoutParams(mParam);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(StudentLectureList.getName());
        tempo = (RadioGroup) findViewById(R.id.tempoRadioGroup);
        int curTempoRating = c.getStudentSpeedRating(RoleSelect.getStudentId(),lectureID);
        if (curTempoRating!=-1) {
            tempo.check(tempo.getChildAt(curTempoRating-1).getId());
        }
        initiateRadioGroup(tempo);
        isOnCreate=false;
    }

    /**
     * Sets the radiobuttons to send the new info to the server on every click.
     * @param radioGroup The Tempo RadioGroup.
     */
    private void initiateRadioGroup(RadioGroup radioGroup){

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                if (!isValidTime()) {
                    Toast.makeText(getApplicationContext(), "This lecture is not active", Toast.LENGTH_LONG).show();
                    finish();
                }else{
                    radioButtonID = radioGroup.getCheckedRadioButtonId();
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(radioButtonID);
                    int rating = radioGroup.indexOfChild(radioButton) + 1;
                    c.sendSpeedRating(StudentLectureList.getID(), RoleSelect.StudentId, rating);
                    Toast.makeText(StudentRating.this, "Lecture tempo rated: " + radioButton.getText(), Toast.LENGTH_SHORT).show();
                 }
            }
        });
    }

    private RowModel getModel(int position) {
        return (RowModel) ((SubjectRatingAdapter) ratingList.getAdapter()).getItem(position);
    }

    public void sendComment(View view) {
        c.setLectureComment(lectureID, commentsTxtView.getText().toString());
        Toast.makeText(this, "Comments sent", Toast.LENGTH_SHORT).show();
    }
    /**
     * Custom ArrayAdapter to handle a ListView of subjects with RatingBars.
     */
    private class SubjectRatingAdapter extends ArrayAdapter {
        SubjectRatingAdapter(ArrayList list) {
            super(StudentRating.this, R.layout.student_rating_row, list);
        }

        /**
         * Handles the generation/recycling of row views, and the listeners for all the widgets in each row.
         * @param position Position in the ListView.
         * @param convertView ...
         * @param parent The ListView.
         * @return A row view.
         */
        public View getView(final int position, View convertView, ViewGroup parent) {
            View row = convertView;
            ViewWrapper wrapper;
            RatingBar rate;
            FloatingActionButton showCommentsFAB;
            if (row == null) {
                LayoutInflater inflater = getLayoutInflater();
                row = inflater.inflate(R.layout.student_rating_row, parent, false);
                wrapper = new ViewWrapper(row);
                row.setTag(wrapper);
                rate = wrapper.getRatingBar();
                RatingBar.OnRatingBarChangeListener listener =
                        new RatingBar.OnRatingBarChangeListener() {
                            public void onRatingChanged(RatingBar ratingBar,
                                                        float rating, boolean fromTouch) {
                                if (!isValidTime()) {
                                    Toast.makeText(StudentRating.this, "Lecture is not active", Toast.LENGTH_SHORT).show();
                                    finish();
                                } else if (!isOnCreate){
                                    Integer myPosition = (Integer) ratingBar.getTag();
                                    RowModel model = getModel(myPosition);
                                    model.rating = rating;
                                    currentSub = subjects.get(myPosition);
                                    c.sendSubjectRating(currentSub.getId(), RoleSelect.StudentId, Math.round(rating), "GTFO");
                                    Toast.makeText(StudentRating.this, currentSub.getName().trim() + " rated a " + Math.round(rating) + "/5", Toast.LENGTH_SHORT).show();
                                }
                            }
                        };
                rate.setOnRatingBarChangeListener(listener);
                showCommentsFAB = wrapper.getCommentFAB();
                String currentComment = subjectComments.get(position);
                if(currentComment.isEmpty() || currentComment.equals("NULL")) {
                    showCommentsFAB.hide();
                }
                FloatingActionButton.OnClickListener fabl = new FloatingActionButton.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Integer myPosition = (Integer) v.getTag();
                        currentSub = subjects.get(position);
                        startActivity(new Intent(StudentRating.this, CommentsPopup.class));
                    }
                };
                showCommentsFAB.setOnClickListener(fabl);
            } else {
                wrapper = (ViewWrapper) row.getTag();
                rate = wrapper.getRatingBar();
            }
            RowModel model = getModel(position);
            wrapper.getLabel().setText(model.toString());
            rate.setTag(position);
            rate.setRating(model.rating);
            return (row);
        }
    }

    /**
     * Holds the info of a given row of subjects.
     */
    private class RowModel {
        String subjectName;
        float rating;
        String comment;


        RowModel(Subject subject) {
            this.subjectName = subject.getName();
            this.comment = subject.getComment();
            this.rating = c.getStudentSubjectRating(RoleSelect.getStudentId(),subject.getId());
        }

        public String toString() {
            return (subjectName);
        }
    }

    /**
     * Gives the total height of the subject list elements.
     * @param list The ListView to find the height of.
     * @return Total height of list in pixels.
     */
    private int calculateHeight(ListView list) {

        int height = 0;
        for (int i = 0; i < list.getCount(); i++) {
            View childView = list.getAdapter().getView(i, null, list);
            childView.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
            height+= childView.getMeasuredHeight();
        }
        //dividers height
        height += list.getDividerHeight() * list.getCount();
        return height;
    }

    private boolean isValidTime(){
        Lecture lecture = StudentLectureList.getL();
        Date lectureDate = lecture.getDate();
        int start = lecture.getStart();
        int end = lecture.getEnd();
        Date now = new Date();
        if(lectureDate.getYear()<now.getYear()||lectureDate.getYear()>now.getYear()){
            return false;
        } else {
            if (lectureDate.getDate() < now.getDate() || lectureDate.getDate() > now.getDate()) {
                return false;
            } else {
                if ((end == now.getHours() && now.getMinutes() > 30) || end < now.getHours() || start > now.getHours()) {
                    return false;
                } else {
                    return true;
                }
            }
        }
    }

    public boolean onOptionsItemSelected(MenuItem item){
        finish();
        return true;
    }

    @Override
    public void onDestroy() {
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}