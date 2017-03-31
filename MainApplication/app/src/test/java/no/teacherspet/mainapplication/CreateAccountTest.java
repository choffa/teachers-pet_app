package no.teacherspet.mainapplication;

import android.content.Context;
import android.test.ActivityUnitTestCase;
import android.text.Editable;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mock;

import frontend.Connection;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


/**
 * Created by magnus on 23.03.2017.
 */
@RunWith(JUnit4.class)
public class CreateAccountTest extends ActivityUnitTestCase<CreateAccount>{
    private CreateAccount createAccount;
    @Mock
    Context mContext=mock(Context.class);
    @Mock
    Toast mToast;
    @Mock
    private Button doneButton = mock(Button.class);
    @Mock
    private EditText email=mock(EditText.class);
    @Mock
    private EditText password=mock(EditText.class);
    @Mock EditText password_confirm=mock(EditText.class);
    public CreateAccountTest() {
        super(CreateAccount.class);
    }
    @Mock
    Connection c = mock(Connection.class);
    @Before
    public void setUp() throws Exception {
        mToast=mock(Toast.class);
        createAccount=new CreateAccount();
        createAccount.email= this.email;
        createAccount.password= this.password;
        createAccount.password_confirm= this.password_confirm;
        createAccount.c=this.c;
    }

    @Test
    public void passwordShouldNotMatch(){
        when(c.checkUsername(anyString())).thenReturn(false);
        Editable e = mock(Editable.class);
        when(e.toString()).thenReturn("test");
        Editable otherE = mock(Editable.class);
        when(otherE.toString()).thenReturn("Somethingelse");
        when(email.getText()).thenReturn(e);
        when(password.getText()).thenReturn(e);
        when(password_confirm.getText()).thenReturn(otherE);
        createAccount.onCreateBtnClick();
        assertEquals(false,createAccount.isDone);
    }
    @Test
    public void passwordMatch(){
        when(c.checkUsername(anyString())).thenReturn(false);
        Editable e = mock(Editable.class);
        when(e.toString()).thenReturn("test");
        when(email.getText()).thenReturn(e);
        when(password.getText()).thenReturn(e);
        when(password_confirm.getText()).thenReturn(e);
        createAccount.onCreateBtnClick();
        assertEquals(true,createAccount.isDone);
    }
    @Test
    public void usernameTaken(){
        when(c.checkUsername(anyString())).thenReturn(true);
        doneButton.performClick();
        assertEquals(false,createAccount.isDone);
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
    public void md5() throws Exception{
        String test1="test";
        String test2="test";
        String wrongInput="Test";
        String random="qwerty";
        CreateAccount cA=new CreateAccount();
        String converted1=cA.md5(test1);
        String converted2=cA.md5(test2);
        String wrongConverted=cA.md5(wrongInput);
        String randomConverted=cA.md5(random);

        assertEquals(converted1,converted2);
        assertNotSame(converted1,wrongConverted);
        assertNotSame(converted1,randomConverted);
    }
    @Test
    public void onOptionsItemSelected() throws Exception {
        CreateAccount cA = new CreateAccount();
    }

}