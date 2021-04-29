package cat.itb.redditapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.appbar.MaterialToolbar;

import cat.itb.redditapp.R;
import cat.itb.redditapp.adapter.CommunityAdapter;
import cat.itb.redditapp.helper.DatabaseHelper;
import cat.itb.redditapp.model.Community;

public class CommunityListFragment extends Fragment {
    RecyclerView recyclerViewCommunity;
    MaterialToolbar toolbar;
    CommunityAdapter adapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_community_list, container, false);
        recyclerViewCommunity = v.findViewById(R.id.recycler_view_community);
        toolbar = v.findViewById(R.id.top_app_bar_community);
        recyclerViewCommunity.setLayoutManager(new LinearLayoutManager(v.getContext()));
        FirebaseRecyclerOptions<Community> options =  new FirebaseRecyclerOptions.Builder<Community>().setQuery(DatabaseHelper.getCommunityRef(), Community.class).build();
        adapter = new CommunityAdapter(options, getActivity(), "post");
        recyclerViewCommunity.setAdapter(adapter);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostFragment fragment = new PostFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });

        // Inflate the layout for this fragment
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