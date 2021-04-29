package cat.itb.redditapp;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class LoginRegisterTest {

    String USERNAME = "test";
    String LOGIN_USER = "aaron.martinez.7e3@itb.cat";
    String REGISTER_USER = "testing2.registro@itb.cat";
    String PASSWORD = "123456";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void login(){
        //Hacemos login con un usuario para iniciar la app
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(LOGIN_USER)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASSWORD)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Comprobamos que el login se haya realizado correctamente
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void register(){
        //Accedemos a la pestaña de registro
        onView(withId(R.id.textViewSignUp)).perform(click());

        //Rellenamos los campos necesarios
        onView(withId(R.id.editTextTextEmail)).perform(typeText(REGISTER_USER)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPersonName2)).perform(typeText(USERNAME)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword2)).perform(typeText(PASSWORD)).perform(closeSoftKeyboard());

        //Activamos la checkbox de los terminos y condiciones
        onView(withId(R.id.checkBox)).perform(click());

        //Completamos el registro
        onView(withId(R.id.button6)).perform(click());

        //Comprobamos que el registro se haya realizado correctamente
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));

    }
}
