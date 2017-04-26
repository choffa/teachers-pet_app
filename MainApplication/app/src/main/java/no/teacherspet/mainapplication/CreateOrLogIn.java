package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * Created by magnus on 17.03.2017.
 */

public class CreateOrLogIn extends AppCompatActivity{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_or_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    /**
     * Handles the "Back"-button
     * @param v
     */
    public void backBtnPressed(View v){
        finish();
    }

    /**
     * Handles the "Login"-button
     * @param v
     */
    public void gotoLoginPressed(View v){
        Intent login=new Intent(CreateOrLogIn.this,ProfessorLogin.class);
        startActivity(login);
    }

    /**
     * Handles the "Create Account"-button
     * @param v
     */
    public void createBtnPressed(View v){
        Intent createAcc=new Intent(CreateOrLogIn.this,CreateAccount.class);
        startActivity(createAcc);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }

    @Override
    public void onResume(){
        super.onResume();
        if(RoleSelect.isValidated){
            finish();
        }
    }
}
