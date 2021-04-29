package cat.itb.redditapp.helper;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cat.itb.redditapp.model.Comment;
import cat.itb.redditapp.model.Community;
import cat.itb.redditapp.model.Post;
import id.zelory.compressor.Compressor;

public class DatabaseHelper {
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    public static DatabaseReference communityRef = database.getReference("Community");
    public static DatabaseReference postRef = database.getReference("Post");
    public static DatabaseReference commentRef = database.getReference("Comment");
    public static DatabaseReference usersRef = database.getReference("Users");
    static StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    static StorageReference storageRefCommunity = storageRef.child("community_pictures");
    static StorageReference storageRefPost = storageRef.child("post_pictures");
    static Bitmap thum_bitmap;
    static byte[] thumb_byte;
    static String imageUrl;
    static Community c;
    static boolean isAdded;
    static List<String> listOfVotes;
    public static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    public static DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();


    public static void insertCommunity(Community c, String title ,String picture) throws IOException {
        String key = communityRef.push().getKey();
        c.setCommunityId(key);
        c.setName(title);
        imageUrl = picture;
        c.setPicture(imageUrl);
        communityRef.child(key).setValue(c);
    }

    public static void insertPost(Post p){
        String key = postRef.push().getKey();
        p.setPostId(key);
        postRef.child(key).setValue(p);
    }

    public static void insertComment(Comment c){
        String key = commentRef.push().getKey();
        c.setCommentId(key);
        commentRef.child(key).setValue(c);
    }

    public static void subirImagenCommunity(File f, Context context,String title){
        comprimirImagen(f, context);
        storageRefCommunity.child(title);
        UploadTask uploadTask = storageRef.putBytes(thumb_byte);
        Task<Uri> uriTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    System.out.println("Imposible");
                }
                return storageRefCommunity.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                Uri downloadUri = task.getResult();
                imageUrl = downloadUri.toString();

            }
        });
    }
    public static void comprimirImagen(File f, Context c){
        try {
            thum_bitmap = new Compressor(c)
                    .compressToBitmap(f);
        } catch (IOException e) {
            System.out.println("Este es el fallo: "+ e);
        }

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        thum_bitmap.compress(Bitmap.CompressFormat.JPEG, 90, byteArrayOutputStream);
        thumb_byte = byteArrayOutputStream.toByteArray();
    }
    public static DatabaseReference getCommunityRef(){
        return communityRef;
    }

    public static void addVoteUser(final String postId, final String state, final boolean isToAdd){

        readData(usersRef, mAuth.getCurrentUser().getUid(), null,new MyCallback() {
            @Override
            public void onCallback(Object value) {
                Map<String, Object> map = (HashMap<String, Object>) value;
                List<String> upPosts;
                List<String> downPosts;
                int votes=0;
                switch (state) {
                    case "up":
                        if (map.get("up_posts") == null) {
                            map.put("up_posts", new ArrayList<String>());

                        }
                        if (map.get("down_posts") == null) {
                            map.put("down_posts", new ArrayList<String>());

                        }
                         upPosts = (List<String>) map.get("up_posts");
                         downPosts = (List<String>) map.get("down_posts");
                        if (!upPosts.contains(postId)) {
                            if (downPosts.contains(postId)) {
                                downPosts.remove(postId);
                                votes++;
                                map.put("down_posts", downPosts);
                            }
                            upPosts.add(postId);
                            votes++;

                        }else {
                            upPosts.remove(postId);
                            votes--;

                        }
                        changeVotes(postId,votes);
                        map.put("up_posts", upPosts);

                        break;
                    case "down":
                        if (map.get("up_posts") == null) {
                            map.put("up_posts", new ArrayList<String>());

                        }
                        if (map.get("down_posts") == null) {
                            map.put("down_posts", new ArrayList<String>());

                        }
                        upPosts = (List<String>) map.get("up_posts");
                        downPosts = (List<String>) map.get("down_posts");
                        if (!downPosts.contains(postId)) {
                            if (upPosts.contains(postId)){
                                upPosts.remove(postId);

                                votes--;
                                map.put("up_posts", upPosts);
                            }
                                downPosts.add(postId);
                                votes--;


                        }else {
                            downPosts.remove(postId);
                            votes++;

                        }
                        changeVotes(postId, votes);
                        map.put("down_posts", downPosts);

                        break;
                }
                usersRef.child(mAuth.getCurrentUser().getUid()).setValue(map);
            }

        });

    }
    public static void changeVotes(final String postId, final int vote){
        readData(postRef, postId, Post.class, new MyCallback() {
            @Override
            public void onCallback(Object value) {
                Post p =(Post) value;
                postRef.child(postId).child("votes").setValue(p.getVotes()+vote);
            }
        });

    }

    public static List<String> getListVotes(final String vote){

        readData(usersRef, mAuth.getCurrentUser().getUid(), null, new MyCallback() {
            @Override
            public void onCallback(Object value) {
                HashMap<String, Object> map = (HashMap<String, Object>) value;
                switch (vote){
                    case "up":
                        listOfVotes = (List<String>) map.get("up_votes");
                        break;
                    case "down":
                        listOfVotes = (List<String>) map.get("down_votes");
                        break;
                }
            }
        });
        return listOfVotes;
    }

    public static void readData(DatabaseReference databaseReference, String id, final Class<?> object, final MyCallback myCallback){

        databaseReference.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Object co;
                if(object!=null){
                    co = snapshot.getValue(object);
                }else {
                    co = snapshot.getValue();
                }
                myCallback.onCallback(co);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public interface MyCallback {
        void onCallback(Object value);
    }
}


