package no.teacherspet.mainapplication;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import backend.Lecture;

/**
 * Created by magnus on 14.03.2017.
 */

public class CreateAccount extends AppCompatActivity {
    private Button btn;
    private EditText email;
    private EditText password;
    private EditText password_confirm;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);
        setTitle("Create your vey own Teacher's Pet account");
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        btn = (Button) findViewById(R.id.account_createbtn);
        email= (EditText) findViewById(R.id.email);
        email.setText("");
        password= (EditText) findViewById(R.id.password);
        password.setText("");
        password_confirm= (EditText) findViewById(R.id.password_confirmation);
        password_confirm.setText("");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.getText().toString().equals(password_confirm.getText().toString())) {
                    if(RoleSelect.professors.containsKey(email.getText().toString())){
                        Toast.makeText(getApplicationContext(),"This e-mail is already used",Toast.LENGTH_LONG).show();
                    }
                    else {
                        try {
                            RoleSelect.professors.put(email.getText().toString(), SHA1(password.getText().toString()));
                            Toast.makeText(getApplicationContext(),SHA1(password.getText().toString()),Toast.LENGTH_LONG).show();
                            finish();
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(),"Passwords must match",Toast.LENGTH_LONG).show();
                }
            }
        });
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

    public static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
