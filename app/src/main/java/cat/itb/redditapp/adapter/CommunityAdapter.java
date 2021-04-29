package cat.itb.redditapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

import java.io.Serializable;

import cat.itb.redditapp.R;
import cat.itb.redditapp.fragments.PostFragment;
import cat.itb.redditapp.model.Community;
import de.hdodenhof.circleimageview.CircleImageView;

public class CommunityAdapter extends FirebaseRecyclerAdapter<Community, CommunityAdapter.CommunityHolder> {
    FragmentActivity activity;
    String flag;



    public CommunityAdapter(@NonNull FirebaseRecyclerOptions<Community> options, FragmentActivity activity, String flag) {
        super(options);
        this.activity = activity;
        this.flag = flag;
    }

    @Override
    protected void onBindViewHolder(@NonNull CommunityHolder holder, int position, @NonNull Community model) {
        holder.bind(model);
    }

    @NonNull
    @Override
    public CommunityHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_community, parent, false);
        return new CommunityHolder(v);
    }

    public class  CommunityHolder extends RecyclerView.ViewHolder{
        CircleImageView comPicture;
        TextView comTitle;
        Context context;
        public CommunityHolder(@NonNull View itemView) {
            super(itemView);
            comPicture = itemView.findViewById(R.id.circleImageView);
            comTitle = itemView.findViewById(R.id.com_title);
            context = itemView.getContext();
        }

        @SuppressLint("SetTextI18n")
        public void bind (final Community c){
            comTitle.setText("r/"+ c.getName());
            Picasso.with(context).load(c.getPicture()).into(comPicture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (flag) {
                        case "post":
                            PostFragment fragment = new PostFragment();
                            Bundle args = new Bundle();
                            args.putSerializable("community", (Serializable) c);
                            fragment.setArguments(args);
                            FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                            transaction.replace(R.id.fragment_container, fragment);
                            transaction.commit();
                            break;
                        case "view":
                            break;
                    }
                }
            });
        }
    }
}
