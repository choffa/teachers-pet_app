package no.teacherspet.mainapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.EditText;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static java.util.UUID.randomUUID;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by magnus on 31.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestCreateAccount{
    @Rule
    public final ActivityTestRule<CreateAccount> main = new ActivityTestRule<>(CreateAccount.class);

    CreateAccount createAcc;
    Instrumentation.ActivityMonitor timeMonitor=getInstrumentation().addMonitor(ProfessorLectureList.class.getName(),null,false);;

    @Before
    public void setUp()throws Exception{
        createAcc=main.getActivity();
    }

    @Test
    public void shouldBeAbleToLaunchMainScreen()throws Exception{
        onView(withId(R.id.email)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.password)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.password_confirmation)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.account_createbtn)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void shouldNotCreateUser(){
        createAcc.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText userName= (EditText) createAcc.findViewById(R.id.email);
                userName.setText("testing");
                EditText password= (EditText) createAcc.findViewById(R.id.password);
                password.setText("test");
                EditText passwordConfirm= (EditText) createAcc.findViewById(R.id.password_confirmation);
                passwordConfirm.setText("TEST");
            }
        });
        onView(withId(R.id.account_createbtn)).perform(click());
        onView(withId(R.id.account_createbtn)).check(ViewAssertions.matches(isDisplayed()));
        createAcc.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText userName= (EditText) createAcc.findViewById(R.id.email);
                userName.setText("a");
                EditText password= (EditText) createAcc.findViewById(R.id.password);
                password.setText("b");
                EditText passwordConfirm= (EditText) createAcc.findViewById(R.id.password_confirmation);
                passwordConfirm.setText("b");
            }
        });
        onView(withId(R.id.account_createbtn)).perform(click());
        onView(withId(R.id.account_createbtn)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void shouldCreateNewUser(){
        createAcc.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                EditText userName= (EditText) createAcc.findViewById(R.id.email);
                userName.setText(randomUUID().toString());
                EditText password= (EditText) createAcc.findViewById(R.id.password);
                password.setText("b");
                EditText passwordConfirm= (EditText) createAcc.findViewById(R.id.password_confirmation);
                passwordConfirm.setText("b");
            }
        });
        onView(withId(R.id.account_createbtn)).perform(click());
        Activity loggedIn=getInstrumentation().waitForMonitorWithTimeout(timeMonitor,5000);
        assertNotNull(loggedIn);
    }
}
