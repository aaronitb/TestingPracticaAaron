package cat.itb.redditapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import cat.itb.redditapp.MainActivity;
import cat.itb.redditapp.R;
import cat.itb.redditapp.fragments.CommentFragment;
import cat.itb.redditapp.helper.DatabaseHelper;
import cat.itb.redditapp.model.Community;
import cat.itb.redditapp.model.Post;
import de.hdodenhof.circleimageview.CircleImageView;

public class FirebasePostAdapter extends FirebaseRecyclerAdapter<Post, FirebasePostAdapter.PostHolder>  {

    int layout;
    Context context;
    FragmentActivity activity;
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public FirebasePostAdapter(@NonNull FirebaseRecyclerOptions<Post> options, int layout, FragmentActivity activity) {
        super(options);
        this.layout = layout;
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull PostHolder holder, int position, @NonNull Post model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public PostHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new PostHolder(v);
    }

    public class PostHolder extends RecyclerView.ViewHolder{

        CircleImageView comPicture;
        TextView community;
        TextView user;
        ImageView upvote;
        ImageView downvote;
        TextView title;
        TextView optionalText;
        TextView likes;
        TextView comments;
        String postId;

        ImageView commentImage;


        public PostHolder(@NonNull View itemView) {
            super(itemView);
            comPicture = itemView.findViewById(R.id.com_picture_post);
            community = itemView.findViewById(R.id.post_community);
            user = itemView.findViewById(R.id.post_user);
            title = itemView.findViewById(R.id.post_title2);
            optionalText = itemView.findViewById(R.id.post_optional_text2);
            likes = itemView.findViewById(R.id.post_likes);
            comments = itemView.findViewById(R.id.post_comments);
            upvote = itemView.findViewById(R.id.upvote);
            downvote = itemView.findViewById(R.id.downvote);

            commentImage = itemView.findViewById(R.id.imageView3);
        }

        @SuppressLint("SetTextI18n")
        public void bind(final Post post){

            updateVoteButtons();
            postId =post.getPostId();
            DatabaseHelper.readData(DatabaseHelper.getCommunityRef(), post.getCommunity(), Community.class, new DatabaseHelper.MyCallback() {
                @Override
                public void onCallback(Object value) {
                    Community c = (Community) value;
                    Picasso.with(context).load(c.getPicture()).into(comPicture);
                    community.setText("r/"+c.getName());
                }
            });

            user.setText("Posted by u/"+ post.getUser());
            title.setText(post.getTitle());
            String optText = post.getContentText();
            if (optText!=null && !optText.isEmpty() && layout != R.layout.item_compact_view){
                optionalText.setText(optText);
            }
            likes.setText(String.valueOf(post.getVotes()));
            comments.setText(String.valueOf(post.getNumComments()));

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
            upvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DatabaseHelper.addVoteUser(postId, "up", true);
                    updateVoteButtons();
                }
            });
            downvote.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    DatabaseHelper.addVoteUser(postId, "down", true);
                    updateVoteButtons();
                }
            });




            commentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new CommentFragment();
                    FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                    transaction.replace(R.id.fragment_container, fragment);
                    MainActivity.currentFragment = fragment;
                    MainActivity.loginHide();
                    transaction.commit();
                }
            });
        }

        public void updateVoteButtons() {
            DatabaseHelper.readData(DatabaseHelper.usersRef, DatabaseHelper.mAuth.getCurrentUser().getUid(), null, new DatabaseHelper.MyCallback() {
                @Override
                public void onCallback(Object value) {
                    HashMap<String, Object> map = (HashMap<String, Object>) value;
                    List<String> upVotes = (List<String>) map.get("up_posts");
                    List<String> downVotes = (List<String>) map.get("down_posts");
                    if (upVotes != null) {
                        if (upVotes.contains(postId)) {
                            upvote.setColorFilter(Color.parseColor("#F44336"));
                        } else {
                            upvote.setColorFilter(null);
                        }
                    }else {
                        upvote.setColorFilter(null);
                    }
                    if (downVotes != null) {
                        if (downVotes.contains(postId)) {
                            downvote.setColorFilter(Color.parseColor("#2196F3"));
                        } else {
                            downvote.setColorFilter(null);
                        }
                    }else {
                        downvote.setColorFilter(null);
                    }
                }
            });
        }
    }
}

