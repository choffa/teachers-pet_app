package no.teacherspet.mainapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * Created by magnus on 16.04.2017.
 */

@RunWith(AndroidJUnit4.class)
public class TestCreateOrLogin {
    @Rule
    public final ActivityTestRule<CreateOrLogIn> main = new ActivityTestRule<>(CreateOrLogIn.class);

    private CreateOrLogIn activity;
    Instrumentation.ActivityMonitor timeMonitor;

    @Before
    public void setUp(){
        activity=main.getActivity();
    }

    @Test
    public void shouldBeAbleToLaunchMainScreen(){
        onView(withId(R.id.loginbtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.createbtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.backbtn)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void shouldGoToLoginScreen(){
        timeMonitor=getInstrumentation().addMonitor(ProfessorLogin.class.getName(),null,false);
        onView(withId(R.id.loginbtn)).perform(click());
        Activity ds=getInstrumentation().waitForMonitorWithTimeout(timeMonitor,5000);
        assertThat(ds,instanceOf(ProfessorLogin.class));
    }

    @Test
    public void shouldGoToCreateAccountScreen(){
        timeMonitor=getInstrumentation().addMonitor(CreateAccount.class.getName(),null,false);
        onView(withId(R.id.createbtn)).perform(click());
        Activity ds=getInstrumentation().waitForMonitorWithTimeout(timeMonitor,5000);
        assertThat(ds,instanceOf(CreateAccount.class));
    }

    @Test
    public void shouldExitActivity(){
        Instrumentation.ActivityMonitor timeMonitor=getInstrumentation().addMonitor(RoleSelect.class.getName(),null,false);
        onView(withId(R.id.backbtn)).perform(click());
        Activity shouldBeNull=getInstrumentation().waitForMonitorWithTimeout(timeMonitor,2000);
        assertNull(shouldBeNull);
    }
}
