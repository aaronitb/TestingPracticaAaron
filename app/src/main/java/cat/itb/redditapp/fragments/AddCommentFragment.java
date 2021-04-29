package cat.itb.redditapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.appbar.MaterialToolbar;

import cat.itb.redditapp.MainActivity;
import cat.itb.redditapp.R;


public class AddCommentFragment extends Fragment {

    MaterialToolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add_comment, container, false);
        toolbar =v.findViewById(R.id.top_app_bar_comment);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                returnToMain();
            }
        });
        return v;
    }

    public void returnToMain(){
        FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
        transaction.remove(MainActivity.currentFragment);
        transaction.replace(R.id.fragment_container, new CardFragment());
        MainActivity.loginShow();
        transaction.commit();
    }
}