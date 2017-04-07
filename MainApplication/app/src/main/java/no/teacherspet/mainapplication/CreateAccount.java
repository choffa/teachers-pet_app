package no.teacherspet.mainapplication;

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
 * Created by magnus on 14.03.2017.
 */

public class CreateAccount extends AppCompatActivity {
    private Button btn;
    private EditText email;
    private EditText password;
    private EditText password_confirm;
    private Connection c;
    boolean noConnection;
    Thread thread;

    /**
     * Creates the activity and initiates buttons, textfields etc.
     * @param savedInstanceState: The previoud instance of the activity
     */
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account_creation);
        noConnection = false;
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    c = createConnection();
                }
                catch (IOException e){
                    Toast.makeText(CreateAccount.this, "Noe gikk galt under lasting av siden", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
            });
            thread.start();
            setTitle("Create a new account");
            ActionBar actionBar = getSupportActionBar();
            actionBar.setDisplayHomeAsUpEnabled(true);
            btn = (Button) findViewById(R.id.account_createbtn);
            email= (EditText) findViewById(R.id.email);
            email.setText("");
            password= (EditText) findViewById(R.id.password);
            password.setText("");
            password_confirm= (EditText) findViewById(R.id.password_confirmation);
            password_confirm.setText("");
        }

    public Connection createConnection() throws IOException{
        return new Connection();
    }

    /**
     * Method that converts a string to hexadecimals
     * @param data: An array of bytes that will be converted
     * @return The converted bytes as a String
     */
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

    /**
     * Converts a String using the SHA1 hash
     * @param text: String that is to be converted
     * @return a hashed String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    protected static String SHA1(String text) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        byte[] textBytes = text.getBytes("iso-8859-1");
        md.update(textBytes, 0, textBytes.length);
        byte[] sha1hash = md.digest();
        return convertToHex(sha1hash);
    }

    public void onCreateBtnClick(View v){

        try {
            if (password.getText().toString().equals(password_confirm.getText().toString())) {
                if (c.checkUsername(md5(email.getText().toString()))) {
                    Toast.makeText(CreateAccount.this, "This e-mail is already used", Toast.LENGTH_SHORT).show();;
                } else {
                    try {
                        RoleSelect.professors.put(md5(email.getText().toString()), SHA1(password.getText().toString()));
                        c.createUser(md5(email.getText().toString()), SHA1(password.getText().toString()));
                        c.close();
                        finish();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            } else {
                Toast.makeText(CreateAccount.this, "Passwords must match", Toast.LENGTH_SHORT).show();
            }

        }catch (IOException e){
            Toast.makeText(CreateAccount.this,"Noe gikk galt med tilkoblingen til server",Toast.LENGTH_LONG).show();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns you to the previous activity
     * @param item
     * @return
     */
    public boolean onOptionsItemSelected(MenuItem item) {
        try{
            c.close();
            finish();
            return true;
        }
        catch (IOException e){
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Converts a String using the md5 hash
     * @param id: The String to be converted
     * @return a hashed String
     * @throws NoSuchAlgorithmException
     * @throws UnsupportedEncodingException
     */
    protected String md5(String id) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest md=MessageDigest.getInstance("MD5");
        byte[] idBytes=id.getBytes("iso-8859-1");
        md.update(idBytes,0,idBytes.length);
        byte[] md5Hash=md.digest();
        return convertToHex(md5Hash);
    }

    @Override
    public void onDestroy(){
        thread=new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    c.close();
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        super.onDestroy();
    }
}
