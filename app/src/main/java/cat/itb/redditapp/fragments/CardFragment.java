package cat.itb.redditapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import cat.itb.redditapp.R;
import cat.itb.redditapp.adapter.FirebasePostAdapter;
import cat.itb.redditapp.adapter.PostAdapter;
import cat.itb.redditapp.helper.DatabaseHelper;
import cat.itb.redditapp.helper.PostViewModel;
import cat.itb.redditapp.model.Post;

public class CardFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<Post> posts;
    FirebasePostAdapter adapter;
    PostViewModel postViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        postViewModel = new ViewModelProvider(getActivity()).get(PostViewModel.class);
        posts = PostViewModel.posts;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_recycler, container, false);
        recyclerView = v.findViewById(R.id.recycler_view);
        FirebaseRecyclerOptions<Post> options = new FirebaseRecyclerOptions.Builder<Post>().setQuery(DatabaseHelper.postRef, Post.class).build();
        adapter = new FirebasePostAdapter(options, R.layout.item_list_view, getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



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