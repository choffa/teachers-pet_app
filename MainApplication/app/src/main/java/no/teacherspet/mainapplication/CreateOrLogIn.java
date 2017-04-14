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
    @Override
    public void onResume(){
        super.onResume();
        if(RoleSelect.isValidated){
            finish();
        }
    }

    public void backBtnPressed(View v){
        finish();
    }

    public void gotoLoginPressed(View v){
        Intent login=new Intent(CreateOrLogIn.this,ProfessorLogin.class);
        startActivity(login);
    }

    public void createBtnPressed(View v){
        Intent createAcc=new Intent(CreateOrLogIn.this,CreateAccount.class);
        startActivity(createAcc);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}
