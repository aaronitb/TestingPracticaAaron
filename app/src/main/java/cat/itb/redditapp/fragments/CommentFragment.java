package cat.itb.redditapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

import cat.itb.redditapp.MainActivity;
import cat.itb.redditapp.R;
import cat.itb.redditapp.adapter.FirebaseCommentAdapter;
import cat.itb.redditapp.helper.DatabaseHelper;
import cat.itb.redditapp.model.Comment;


public class CommentFragment extends Fragment {

    RecyclerView recyclerView;
    List<Comment> comments;
    FirebaseCommentAdapter adapter;

    Button addComment;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_comment, container, false);
        addComment = v.findViewById(R.id.button_comment);
        addComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = new AddCommentFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                MainActivity.currentFragment = fragment;
                transaction.commit();
            }
        });

        recyclerView = v.findViewById(R.id.recycler_view_comments);
        FirebaseRecyclerOptions<Comment> options = new FirebaseRecyclerOptions.Builder<Comment>().setQuery(DatabaseHelper.commentRef, Comment.class).build();
        adapter = new FirebaseCommentAdapter(options, R.layout.item_list_comment);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);
        return v;
    }
}