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
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;


@RunWith(AndroidJUnit4.class)
public class NavigationTest {

    String LOGIN_USER = "aaron.martinez.7e3@itb.cat";
    String PASSWORD = "123456";

    @Rule
    public ActivityScenarioRule<MainActivity> mActivityTestRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void go_to_register_screen(){
        //Accedemos a la pestaña de registro
        onView(withId(R.id.textViewSignUp)).perform(click());

        //Comprobamos que hemos accedido
        onView(withId(R.id.fragment_registro)).check(matches(isDisplayed()));
    }

    @Test
    public void go_to_login_screen(){
        //Cambiamos de pestaña ya que de por sí empezamos en este fragmento
        onView(withId(R.id.textViewSignUp)).perform(click());

        //Volvemos a la login screen
        onView(withId(R.id.textViewLog)).perform(click());

        //Comprobamos que hemos accedido
        onView(withId(R.id.fragment_login)).check(matches(isDisplayed()));
    }

    @Test
    public void go_to_main_activity(){
        //Nos logueamos ya que es obligatorio para acceder al main activity
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(LOGIN_USER)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASSWORD)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Comprobamos que estamos en el main activity
        onView(withId(R.id.drawer_layout)).check(matches(isDisplayed()));
    }

    @Test
    public void go_to_inbox_fragment() throws InterruptedException {
        //Nos logueamos ya que es obligatorio
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(LOGIN_USER)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASSWORD)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Utilizo el sleep para que le de tiempo a cargar antes de realizar la siguiente acción, sino crashea
        Thread.sleep(1000);

        //Accedemos al fragment del correo
        onView(withId(R.id.page_5)).perform(click());

        //Comprobamos que estamos en el fragment del correo
        onView(withId(R.id.fragment_inbox)).check(matches(isDisplayed()));
    }


    @Test
    public void go_to_chat_fragment() throws InterruptedException {
        //Nos logueamos ya que es obligatorio
        onView(withId(R.id.editTextTextPersonName)).perform(replaceText(LOGIN_USER)).perform(closeSoftKeyboard());
        onView(withId(R.id.editTextTextPassword)).perform(replaceText(PASSWORD)).perform(closeSoftKeyboard());
        onView(withId(R.id.button3)).perform(click());

        //Utilizo el sleep para que le de tiempo a cargar antes de realizar la siguiente acción, sino crashea
        Thread.sleep(1000);

        //Accedemos al fragment de los chats
        onView(withId(R.id.page_4)).perform(click());

        //Comprobamos que estamos en el fragment de los chats
        onView(withId(R.id.fragment_chat)).check(matches(isDisplayed()));
    }
}
