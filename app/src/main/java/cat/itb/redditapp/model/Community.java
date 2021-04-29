package cat.itb.redditapp.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Community implements Serializable {
    private String communityId;
    private String picture;
    private String name;
    private List<Post> posts;

    public Community() {
    }

    public Community(String communityId, String picture, String name) {
        this.communityId = communityId;
        this.picture = picture;
        this.name = name;
        this.posts = new ArrayList<>();
    }

    public String getCommunityId() {
        return communityId;
    }

    public void setCommunityId(String communityId) {
        this.communityId = communityId;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public void addPost(Post p){
        posts.add(p);
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
