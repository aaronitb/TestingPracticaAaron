package cat.itb.redditapp.fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import cat.itb.redditapp.MainActivity;
import cat.itb.redditapp.R;
import cat.itb.redditapp.helper.DatabaseHelper;
import cat.itb.redditapp.model.Community;
import cat.itb.redditapp.model.Post;
import de.hdodenhof.circleimageview.CircleImageView;


public class PostFragment extends Fragment {
    Community c;
    TextView comPicker;
    TextView post;
    CircleImageView comPicture;
    TextInputEditText titlePost;
    TextInputEditText optionalPost;
    MaterialToolbar toolbar;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_post, container, false);
        comPicker = v.findViewById(R.id.com_picker);
        comPicture = v.findViewById(R.id.com_picture);
        post = v.findViewById(R.id.post);
        titlePost = v.findViewById(R.id.post_title2);
        optionalPost = v.findViewById(R.id.post_optional_text2);
        toolbar =v.findViewById(R.id.top_app_bar_community);
        Bundle args = getArguments();
        if(args!=null){
            c = (Community) args.getSerializable("community");
            if(c!=null) {
                Picasso.with(getContext()).load(c.getPicture()).into(comPicture);
                comPicker.setText("r/" + c.getName());
            }
        }
        comPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CommunityListFragment fragment = new CommunityListFragment();
                FragmentTransaction transaction = getActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, fragment);
                transaction.commit();
            }
        });


        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(c==null){
                    Toast.makeText(getContext(),"Select a community", Toast.LENGTH_SHORT).show();

                }else{
                    String title = titlePost.getText().toString();
                    String optional = optionalPost.getText().toString();
                    if (title==null){
                        Toast.makeText(getContext(), "The title is obligatory", Toast.LENGTH_SHORT).show();

                    }else{
                        Post p = new Post(c.getCommunityId(), "anonymous", title,optional, 0, 0, "text");
                        DatabaseHelper.insertPost(p);
                        if (c.getPosts() == null) {
                            c.setPosts(new ArrayList<Post>());
                        }
                        c.addPost(p);
                        DatabaseHelper.communityRef.child(c.getCommunityId()).setValue(c);
                        returnToMain();
                    }
                }
            }
        });
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