package cat.itb.redditapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import cat.itb.redditapp.R;
import cat.itb.redditapp.adapter.CommunityAdapter;
import cat.itb.redditapp.helper.DatabaseHelper;
import cat.itb.redditapp.model.Community;

public class HelperFragment extends Fragment {
    RecyclerView comSelector;
    CommunityAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_helper, container, false);
        comSelector = v.findViewById(R.id.com_selector);

        comSelector.setLayoutManager(new LinearLayoutManager(v.getContext()));
        FirebaseRecyclerOptions<Community> options =  new FirebaseRecyclerOptions.Builder<Community>().setQuery(DatabaseHelper.getCommunityRef(), Community.class).build();
        adapter = new CommunityAdapter(options, getActivity(), "view");
        comSelector.setAdapter(adapter);

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}