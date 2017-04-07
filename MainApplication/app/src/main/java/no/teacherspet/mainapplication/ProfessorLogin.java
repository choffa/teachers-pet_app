package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import frontend.Connection;

/**
 * Created by magnus on 17.03.2017.
 */

public class ProfessorLogin extends AppCompatActivity{
    EditText UserName;
    EditText Password;
    Button loginBtn;
    private Connection c;
    boolean noConnection;
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.professor_login);
            noConnection = false;
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            c = new Connection();
            UserName = (EditText) findViewById(R.id.login_name);
            Password = (EditText) findViewById(R.id.login_password);
            loginBtn = (Button) findViewById(R.id.loginbtn);
            loginBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (c.checkUsername(md5(UserName.getText().toString().trim()))) {
                            if (c.validateUser(md5(UserName.getText().toString().trim()),SHA1(Password.getText().toString()))) {
                                RoleSelect.isValidated = true;
                                RoleSelect.ProfessorID=md5(UserName.getText().toString().trim());
                                Intent showLectures = new Intent(getApplicationContext(), ProfessorLectureList.class);
                                finish();
                                startActivity(showLectures);
                            } else {
                                Toast.makeText(getApplicationContext(), "The password is wrong", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "There does not exist any registered users with this username", Toast.LENGTH_LONG).show();
                        }
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (IOException e){
            Toast.makeText(getApplicationContext(),"Noe gikk galt under lasting av siden",Toast.LENGTH_LONG).show();
            noConnection = true;
            finish();
        }
    }

    private String md5(String id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] idBytes=id.getBytes("iso-8859-1");
        md.update(idBytes,0,idBytes.length);
        byte[] md5Hash=md.digest();
        return convertToHex(md5Hash);
    }
    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
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
    public boolean onOptionsItemSelected(MenuItem item) {
        try {
            c.close();
            finish();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    @Override
    public void onDestroy(){
        try {
            if(!noConnection) {
                c.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
