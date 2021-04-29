package cat.itb.redditapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import cat.itb.redditapp.adapter.ViewPagerAdapter;
import cat.itb.redditapp.fragments.CardFragment;
import cat.itb.redditapp.fragments.ChatFragment;
import cat.itb.redditapp.fragments.CompactCardFragment;
import cat.itb.redditapp.fragments.HelperFragment;
import cat.itb.redditapp.fragments.InboxFragment;
import cat.itb.redditapp.fragments.PostFragment;
import cat.itb.redditapp.fragments.LoginFragment;
import cat.itb.redditapp.helper.DatabaseHelper;
import cat.itb.redditapp.model.Community;


public class MainActivity extends AppCompatActivity {

    private static TabLayout tabLayout;
    private static ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private DrawerLayout drawerLayout;
    private LoginFragment loginFragment = new LoginFragment();
    private ChatFragment chatFragment = new ChatFragment();
    private InboxFragment inboxFragment = new InboxFragment();
    private HelperFragment lejosFragment = new HelperFragment();
    private PostFragment postFragment = new PostFragment();
    public static Fragment currentFragment;
    private static MaterialToolbar topAppBar;
    private static AppBarLayout appBarLayout;
    private static BottomNavigationView bottomNavigationView;
    private static NavigationView bottomDrawer;
    private static FrameLayout scrim;
    private static BottomSheetBehavior<NavigationView> bottomSheetBehavior;
    private Button cerrarSesion;
    


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_drawer);


        tabLayout = (TabLayout) findViewById(R.id.tablayout_id);
        viewPager = (ViewPager) findViewById(R.id.viewpager_id);

        cerrarSesion = findViewById(R.id.footer_item_2);
        cerrarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper.mAuth.signOut();
                loginHide();
                Fragment fragment = new LoginFragment();
                FragmentTransaction transaction = MainActivity.this.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });

        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        topAppBar = findViewById(R.id.top_app_bar);
        drawerLayout =findViewById(R.id.drawer_layout);
        bottomDrawer = findViewById(R.id.bottom_drawer);
        bottomSheetBehavior = BottomSheetBehavior.from(bottomDrawer);
        scrim = findViewById(R.id.scrim);
        scrim.setVisibility(View.INVISIBLE);
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        appBarLayout = findViewById(R.id.app_bar);
        adapter.AddFragment(new CardFragment(),"Home");
        adapter.AddFragment(new CompactCardFragment(),"Popular");
        topAppBar.setNavigationOnClickListener(new MaterialToolbar.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        scrim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                scrim.setVisibility(View.INVISIBLE);
            }
        });
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.page_1:
                        visibilidadOn();
                        return true;

                    case R.id.page_2:
                        visibilidadOff();
                        changeFragment(lejosFragment);
                        return true;

                    case R.id.page_3:
                        scrim.setVisibility(View.VISIBLE);
                        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
                        return true;

                    case R.id.page_4:
                        visibilidadOff();
                        changeFragment(chatFragment);
                        return true;

                    case R.id.page_5:
                        visibilidadOff();
                        changeFragment(inboxFragment);
                        return true;

                    default:
                        return true;
                }
            }
        });
        bottomDrawer.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                PostFragment fragment = new PostFragment();
                Bundle args;
                loginHide();
                scrim.setVisibility(View.INVISIBLE);
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                switch (item.getItemId()){
                    case R.id.text_post:
                        args = new Bundle();
                        args.putString("type", "text");
                        fragment.setArguments(args);
                        changeFragment(fragment);

                        return true;
                    case R.id.image_post:
                        args = new Bundle();
                        args.putString("type", "image");
                        fragment.setArguments(args);
                        changeFragment(fragment);
                        return true;
                    default:
                        return true;
                }

            }
        });
        if(savedInstanceState == null){
            loginHide();
            currentFragment = new LoginFragment();
            changeFragment(currentFragment);
        }
    }

    public static void visibilidadOn(){
        tabLayout.setVisibility(View.VISIBLE);
        viewPager.setVisibility(View.VISIBLE);
    }


    private static void visibilidadOff(){
        tabLayout.setVisibility(View.GONE);
        viewPager.setVisibility(View.GONE);
    }
    public static void loginHide(){
        visibilidadOff();
        topAppBar.setVisibility(View.INVISIBLE);
        bottomNavigationView.setVisibility(View.INVISIBLE);
        appBarLayout.setVisibility(View.INVISIBLE);

    }

    public static void loginShow(){
        visibilidadOn();
        topAppBar.setVisibility(View.VISIBLE);
        bottomNavigationView.setVisibility(View.VISIBLE);
        appBarLayout.setVisibility(View.VISIBLE);
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();
    }


}