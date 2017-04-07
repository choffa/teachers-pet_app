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
import static android.support.test.espresso.matcher.ViewMatchers.withText;
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
        createAcc=main.getActivity();

    }

    @Test
    public void shouldBeAbleToLaunchMainScreen()throws Exception{
        onView(withText(R.string.username)).check(ViewAssertions.matches(isDisplayed()));
        onView(withText(R.string.type_password_here)).check(ViewAssertions.matches(isDisplayed()));
        onView(withText(R.string.confirm_password)).check(ViewAssertions.matches(isDisplayed()));
        onView(withText(R.string.create_my_account)).check(ViewAssertions.matches(isDisplayed()));
    }

}
