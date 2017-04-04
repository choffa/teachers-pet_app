package no.teacherspet.mainapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.widget.TimePicker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;

/**
 * Created by magnus on 03.04.2017.
 */

public class TestTimeSetter {

    @Rule
    public final ActivityTestRule<CreateLecture> main = new ActivityTestRule<>(CreateLecture.class);

    private Activity ts;
    private CreateLecture cl;

    @Before
    public void setUp() {
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                cl=main.getActivity();
            }
        });
        Instrumentation.ActivityMonitor timeMonitor = getInstrumentation().addMonitor(TimeSetter.class.getName(),null,false);
        onView(withId(R.id.startbtn)).perform(click());
        ts = getInstrumentation().waitForMonitorWithTimeout(timeMonitor,5000);
    }

    @Test
    public void shouldBeAbleToLaunchScreen(){
        assertThat(ts,instanceOf(TimeSetter.class));
        onView(withId(R.id.timePicker)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.okbtn)).check(ViewAssertions.matches(isDisplayed()));
    }
    @Test
    public void shouldSaveCurrentTimeOnClick(){
        assertThat(ts,instanceOf(TimeSetter.class));
        ts.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimePicker tp = (TimePicker) ts.findViewById(R.id.timePicker);
                tp.setCurrentHour(10);
                tp.setCurrentMinute(30);
            }

        });
        onView(withId(R.id.okbtn)).perform(click());

    }
}
