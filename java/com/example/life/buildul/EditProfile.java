package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.life.buildul.app.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfile extends AppCompatActivity {
    private DatabaseReference ref;
    private String UID, Email, firstn;
    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private EditText fname, lname, email, city, address, state, postal, phone;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        //getting the current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            UID = user.getUid();
            Email = user.getEmail();
            firstn = user.getDisplayName();
            database = FirebaseDatabase.getInstance();
            ref = database.getReference("Users/");

        }
        setContentView(R.layout.activity_edit_profile);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating Profile...");
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, Profile.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    private void updateProfile(){
        progressDialog.setMessage("Please Wait");
        progressDialog.show();

        fname = (EditText) findViewById(R.id.firstname);

        lname = (EditText) findViewById(R.id.lastname);

        email = (EditText) findViewById(R.id.email);

        state = (EditText) findViewById(R.id.state);

        address = (EditText) findViewById(R.id.address);

        city = (EditText) findViewById(R.id.city);

        phone = (EditText) findViewById(R.id.phone);

        postal = (EditText) findViewById(R.id.postal);

        final String fName = fname.getText().toString();
        final String lName = lname.getText().toString();
        final String eMail = email.getText().toString();
        final String sTate = state.getText().toString();
        final String adDress = address.getText().toString();
        final String ciTy = city.getText().toString();
        final String phOne = phone.getText().toString();
        final String poStal = postal.getText().toString();

        database = FirebaseDatabase.getInstance();
        ref = database.getReference();
        ref.child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot.getRef().child("address").setValue(adDress);
                dataSnapshot.getRef().child("city").setValue(ciTy);
                dataSnapshot.getRef().child("email").setValue(eMail);
                dataSnapshot.getRef().child("firstname").setValue(fName);
                dataSnapshot.getRef().child("lastname").setValue(lName);
                dataSnapshot.getRef().child("phone").setValue(phOne);
                dataSnapshot.getRef().child("postalcode").setValue(poStal);
                dataSnapshot.getRef().child("state").setValue(sTate);


                progressDialog.dismiss();
                startActivity(new Intent(EditProfile.this, Profile.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
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
        if (id == R.id.edit) {
            updateProfile();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.edit, menu);
        return true;
    }
}
