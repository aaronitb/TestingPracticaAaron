package cat.itb.redditapp;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static org.hamcrest.Matchers.allOf;


@RunWith(AndroidJUnit4.class)
public class RecyclerViewTest {

    String LOGIN_USER = "aaron.martinez.7e3@itb.cat";
    String PASSWORD = "123456";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void click_recycler_item() throws InterruptedException {
        //Hacemos login con un usuario para iniciar la app
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(LOGIN_USER)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASSWORD)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Utilizo el sleep para que le de tiempo a cargar antes de realizar la siguiente acción, sino crashea
        Thread.sleep(3000);

        //Hacemos click en un elemento del recyclerView (en este caso para acceder a poner un comentario de un post)
        ViewInteraction appCompatImageView2 = onView(
                allOf(withId(R.id.imageView3),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        2),
                                8),
                        isDisplayed()));
        appCompatImageView2.perform(click());

        //Comprobamos que hemos accedido al nuevo fragment mediante el recycler
        onView(withId(R.id.fragment_comment)).check(matches(isDisplayed()));
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
