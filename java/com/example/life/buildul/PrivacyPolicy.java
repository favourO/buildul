package com.example.life.buildul;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PrivacyPolicy extends AppCompatActivity {
    private Toolbar toolbar;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String UID, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            UID = mAuth.getCurrentUser().getUid();
            startActivity(new Intent(PrivacyPolicy.this, Login.class));
            finish();
        }else {
            final String WelcomMail = mAuth.getCurrentUser().getEmail();
        }
        setContentView(R.layout.activity_privacy_policy);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UID = mAuth.getCurrentUser().getUid();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
