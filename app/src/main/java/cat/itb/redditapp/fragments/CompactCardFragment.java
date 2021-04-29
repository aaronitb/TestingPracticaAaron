package cat.itb.redditapp.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

import cat.itb.redditapp.R;
import cat.itb.redditapp.adapter.PostAdapter;
import cat.itb.redditapp.helper.PostViewModel;
import cat.itb.redditapp.model.Post;

public class CompactCardFragment extends Fragment {

    BottomNavigationView bottomNavigationView;
    RecyclerView recyclerView;
    List<Post> posts;
    PostAdapter adapter;
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
        adapter = new PostAdapter(posts,R.layout.item_compact_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);



        return v;
    }


}