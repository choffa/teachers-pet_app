package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.provider.Settings.Secure;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by magnus on 17.02.2017.
 */


public class RoleSelect extends AppCompatActivity {
    //Page for redirecting to the students' GUI or the Professors' GUI. Might be changed for a login page.
    protected static HashMap<Integer, ArrayList<Integer>> saves=new HashMap<>();
    protected static HashMap<String,String> professors = new HashMap<>();
    protected static String StudentId;
    protected static boolean isValidated;
    protected static String ProfessorID="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isValidated=false;
        setContentView(R.layout.activity_select_role);
        Button StudBtn= (Button) findViewById(R.id.studBtn);
        Button ProfBtn= (Button) findViewById(R.id.profBtn);
        StudBtn.setOnClickListener(handler);
        ProfBtn.setOnClickListener(handler);
        try {
            StudentId = md5(Secure.getString(getContentResolver(),Secure.ANDROID_ID));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Toast.makeText(getApplicationContext(),StudentId,Toast.LENGTH_LONG).show();

        //Allows threads
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    /**
     * Sends to the ProfessorLive view
     */
    public void selectProfessor(){
        if(isValidated){
            Intent showProfessorLectures=new Intent(getApplicationContext(),ProfessorLectureList.class);
            startActivity(showProfessorLectures);
        }
        else{
            Intent loginScreen = new Intent(RoleSelect.this,CreateOrLogIn.class);
            startActivity(loginScreen);
        }
    }

    /**
     * Sends to the StudentRating view
     */
    public void selectStudent(){

        Intent intent= new Intent(RoleSelect.this,StudentLectureList.class);
        startActivity(intent);
    }

    /**
     * Checks weither the user clicks the professor or the student button.
     */
    View.OnClickListener handler= new OnClickListener() {
        @Override
        public void onClick(View v) {
            switch(v.getId()){
                case R.id.studBtn:

                    selectStudent();
                    break;
                case R.id.profBtn:

                    selectProfessor();
                    break;
            }
        }
    };

    private String md5(String id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] idBytes=id.getBytes("iso-8859-1");
        md.update(idBytes,0,idBytes.length);
        byte[] md5Hash=md.digest();
        return convertToHex(md5Hash);
    }

    private static String convertToHex(byte[] data) {
        StringBuilder buf = new StringBuilder();
        for (byte b : data) {
            int halfbyte = (b >>> 4) & 0x0F;
            int two_halfs = 0;
            do {
                buf.append((0 <= halfbyte) && (halfbyte <= 9) ? (char) ('0' + halfbyte) : (char) ('a' + (halfbyte - 10)));
                halfbyte = b & 0x0F;
            } while (two_halfs++ < 1);
        }
        return buf.toString();
    }

    public void logOut(View view) {
        isValidated = false;
        ProfessorID="";
        Toast.makeText(getApplicationContext(),"You are now logged out",Toast.LENGTH_LONG).show();
    }
}