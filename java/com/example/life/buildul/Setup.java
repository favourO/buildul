package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.app.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

public class Setup extends AppCompatActivity implements View.OnClickListener {
    private DatabaseReference mDatabase;
    private TextView emailFromServer;
    private EditText Firstname, Lastname, Phone, Address, City, State, PostalCode;
    private ProgressDialog progressDialog;
    private Button button;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    private String UID, Email;
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
             databaseReference = database.getReference("Users");

        }

        setContentView(R.layout.activity_setup);

        emailFromServer = (TextView) findViewById(R.id.status);
        emailFromServer.setText(Email);

        button = (Button) findViewById(R.id.finish_login);
        button.setOnClickListener(this);

    }

    @Override
    public void onClick(View view){
        //Start progress Dialog
        Firstname = (EditText) findViewById(R.id.firstname);
        Lastname = (EditText) findViewById(R.id.lastname);
        Phone = (EditText) findViewById(R.id.phone);
        Address = (EditText) findViewById(R.id.address);
        City = (EditText) findViewById(R.id.city);
        State = (EditText) findViewById(R.id.state);
        PostalCode = (EditText) findViewById(R.id.postal);

        final String firstn = Firstname.getText().toString().trim();
        final String lastn = Lastname.getText().toString().trim();
        final String phoneN = Phone.getText().toString().trim();
        final String addres = Address.getText().toString().trim();
        final String city = City.getText().toString().trim();
        final String state = State.getText().toString().trim();
        final String postal = PostalCode.getText().toString().trim();

        if (TextUtils.isEmpty(firstn)){
            Firstname.setError("Enter Your First Name");
            Firstname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastn)){
            Lastname.setError("Enter Your Last Name");
            Lastname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phoneN)){
            Phone.setError("Enter Your Phone Number");
            Phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(addres)){
            Address.setError("Enter Your Address");
            Address.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(city)){
            City.setError("Enter Your City");
            City.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(state)){
            Firstname.setError("Enter Your State");
            Firstname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(postal)){
            PostalCode.setError("Enter a valid Postal Code");
            PostalCode.requestFocus();
        }

        progressDialog = new ProgressDialog(Setup.this);
        progressDialog.setTitle("Setting Up your Account");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

    databaseReference.child("Users").child(UID).addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

            if (dataSnapshot.child(UID).exists()){
                progressDialog.dismiss();
            }else {
                progressDialog.dismiss();
                User user = new User(addres, city, Email, firstn, lastn ,phoneN , postal, state);
                databaseReference.child(UID).setValue(user);
                LayoutInflater inflater = getLayoutInflater();
                View layout = inflater.inflate(R.layout.toast_layout,
                        (ViewGroup) findViewById(R.id.custom_toast));

                TextView textView = (TextView) layout.findViewById(R.id.text);
                textView.setText(R.string.custom_toast);

                Toast toast = new Toast(getApplicationContext());
                toast.setDuration(Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                toast.setView(layout);
                toast.show();
                Intent i = new Intent(Setup.this, Profile.class);
                startActivity(i);
                finish();
            }
        }


        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

}

    /*public void setupProfile(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Setting Up your Account");
        progressDialog.setMessage("Please Wait...");

        Firstname = (EditText) findViewById(R.id.firstname);
        Lastname = (EditText) findViewById(R.id.lastname);
        Phone = (EditText) findViewById(R.id.phone);
        Address = (EditText) findViewById(R.id.address);
        City = (EditText) findViewById(R.id.city);
        State = (EditText) findViewById(R.id.state);
        PostalCode = (EditText) findViewById(R.id.postal);

        String firstn = Firstname.getText().toString().trim();
        String lastn = Lastname.getText().toString().trim();
        String phoneN = Phone.getText().toString().trim();
        String addres = Address.getText().toString().trim();
        String city = City.getText().toString().trim();
        String state = State.getText().toString().trim();
        String postal = PostalCode.getText().toString().trim();

        if (TextUtils.isEmpty(firstn)){
            Firstname.setError("Enter Your First Name");
            Firstname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(lastn)){
            Lastname.setError("Enter Your Last Name");
            Lastname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phoneN)){
            Phone.setError("Enter Your Phone Number");
            Phone.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(addres)){
            Address.setError("Enter Your Address");
            Address.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(city)){
            City.setError("Enter Your City");
            City.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(state)){
            Firstname.setError("Enter Your State");
            Firstname.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(postal)){
            PostalCode.setError("Enter a valid Postal Code");
            PostalCode.requestFocus();
        }
        writeNewUser(UID, Email, firstn, lastn, phoneN, addres, city, state, postal);

    }


    private void writeNewUser(String userid, String email, String firstname, String lastname, String phone, String address, String city, String state, String postalCode){
        User user = new User(email, firstname, lastname, phone, address, city, state, postalCode);

        mDatabase.child("Users").child(userid).setValue(user)
        .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(@NonNull Void aVoid) {
                //Write was successful
                progressDialog.dismiss();
                customToastSuccess();
                Intent intent = new Intent(Setup.this, Profile.class);
                startActivity(intent);
            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                customToastFailure();
            }
        });
    }
*/
    private void customToastSuccess(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.custom_toast));

        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(R.string.custom_toast);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }

    private void customToastFailure(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.custom_toast));

        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(R.string.custom_toast_fail);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }

    public void customToastFailedToUpdateProfile(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.custom_toast));

        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(R.string.custom_toast_fail_to_load_profile);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }

    public void customNetworkError(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.custom_toast));

        TextView textView = (TextView) layout.findViewById(R.id.text);
        textView.setText(R.string.custom_toast_network_error);

        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.setView(layout);
        toast.show();
    }

}
