package cat.itb.redditapp;


import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.runner.AndroidJUnit4;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void mainActivityTest() {
        ViewInteraction appCompatEditText = onView(
                allOf(withId(R.id.editTextTextPersonName),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText.perform(replaceText("aaron.martinez.7e3@"), closeSoftKeyboard());

        ViewInteraction appCompatEditText2 = onView(
                allOf(withId(R.id.editTextTextPersonName), withText("aaron.martinez.7e3@"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText2.perform(click());

        ViewInteraction appCompatEditText3 = onView(
                allOf(withId(R.id.editTextTextPersonName), withText("aaron.martinez.7e3@"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText3.perform(replaceText("aaron.martinez.7e3@itb.cat"));

        ViewInteraction appCompatEditText4 = onView(
                allOf(withId(R.id.editTextTextPersonName), withText("aaron.martinez.7e3@itb.cat"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                5),
                        isDisplayed()));
        appCompatEditText4.perform(closeSoftKeyboard());

        ViewInteraction appCompatEditText5 = onView(
                allOf(withId(R.id.editTextTextPassword),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                6),
                        isDisplayed()));
        appCompatEditText5.perform(replaceText("123456"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.button3), withText("Continue"),
                        childAtPosition(
                                allOf(withId(R.id.fragment_login),
                                        childAtPosition(
                                                withId(R.id.fragment_container),
                                                0)),
                                10),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction bottomNavigationItemView = onView(
                allOf(withId(R.id.page_3),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.bottom_navigation),
                                        0),
                                2),
                        isDisplayed()));
        bottomNavigationItemView.perform(click());

        ViewInteraction navigationMenuItemView = onView(
                allOf(withId(R.id.text_post),
                        childAtPosition(
                                allOf(withId(R.id.design_navigation_view),
                                        childAtPosition(
                                                withId(R.id.bottom_drawer),
                                                0)),
                                1),
                        isDisplayed()));
        navigationMenuItemView.perform(click());

        ViewInteraction materialTextView = onView(
                allOf(withId(R.id.com_picker), withText("Choose a community"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.fragment_container),
                                        0),
                                4),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_community),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(3, click()));

        ViewInteraction textInputEditText = onView(
                allOf(withId(R.id.post_title2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textField),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText.perform(replaceText("hola"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withId(R.id.post_optional_text2),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.textField2),
                                        0),
                                0),
                        isDisplayed()));
        textInputEditText2.perform(replaceText("qqtal"), closeSoftKeyboard());

        ViewInteraction materialTextView2 = onView(
                allOf(withId(R.id.post), withText("Post"),
                        childAtPosition(
                                allOf(withId(R.id.top_app_bar_community),
                                        childAtPosition(
                                                withId(R.id.app_bar),
                                                0)),
                                1),
                        isDisplayed()));
        materialTextView2.perform(click());
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
