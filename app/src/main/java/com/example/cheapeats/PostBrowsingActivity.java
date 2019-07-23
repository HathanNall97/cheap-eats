package com.example.cheapeats;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
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
import android.widget.TextView;

public class PostBrowsingActivity extends AppCompatActivity {
//    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    // Firebase
    FirebaseFirestore db;

    private RecyclerView mListView;
    private FirestoreRecyclerAdapter<PostModel, PostViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_browsing);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        DocumentReference docRef = db.collection("posts").document();

        //Get list of posts from firesore
        Query query = db.collection("posts")
                .orderBy("postTitle", Query.Direction.ASCENDING);

        // specify the options that will be used to build the list of posts from firestore
        FirestoreRecyclerOptions<PostModel> options =
                new FirestoreRecyclerOptions.Builder<PostModel>()
                        .setQuery(query, PostModel.class)
                        .build();

        // adapt the firestore data using our model
        adapter = new FirestoreRecyclerAdapter<PostModel, PostViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PostViewHolder postViewHolder, int i, @NonNull PostModel postModel) {
                postViewHolder.setPostTitle(postModel.getTitle());
            }

            @NonNull
            @Override
            public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                //!-------------------------------------------------------------------------------------------------------------- may have to change item_product to post
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
                return new PostViewHolder(view);
            }
        };

        recyclerView.setAdapter(adapter);


        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
//        recyclerView.setHasFixedSize(true);

        // use a linear layout manager
//        layoutManager = new LinearLayoutManager(this);
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
    // Product View Holder
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
//            textView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Toast.makeText(getApplicationContext(), postTitle, Toast.LENGTH_SHORT).show();
//                }
//            });

        }

    }
}
