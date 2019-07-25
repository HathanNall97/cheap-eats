package com.example.cheapeats;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.CalendarView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class NewPostActivity extends AppCompatActivity {

    private EditText editEventTitle, editHostName, editEventDescription;
    private CalendarView eventDate;
    private Button postComplete;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setting up view
        setContentView(R.layout.activity_new_post);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close);
//        setTitle("Add Post");

        //Setting the widgets up
        editEventTitle = (EditText) findViewById(R.id.edit_title);
        editHostName = (EditText) findViewById(R.id.edit_host_name);
        editEventDescription = (EditText) findViewById(R.id.edit_description);
        eventDate = (CalendarView) findViewById(R.id.edit_date_entry);



        postComplete = (Button) findViewById(R.id.post_button);
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
//        switch (item.getItemId()){
//            case R.id.save_note:
//                saveNote();
//                return true;
//            default:
//                return super.onOptionsItemSelected(item);
////            case R.id.whatever:
////                what I want to do();
//        }
        return super.onOptionsItemSelected(item);
    }

    private void savePost(){
        String title = editEventTitle.getText().toString();
        String host = editEventDescription.getText().toString();
        String description = editHostName.getText().toString();

        if(title.trim().isEmpty() || description.trim().isEmpty()){
            Toast.makeText(this, "NOT EMPTY DINGUS",Toast.LENGTH_SHORT);
            return;
        }

        CollectionReference postsRef = FirebaseFirestore.getInstance()
                .collection("posts");
        postsRef.add(new PostModel(title, host, description));
        Toast.makeText(this,"Post Created!", Toast.LENGTH_SHORT);
        finish();
    }
}
