package cat.itb.redditapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


import cat.itb.redditapp.MainActivity;
import cat.itb.redditapp.R;
import cat.itb.redditapp.fragments.CommentFragment;
import cat.itb.redditapp.fragments.RegistroFragment;
import cat.itb.redditapp.model.Comment;
import de.hdodenhof.circleimageview.CircleImageView;

public class FirebaseCommentAdapter extends FirebaseRecyclerAdapter<Comment, FirebaseCommentAdapter.CommentHolder> {

    int layout;
    Context context;

    public FirebaseCommentAdapter(@NonNull FirebaseRecyclerOptions<Comment> options, int layout) {
        super(options);
        this.layout = layout;
    }

    @Override
    protected void onBindViewHolder(@NonNull CommentHolder holder, int position, @NonNull Comment model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public CommentHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        return new CommentHolder(v);
    }

    public class CommentHolder extends RecyclerView.ViewHolder{

        CircleImageView picture;
        TextView user;
        TextView commentFecha;
        TextView commentContent;
        TextView numLikes;


        public CommentHolder(@NonNull View itemView) {
            super(itemView);
            picture = itemView.findViewById(R.id.circleImageViewComment);
            user = itemView.findViewById(R.id.commentTitle);
            commentFecha = itemView.findViewById(R.id.commentFecha);
            commentContent = itemView.findViewById(R.id.commentContent);
            numLikes = itemView.findViewById(R.id.textViewNumeroLikes);
        }

        @SuppressLint("SetTextI18n")
        public void bind(Comment comment){
            user.setText(comment.getUser());
            commentFecha.setText(comment.getFecha());
            commentContent.setText(comment.getContentText());
            numLikes.setText(String.valueOf(comment.getVotes()));
        }
    }
}
