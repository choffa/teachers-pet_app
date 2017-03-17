package no.teacherspet.mainapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by magnus on 17.03.2017.
 */

public class CreateOrLogIn extends AppCompatActivity{
    Button createAccount;
    Button login;
    Button back;
    View.OnClickListener handler;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_or_login);
        handler= new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(v.getId()){
                    case(R.id.backbtn):
                        finish();
                        break;
                    case(R.id.loginbtn):
                        Intent login=new Intent(getApplicationContext(),ProfessorLogin.class);
                        startActivity(login);
                        break;
                    case(R.id.createbtn):
                        Intent createAcc=new Intent(getApplicationContext(),CreateAccount.class);
                        startActivity(createAcc);
                }

            }
        };
        setTitle("Do you wish to create a user or login");
        createAccount=(Button) findViewById(R.id.createbtn);
        login=(Button) findViewById(R.id.loginbtn);
        back=(Button) findViewById(R.id.backbtn);
        createAccount.setOnClickListener(handler);
        login.setOnClickListener(handler);
        back.setOnClickListener(handler);
    }
}
