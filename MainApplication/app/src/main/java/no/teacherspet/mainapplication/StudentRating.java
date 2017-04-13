package no.teacherspet.mainapplication;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import backend.Lecture;
import frontend.Connection;
import frontend.Subject;

public class StudentRating extends AppCompatActivity{

    byte rating;
    int radioButtonID;
    private static int lectureID;
    static Subject currentSub;
    RadioGroup tempo;
    ArrayList<Subject> subjects;
    ArrayList<String> subjectNames= new ArrayList<>();
    ArrayList<String> subjectComments = new ArrayList<>();
    ArrayList<Integer> subjectIDs = new ArrayList<>();
    private Connection c;
    HashMap<String,ArrayList<Integer>> savedLectures=new HashMap<>();
    TextView hello; //Textfield only for debugging purposes: shows the last two values
    ListView ratingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            lectureID = StudentLectureList.getID();
            c = new Connection();
            subjects = c.getSubjects(StudentLectureList.getID());
            ArrayList list = new ArrayList();
            for (Subject s:subjects) {
                subjectNames.add(s.getName());
                subjectComments.add(s.getComment());
                subjectIDs.add(s.getId());
                list.add(new RowModel(s));
            }
            setContentView(R.layout.activity_student_rating);
            ratingList = (ListView) findViewById(android.R.id.list);

            ratingList.setAdapter(new RatingAdapter(list));
            RelativeLayout.LayoutParams mParam = (RelativeLayout.LayoutParams) ratingList.getLayoutParams();
            mParam.height = (calculateHeight(ratingList));
            ratingList.setLayoutParams(mParam);
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle(StudentLectureList.getName());
            tempo = (RadioGroup) findViewById(R.id.tempoRadioGroup);
            if (RoleSelect.saves.containsKey(StudentLectureList.getID())) {
                tempo.check(RoleSelect.saves.get(lectureID).get(0));
            }
            hello = (TextView) findViewById(R.id.textView2);
            //Sets the radiobuttons to send the new info to the server on every click.
            tempo.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {



            @Override
            public void onCheckedChanged(RadioGroup tempo, int checkedId) {
                if(!isValidTime()){
                    Toast.makeText(getApplicationContext(),"This lecture is not active",Toast.LENGTH_LONG).show();
                    finish();

                    } else {
                        ArrayList<Integer> changes = new ArrayList<Integer>();
                        radioButtonID = tempo.getCheckedRadioButtonId();
                        changes.add(radioButtonID);
                        //RoleSelect.getStud().setButton(radioButtonID);
                        View radioButton = tempo.findViewById(radioButtonID);
                        int rating = tempo.indexOfChild(radioButton) + 1;
                        c.sendSpeedRating(StudentLectureList.getID(),RoleSelect.StudentId,rating);
                        changes.add(rating);
                        if (RoleSelect.saves.containsKey(StudentLectureList.getID())) {
                            changes.add(RoleSelect.saves.get(StudentLectureList.getID()).get(1));
                        } else {
                            changes.add(0);
                        }

                        //RoleSelect.changeStud((byte)rating);
                        if (RoleSelect.saves.containsKey(StudentLectureList.getID())) {
                            RoleSelect.saves.remove(StudentLectureList.getID());
                        }
                        RoleSelect.saves.put(lectureID, changes);
                        Toast.makeText(StudentRating.this, "Rating sent", Toast.LENGTH_SHORT).show();
                        hello.setText(Integer.toString(RoleSelect.saves.get(lectureID).get(1)) + " , " + Integer.toString(RoleSelect.saves.get(lectureID).get(2)));
                    }
                }
            });
        }catch(IOException e){
            Toast.makeText(getApplicationContext(),"Error occured while loading page",Toast.LENGTH_LONG).show();
            finish();
        }
    }


    private RowModel getModel(int position) {
        return (RowModel) ((RatingAdapter) ratingList.getAdapter()).getItem(position);
    }

    public void sendComment(View view) {
    }

    class RatingAdapter extends ArrayAdapter {
        RatingAdapter(ArrayList list) {
            super(StudentRating.this, R.layout.student_rating_row, list);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
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
                RatingBar.OnRatingBarChangeListener l =
                        new RatingBar.OnRatingBarChangeListener() {
                            public void onRatingChanged(RatingBar ratingBar,
                                                        float rating, boolean fromTouch) {
                                Integer myPosition = (Integer) ratingBar.getTag();
                                RowModel model = getModel(myPosition);
                                model.rating = rating;
                                currentSub = subjects.get(myPosition);
                                c.sendSubjectRating(currentSub.getId(), RoleSelect.StudentId, Math.round(rating), "");
                                Toast.makeText(StudentRating.this, "You have rated " + currentSub.getName() + " a " + Math.round(rating) + "/5", Toast.LENGTH_SHORT).show();
                            }
                        };
                rate.setOnRatingBarChangeListener(l);
                showCommentsFAB = wrapper.getCommentFAB();
                String currentComment = subjectComments.get(position);
                if(currentComment.isEmpty()||currentComment.equals("NULL")||currentComment==null) {
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
            int str = row.getHeight();
            wrapper.getLabel().setText(model.toString());
            rate.setTag(new Integer(position));
            rate.setRating(model.rating);
            return (row);
        }
    }

    class RowModel {
        String subjectName;
        float rating = 3.0f;
        String comment;

        RowModel(Subject subject) {
            this.subjectName = subject.getName();
            this.comment = subject.getComment();
            //TODO: this.rating = c.getStudentSubjectRating(RoleSelect.studentID,subject.getID)
        }

        public String toString() {

            return (subjectName);
        }
    }

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
            }else{
                if(lectureDate.getMonth()<now.getMonth()||lectureDate.getMonth()>now.getMonth()){
                    return false;
                }else{
                    if(lectureDate.getDate()<now.getDate()||lectureDate.getDate()>now.getDate()){
                        return false;
                    }else{
                        if((end==now.getHours()&&now.getMinutes()>30)||end<now.getHours()||start>now.getHours()){
                            return false;
                        }else{
                            return true;
                        }
                    }
                }
            }
    }



    public boolean onOptionsItemSelected(MenuItem item){
        /* Unnecessary as it creates a new instance of the RoleSelect page
        Intent myIntent = new Intent(getApplicationContext(), RoleSelect.class);
        startActivityForResult(myIntent, 0); */
        try {
            c.close();
            finish();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;

    }

    @Override
    public void onDestroy(){
        try {
            c.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
