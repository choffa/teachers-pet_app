package no.teacherspet.mainapplication;

import android.support.test.espresso.assertion.ViewAssertions;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import frontend.Connection;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

/**
 * Created by magnus on 31.03.2017.
 */
@RunWith(AndroidJUnit4.class)
public class TestCreateAccount{
    @Rule
    public final ActivityTestRule<CreateAccount> main = new ActivityTestRule<>(CreateAccount.class);




    CreateAccount createAcc;
    Connection c;

    @Before
    public void setUp()throws Exception{
        c=mock(Connection.class);
        doReturn(c).when(createAcc.createConnection());
        createAcc=main.getActivity();

    }

    @Test
    public void shouldBeAbleToLaunchMainScreen()throws Exception{
        doReturn(c).when(createAcc.createConnection());
        onView(withId(R.id.email)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.password)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.password_confirmation)).check(ViewAssertions.matches(isDisplayed()));
        onView(withId(R.id.createbtn)).check(ViewAssertions.matches(isDisplayed()));
    }

}
