package com.example.cheapeats;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.CalendarView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class PostCreationActivity extends AppCompatActivity {

    private EditText eventTitle, hostName, eventDescription;
    private CalendarView eventDate;
    private Button postComplete;

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //setting up view
        setContentView(R.layout.activity_create_post);

        //Setting the widgets up
        eventTitle = (EditText) findViewById(R.id.event_title_entry);
        hostName = (EditText) findViewById(R.id.host_name_entry);
        eventDescription = (EditText) findViewById(R.id.event_description_entry);
        eventDate = (CalendarView) findViewById(R.id.event_date_entry);
        postComplete = (Button) findViewById(R.id.complete_post_button);

        postComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titleVerification = eventTitle.getText().toString();
                String hostVerification = hostName.getText().toString();
                String descriptionVerification = eventDescription.getText().toString();
                // verify that the user has inputs for all fields
                if(titleVerification.equals(null) || hostVerification.equals(null) || descriptionVerification.equals(null)){
                    Toast.makeText(getApplication(), "Empty Fields. Post Creation Unsuccessful. Page reloading...", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PostCreationActivity.this, PostCreationActivity.class));
                } else {
                    Toast.makeText(getApplication(), "Post Creation Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(PostCreationActivity.this, MainActivity.class));
                }
            }
        });
    }
}
