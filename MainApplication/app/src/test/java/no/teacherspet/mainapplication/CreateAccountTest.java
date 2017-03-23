package no.teacherspet.mainapplication;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import frontend.Connection;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by magnus on 23.03.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class CreateAccountTest extends ActivityUnitTestCase<CreateAccount>{
    private CreateAccount createAccount;
    private EditText userName;
    private EditText password;
    private EditText passwordConfirm;
    private Button doneButton;

    public CreateAccountTest() {
        super(CreateAccount.class);
    }

    @Before
    public void setUp() throws Exception {
        super.setUp();
        startActivity(new Intent(getInstrumentation().getContext(),CreateAccount.class),null,null);
        createAccount = getActivity();
        userName = (EditText) createAccount.findViewById(no.teacherspet.mainapplication.R.id.email);
        password = (EditText) createAccount.findViewById(no.teacherspet.mainapplication.R.id.password);
        passwordConfirm = (EditText) createAccount.findViewById(no.teacherspet.mainapplication.R.id.password_confirmation);
        doneButton = (Button) createAccount.findViewById(no.teacherspet.mainapplication.R.id.account_createbtn);

    }

    @Test
    public void passwordShouldNotMatch(){
        userName.setText("test");
        password.setText("test");
        passwordConfirm.setText("Test");
        Connection c = mock(Connection.class);
        when(c.checkUsername(anyString())).thenReturn(false);
        doneButton.performClick();
        assertEquals(false,createAccount.isDestroyed());

    }
    @Test
    public void onCreate() throws Exception {

    }

    @Test
    public void SHA1() throws Exception {
        String test1="test";
        String test2="test";
        String wrongInput="Test";
        String random="qwerty";
        CreateAccount cA=new CreateAccount();
        String converted1=cA.SHA1(test1);
        String converted2=cA.SHA1(test2);
        String wrongConverted=cA.SHA1(wrongInput);
        String randomConverted=cA.SHA1(random);

        assertEquals(converted1,converted2);
        assertNotSame(converted1,wrongConverted);
        assertNotSame(converted1,randomConverted);
    }

    @Test
    public void onOptionsItemSelected() throws Exception {
        CreateAccount cA = new CreateAccount();
    }

}