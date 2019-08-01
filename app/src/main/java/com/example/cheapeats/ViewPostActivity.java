package com.example.cheapeats;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

public class ViewPostActivity extends AppCompatActivity {
    TextView title, host, description, likeCount, dislikeCount, date, time;
    RatingBar rating;

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_post);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        title = findViewById(R.id.text_view_title);
        host = findViewById(R.id.text_view_host_name);
        description = findViewById(R.id.text_view_description);
        date = findViewById(R.id.text_view_date_entry);
        time = findViewById(R.id.text_view_time_entry);
        likeCount = findViewById(R.id.txt_view_like_count);
        dislikeCount = findViewById(R.id.txt_view_dislike_count);
        rating = findViewById(R.id.rating_bar);

        Intent intent = getIntent();
        String postKey = intent.getStringExtra("post_key");

        DocumentReference docRef = db.collection("posts").document(postKey);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        title.setText((String)document.get("title"));
                        host.setText((String)document.get("host"));
                        description.setText((String)document.get("description"));
//                        time.setText((String)document.get("startTime"));
//                        title.setText((String)document.get("title"));
//                        rating.setRating(document.get("rating"));
                        likeCount.setText((String) document.get("likeCount"));
                        dislikeCount.setText((String) document.get("dislikeCount"));
                    }
                }
            }
        });


    }

}
