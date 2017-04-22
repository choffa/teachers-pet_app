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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static junit.framework.Assert.assertNotNull;

/**
 * Created by magnus on 31.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestRoleSelect{
    @Rule
    public final ActivityTestRule<RoleSelect> main = new ActivityTestRule<>(RoleSelect.class);

    private Instrumentation.ActivityMonitor studentMonitor = getInstrumentation().addMonitor(StudentLectureList.class.getName(),null,false);
    private Instrumentation.ActivityMonitor professorMonitor = getInstrumentation().addMonitor(ProfessorLectureList.class.getName(),null,false);
    private Instrumentation.ActivityMonitor loginMonitor = getInstrumentation().addMonitor(CreateOrLogIn.class.getName(),null,false);
    private RoleSelect rs;
    @Before
    public void setUp(){
        rs=main.getActivity();
    }
    @Test
    public void shouldBeAbleToLaunchMainScreen(){
        onView(withId(R.id.profBtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.studBtn)).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void shouldGoToStudentLectureList()throws Exception{
        onView(withText(R.string.i_am_a_student)).perform(click());
        Activity lectureList=getInstrumentation().waitForMonitorWithTimeout(studentMonitor,5000);
        assertNotNull(lectureList);
        lectureList.finish();
    }
    @Test
    public void shouldGoToLogin(){
        RoleSelect.isValidated=false;
        onView(withId(R.id.profBtn)).perform(click());
        Activity login=getInstrumentation().waitForMonitorWithTimeout(loginMonitor,5000);
        assertNotNull(login);
        login.finish();
    }
    @Test
    public void shouldGoToProfessorLectures() throws Exception{
        RoleSelect.isValidated=true;
        RoleSelect.ProfessorID="TestProfessor";
        onView(withId(R.id.profBtn)).perform(click());
        Activity prof=getInstrumentation().waitForMonitorWithTimeout(professorMonitor,5000);
        assertNotNull(prof);
        prof.finish();
    }

}
