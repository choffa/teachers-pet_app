package no.teacherspet.mainapplication;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import frontend.Connection;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

/**
 * Created by magnus on 03.04.2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestCreateLecture{

    @Rule
    public final ActivityTestRule<CreateLecture> main = new ActivityTestRule<>(CreateLecture.class);

    @Mock
    private static Connection c;
    private CreateLecture cl;
    Instrumentation.ActivityMonitor timeMonitor;

    @Before
    public void setUp() throws Exception{
        cl=main.getActivity();
    }

    @Test
    public void shouldBeAbleToLaunchScreen(){
        onView(withId(R.id.lecture_header)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.nametxt)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.room_header)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.roomtxt)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.datebtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.startbtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.endbtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.subjectBtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.donebtn)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.cancelbtn)).check(ViewAssertions.matches(isDisplayed()));
    }

    @Test
    public void shouldSaveCurrentDateOnClick(){
        timeMonitor = getInstrumentation().addMonitor(DateSetter.class.getName(),null,false);
        onView(withId(R.id.datebtn)).perform(click());
        final Activity ds=getInstrumentation().waitForMonitorWithTimeout(timeMonitor,5000);
        assertThat(ds,instanceOf(DateSetter.class));
        onView(withId(R.id.datePicker)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.confirmDateBtn)).check(ViewAssertions.matches(isDisplayed()));
        ds.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                DatePicker dp= (DatePicker) ds.findViewById(R.id.datePicker);
                dp.updateDate(2017,3,27);
            }
        });
        onView(withId(R.id.confirmDateBtn)).perform(click());
        assertEquals("Date of lecture: Thursday 27 Apr 2017",((Button) cl.findViewById(R.id.datebtn)).getText().toString());
    }

    @Test
    public void shouldSaveCurrentStartTimeOnClick(){
        timeMonitor = getInstrumentation().addMonitor(TimeSetter.class.getName(),null,false);
        onView(withId(R.id.startbtn)).perform(click());
        final Activity tsStart = getInstrumentation().waitForMonitorWithTimeout(timeMonitor,5000);
        assertThat(tsStart,instanceOf(TimeSetter.class));
        onView(withId(R.id.timePicker)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.okbtn)).check(ViewAssertions.matches(isDisplayed()));

        tsStart.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimePicker tp = (TimePicker) tsStart.findViewById(R.id.timePicker);
                tp.setCurrentHour(10);
                tp.setCurrentMinute(30);
            }

        });
        onView(withId(R.id.okbtn)).perform(click());
        assertEquals("Start Time: 10",((Button) cl.findViewById(R.id.startbtn)).getText().toString());
    }

    @Test
    public void shouldSaveCurrentEndTimeOnClick(){
        timeMonitor = getInstrumentation().addMonitor(TimeSetter.class.getName(),null,false);
        onView(withId(R.id.endbtn)).perform(click());
        final Activity ts = getInstrumentation().waitForMonitorWithTimeout(timeMonitor,5000);
        assertThat(ts,instanceOf(TimeSetter.class));
        ts.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                TimePicker tp = (TimePicker) ts.findViewById(R.id.timePicker);
                tp.setCurrentHour(15);
                tp.setCurrentMinute(45);
            }

        });
        onView(withId(R.id.okbtn)).perform(click());
        assertEquals("End Time: 15",((Button) cl.findViewById(R.id.endbtn)).getText().toString());
    }
}
