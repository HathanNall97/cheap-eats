package com.example.cheapeats;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.LayoutInflaterCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class PostBrowsingActivity extends AppCompatActivity {

    //creation declarations for test layout
    EditText edt_title, edt_content;
    Button btn_post;
    RecyclerView recyclerView;


    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;


    /**
     *   __ _          _
     *  / _(_)        | |
     * | |_ _ _ __ ___| |__   __ _ ___  ___
     * |  _| | '__/ _ \ '_ \ / _` / __|/ _ \
     * | | | | | |  __/ |_) | (_| \__ \  __/
     * |_| |_|_|  \___|_.__/ \__,_|___/\___|
     */
    // Represents our database connection to FireStore
    FirebaseFirestore db;
    private RecyclerView mListView;
    private FirestoreRecyclerAdapter<PostModel, MyRecylerViewHolder> adapter;
    FirestoreRecyclerOptions<PostModel> options;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_browsing);

        /**
         * TEST CODE FOR POST CREATION
         */
        edt_content = (EditText) findViewById(R.id.edt_content);
        edt_title = (EditText) findViewById(R.id.edt_title);
        btn_post = (Button) findViewById(R.id.btn_post);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);

        //Instantiate our connection to firestore with getInstance()
        db = FirebaseFirestore.getInstance();

        // SET ONCLICK LISTENER FOR TEST BUTTON
        btn_post.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                postComment();
            }
        });
        // use a linear layout manager
        layoutManager = new LinearLayoutManager(this);
    }

    //TODO: UPDATE TO CREATE A NEW EVENTMODEL WITH THE FULL FIELDS FILLED OUT
    private void postComment() {
        String title = edt_title.getText().toString();
        String content = edt_content.getText().toString();

        // Create the new post to display
        PostModel newPost = new PostModel(title, content);

        // Commits the new item to the database
        db.collection("posts")
                .add(newPost)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d("I IS A TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("I IS A SAD TAG!!", "Error adding document", e);
                    }
                });
        //Display the comment in the RecylclerViewHolder, TODO: Check if this is necessary
//        displayEventPosts();
        adapter.notifyDataSetChanged();
    }

    private void displayEventPosts() {
        //Get list of posts from firesore
        Query query = db.collection("posts")
                .orderBy("postTitle", Query.Direction.ASCENDING);

        // specify the options that will be used to build the list of posts from firestore
        options = new FirestoreRecyclerOptions.Builder<PostModel>()
                        .setQuery(query, PostModel.class)
                        .build();

        // adapt the firestore data using our model
        adapter = new FirestoreRecyclerAdapter<PostModel, MyRecylerViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyRecylerViewHolder holder, int i, @NonNull PostModel postModel) {
//                postViewHolder.setPostTitle(postModel.getTitle());
                holder.txt_title.setText(postModel.getTitle());
                holder.txt_content.setText(postModel.getDescription());
            }

            @NonNull
            @Override
            public MyRecylerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
                //!-------------------------------------------------------------------------------------------------------------- may have to change item_product to post
                View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.post_item, viewGroup, false);
                return new MyRecylerViewHolder(itemView);
            }
        };
        /* tell our adapter to start listening, and refreshing on updates*/
        adapter.startListening();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (adapter != null) {
            adapter.stopListening();
        }
    }


/**    // Product View Holder
    private class PostViewHolder extends RecyclerView.ViewHolder {
        private View view;

        PostViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        void setPostTitle(final String postTitle) {
            TextView textView = view.findViewById(R.id.textView);
            textView.setText(postTitle);

            //code to display a toast message when an item is clicked
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getApplicationContext(), postTitle, Toast.LENGTH_SHORT).show();
                }
            });

        }

    }*/
}
