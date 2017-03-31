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

    Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(StudentLectureList.class.getName(),null,false);
    RoleSelect rs;
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
    public void shouldGoToStudentLectureList(){
        onView(withText(R.string.i_am_a_student)).perform(click());
        Activity lectureList=getInstrumentation().waitForMonitorWithTimeout(monitor,5000);
        assertNotNull(lectureList);
        lectureList.finish();
    }

}
