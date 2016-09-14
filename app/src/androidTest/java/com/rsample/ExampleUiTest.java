package com.rsample;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.rsample.activity.MainActivity;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static org.junit.Assert.assertTrue;


/**
 * Created by Tosin Onikute on 9/1/16.
 */

@RunWith(AndroidJUnit4.class)
@LargeTest
public class ExampleUiTest {

    public static final String FIRST_NAME = "james";
    public static final String LAST_NAME = "blunde";
    public static final String EMAIL = "name@email.com";


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @Test
    public void validateEmail_Test() {
        // Type text and then press the button.
        onView(withId(R.id.name)).perform(typeText(FIRST_NAME));
        onView(withId(R.id.lastname)).perform(typeText(LAST_NAME));
        onView(withId(R.id.email)).perform(typeText(EMAIL), closeSoftKeyboard());

        onView(withId(R.id.savecontact)).perform(click());

        // Check that the email was valid.
        //onView(withId(R.id.textToBeChanged)).check(matches(withText(STRING_TO_BE_TYPED)));
        assertTrue(EmailValidator.isValidEmail(EMAIL));
    }

}
