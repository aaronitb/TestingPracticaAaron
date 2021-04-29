package cat.itb.redditapp.helper;

import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cat.itb.redditapp.model.Post;

public class PostViewModel extends ViewModel {
    public static List<Post> posts = new ArrayList<Post>(){

    };

    public PostViewModel() {

    }

}
