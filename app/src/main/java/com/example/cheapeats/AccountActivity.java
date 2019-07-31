package com.example.cheapeats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AccountActivity extends AppCompatActivity {

    private Button btnChangeEmail, btnChangePass, btnPassReset, btnDeleteAccount, btnSignOut, btnBack;
    private Button btnNotificationSettings, btnFaves, btnFoodPrefs, btnChangeUserN, btnSendText;
    private String email;
    private TextView emailDisplay, userNameDisplay;
    private EditText textEditor;
    FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //Creating the edit space at the bottom of the page and hiding the view until needed
        btnSendText = (Button) findViewById(R.id.sendEdit);
        textEditor = (EditText) findViewById(R.id.textEditor);
        btnSendText.setVisibility(View.GONE);
        textEditor.setVisibility(View.GONE);


        //Displaying the user's email in settings
        emailDisplay = (TextView) findViewById(R.id.displayEmail);
        emailDisplay.setText(user.getEmail());

        //Displaying the user's username in settings
        userNameDisplay = (TextView) findViewById(R.id.displayUserName);
        userNameDisplay.setText(user.getDisplayName());

        btnChangeEmail = (Button) findViewById(R.id.change_email_button);
        btnChangePass = (Button) findViewById(R.id.change_password_button);
        btnPassReset = (Button) findViewById(R.id.sending_pass_reset_button);
        btnDeleteAccount = (Button) findViewById(R.id.remove_user_button);
        btnSignOut = (Button) findViewById(R.id.sign_out);
        auth = FirebaseAuth.getInstance();

        

        //DONE
        btnPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, ResetPasswordActivity.class));
            }
        });

        //DONE
        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(AccountActivity.this, SignupActivity.class));
            }
        });


        /*btnChangeUserN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnSendText.setVisibility(View.VISIBLE);
                textEditor.setVisibility(View.VISIBLE);
                btnSendText.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String newUser = textEditor.getText().toString();
                        UserProfileChangeRequest profileUpdater = new UserProfileChangeRequest.Builder()
                                .setDisplayName(newUser)
                                .build();
                        userNameDisplay.setText(user.getDisplayName());
                        btnSendText.setVisibility(View.GONE);
                        textEditor.setVisibility(View.GONE);
                    }
                });

            }
        });


        // ^  EVERYTHING ABOVE IS IMPLEMENTED  ^
        // -----------------------------------------------------------------
        // ↓ EVERYTHING BELOW IS NOT IMPLEMENTED ↓


        /* Needs Implemented
        btnFoodPrefs.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

            }
        });

        //Needs Implemented
        btnFaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //Needs Implemented
        btnNotificationSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });




        //Needs Implemented
        btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AccountActivity.this, changeEmailActivity.class));

            }
        });

        //Needs Implemented
        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AccountActivity.this, changePassActivity.class));

            }
        });

        //Needs Implemented
        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //startActivity(new Intent(AccountActivity.this, delAccountActivity.class));
            }
        });*/









    }
}
