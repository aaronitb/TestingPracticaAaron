package cat.itb.redditapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import cat.itb.redditapp.R;
import cat.itb.redditapp.model.Post;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    List<Post> posts;
    int layout;

    public RecyclerAdapter(List<Post> posts, int layout) {
        this.posts = posts;
        this.layout = layout;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(posts.get(position));

    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView community;
        TextView user;

        TextView title;
        TextView optionalText;
        TextView likes;
        TextView comments;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            community = itemView.findViewById(R.id.post_community);
            user = itemView.findViewById(R.id.post_user);
            title = itemView.findViewById(R.id.post_title2);
            optionalText = itemView.findViewById(R.id.post_optional_text2);
            likes = itemView.findViewById(R.id.post_likes);
            comments = itemView.findViewById(R.id.post_comments);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Post post){
            community.setText("r/"+post.getCommunity());
            user.setText("Posted by u/"+ post.getUser());
            title.setText(post.getTitle());
            String optText = post.getContentText();
            if (optText!=null && !optText.isEmpty() && layout != R.layout.item_compact_view){
                optionalText.setText(optText);
            }
            likes.setText(String.valueOf(post.getVotes()));
            comments.setText(String.valueOf(post.getNumComments()));
        }
    }
}
