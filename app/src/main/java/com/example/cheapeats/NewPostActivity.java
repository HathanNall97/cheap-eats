package com.example.cheapeats;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;
import android.widget.CalendarView;

import com.google.android.material.chip.Chip;
import android.widget.CompoundButton.OnCheckedChangeListener;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.type.Date;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class NewPostActivity extends AppCompatActivity {

    private EditText editEventTitle, editHostName, editEventDescription, editDate, editTime;
    private ChipGroup tagGroup;
    private Button postComplete;
    private RatingBar ratingBar;

    private String[] tagList = {"Latin", "Pub Food", "Happy Hour", "Student Organized", "Sandwiches",
            "American", "BBQ", "Breakfast", "Lunch", "Dinner", "Dessert", "Salads", "Asian", "Pizza",
            "Hamburgers", "Pasta", "Healthy", "Italian", "Diet & Nutrition", "Vegetarian", "Vegan",
            "Halal", "Gluten Free", "Organic", "Low-fat", "Low-carb", "Kosher"};

    private List<String> tagsToApply = new ArrayList<String>();
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setting up view
        setContentView(R.layout.activity_new_post);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
        setTitle("Add Post");

        //Setting the widgets up
        editEventTitle = (EditText) findViewById(R.id.edit_title);
        editHostName = (EditText) findViewById(R.id.edit_host_name);
        editEventDescription = (EditText) findViewById(R.id.edit_description);
        editTime= (EditText) findViewById(R.id.edit_time_entry);
        editDate = (EditText) findViewById(R.id.edit_date_entry);
        ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        postComplete = (Button) findViewById(R.id.post_button);
        tagGroup = (ChipGroup) findViewById(R.id.chip_group);

        int tagIterator = 0;
        // Generate Chip for each tag
        for(String tag : tagList){
            Chip newTag = new Chip(this);
            newTag.setId(tagIterator);
            newTag.setTag(tagIterator);
            newTag.setTextAppearance(this, android.R.style.TextAppearance_Small);
            newTag.setText(tag);
            newTag.setCheckable(true);
//            newTag.isClickable();
            newTag.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    //get the tag
                    int tagId = (int) compoundButton.getTag();
                    Chip c = (Chip) tagGroup.findViewById(tagId);
                    if (b) {
                        tagsToApply.add((String) c.getText());
                    }else{
                        tagsToApply.remove(tagId);
                    }

                }
            });
            tagIterator++;
            tagGroup.addView(newTag);
        }
        postComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                savePost();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.new_post_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void populateTags(){
        return;
    }

    private void savePost(){
        String title = editEventTitle.getText().toString();
        String host = editEventDescription.getText().toString();
        String description = editHostName.getText().toString();
        float rating = ratingBar.getRating();
        String newKey;

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "NOT EMPTY DINGUS",Toast.LENGTH_SHORT);
            return;
        }

        FirebaseAuth auth = FirebaseAuth.getInstance();

        CollectionReference postsRef = FirebaseFirestore.getInstance()
                .collection("posts");

        PostModel newPost = new PostModel(title,host, description, auth.getCurrentUser().getUid(), tagsToApply, (int)rating);
        postsRef.add(newPost);
//        postsRef.getId();
        Toast.makeText(this,"Post Created!", Toast.LENGTH_SHORT);
        finish();
    }
}
