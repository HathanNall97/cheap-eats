package com.example.cheapeats;

import androidx.appcompat.app.AppCompatActivity;

import android.accounts.Account;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AccountActivity extends AppCompatActivity {

    private Button btnChangeEmail, btnChangePass, btnPassReset, btnDeleteAccount, btnSignOut, btnBack;
    FirebaseAuth auth;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

       btnChangeEmail = (Button) findViewById(R.id.change_email_button);
        btnChangePass = (Button) findViewById(R.id.change_password_button);
        btnPassReset = (Button) findViewById(R.id.sending_pass_reset_button);
        btnDeleteAccount = (Button) findViewById(R.id.remove_user_button);
        btnSignOut = (Button) findViewById(R.id.sign_out);
        btnBack = (Button) findViewById(R.id.backbutton);
        auth = FirebaseAuth.getInstance();



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(AccountActivity.this, MainActivity.class));
            }
        });


        btnPassReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, ResetPasswordActivity.class));
            }
        });

        //Need to implement these by adding in the changeEmail, ChangePass, and DeleteAccount Activities :)
        /*btnChangeEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, changeEmailActivity.class));

            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, changePassActivity.class));

            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AccountActivity.this, delAccountActivity.class));
            }
        });*/

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(AccountActivity.this, SignupActivity.class));
            }
        });







    }
}
