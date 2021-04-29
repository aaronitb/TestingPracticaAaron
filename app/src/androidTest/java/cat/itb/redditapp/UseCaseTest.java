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
import static androidx.test.espresso.contrib.DrawerMatchers.isOpen;
import static androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@RunWith(AndroidJUnit4.class)
public class UseCaseTest {

    String USER_TO_BE_TYPED = "aaron.martinez.7e3@itb.cat";
    String PASS_TO_BE_TYPED = "123456";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void create_and_publish_post() throws InterruptedException {
        //Hacemos login con un usuario para iniciar la app
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(USER_TO_BE_TYPED)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASS_TO_BE_TYPED)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Comprobamos que el login se haya realizado correctamente
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));

        //Utilizo el sleep para que le de tiempo a cargar antes de realizar la siguiente acción, sino crashea
        Thread.sleep(2000);

        //Accedemos a la pestaña correspondiente para crear el post
        onView(withId(R.id.page_3)).perform(click());

        //Seleccionamos en el desplegable qué tipo de post subir
        onView(withId(R.id.text_post)).perform(click());
        Thread.sleep(2000);
        //Seleccionamos una comunidad
        onView(withId(R.id.com_picker)).perform(click());
        ViewInteraction recyclerView = onView(
                allOf(withId(R.id.recycler_view_community),
                        childAtPosition(
                                withClassName(is("androidx.constraintlayout.widget.ConstraintLayout")),
                                1)));
        recyclerView.perform(actionOnItemAtPosition(3, click()));
        Thread.sleep(2000);

        //Introducimos el título del post
        ViewInteraction textInputEditText = onView(withId(R.id.post_title));
        textInputEditText.perform(replaceText("Testing title"), closeSoftKeyboard());

        //Introducimos el contenido del post
        ViewInteraction textInputEditText2 = onView(withId(R.id.post_optional_text));
        textInputEditText2.perform(replaceText("Testing text"), closeSoftKeyboard());
        Thread.sleep(1000);

        //Subimos el post
        ViewInteraction materialTextView2 = onView(withId(R.id.post));
        materialTextView2.perform(click());
    }


    @Test
    public void log_out() throws InterruptedException {
        //Hacemos login con un usuario para iniciar la app
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(USER_TO_BE_TYPED)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASS_TO_BE_TYPED)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Comprobamos que el login se haya realizado correctamente
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));

        //Utilizo el sleep para que le de tiempo a cargar antes de realizar la siguiente acción, sino crashea
        Thread.sleep(1000);

        //Accedemos al navigation drawer donde se encuentra el botón del log out
        ViewInteraction appCompatImageButton = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.top_app_bar),
                                childAtPosition(
                                        withId(R.id.app_bar),
                                        0)),
                        0),
                        isDisplayed()));
        appCompatImageButton.perform(click());

        //Hacemos click en el botón de log out
        onView(withText(R.string.logout)).perform(click());

        //Comprobamos que se ha realizado correctamente
        onView(withId(R.id.fragment_login)).check(matches(isDisplayed()));

    }


    @Test
    public void like_a_post() throws InterruptedException {
        //Hacemos login con un usuario para iniciar la app
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(USER_TO_BE_TYPED)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASS_TO_BE_TYPED)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Comprobamos que el login se haya realizado correctamente
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));

        //Utilizo el sleep para que le de tiempo a cargar antes de realizar la siguiente acción, sino crashea
        Thread.sleep(3000);

        //Pulsamos el botón de like de un post
        ViewInteraction appCompatImageView = onView(
                allOf(withId(R.id.upvote),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.recycler_view),
                                        2),
                                5),
                        isDisplayed()));
        appCompatImageView.perform(click());
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
