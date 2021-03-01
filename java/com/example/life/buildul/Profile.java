package com.example.life.buildul;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.life.buildul.app.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class Profile extends AppCompatActivity {
    private DatabaseReference ref;
    private String UID, Email;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private TextView firstN, lastN, email, phone, city, state, postalcode, address;
    private ProgressDialog progressDialog;
    private android.support.v7.widget.Toolbar toolbar;
    private AlertDialog.Builder alert;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        //getting the current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();
            Email = user.getEmail();
            database = FirebaseDatabase.getInstance();
            ref = database.getReference("Users/" + UID);

        }
        setContentView(R.layout.activity_profile);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading Profile");
        progressDialog.show();

        getProfile();

    }

    private void getProfile(){
        firstN = (TextView) findViewById(R.id.firstp);
        lastN = (TextView) findViewById(R.id.lastp);
        email = (TextView) findViewById(R.id.emailp);
        phone = (TextView) findViewById(R.id.phonep);
        city = (TextView) findViewById(R.id.cityp);
        state = (TextView) findViewById(R.id.statep);
        postalcode = (TextView) findViewById(R.id.postalp);
        address = (TextView) findViewById(R.id.addressp);

        ref.addValueEventListener(new com.google.firebase.database.ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot dataSnapshot) {
                User post =  dataSnapshot.getValue(User.class);
                //String num = String.valueOf(arrayCount);
                if (post != null) {
                    firstN.setText(post.getFirstname());
                    lastN.setText(post.getLastname());
                    email.setText(post.getEmail());
                    phone.setText(post.getPhone());
                    city.setText(post.getCity());
                    state.setText(post.getState());
                    postalcode.setText(post.getPostalcode());
                    phone.setText(post.getPhone());
                    address.setText(post.getAddress());
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(Profile.this, databaseError.getMessage().toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.edit) {
            Intent i = new Intent(Profile.this, EditProfile.class);
            startActivity(i);
            return true;
        }else */if (id == R.id.home){
            Intent i = new Intent(Profile.this, MainActivity.class);
            startActivity(i);
        }/*else if (id == R.id.add_profile_pic){
            startActivity(new Intent(this, UploadProfile_Picture.class));
        }*/

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.profile, menu);
        return true;
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MyAccount.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
