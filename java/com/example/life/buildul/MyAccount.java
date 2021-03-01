package com.example.life.buildul;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.life.buildul.app.SessionManager;
import com.example.life.buildul.helper.SQLiteHandler;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MyAccount extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView username, email, toOrders;
    private Button btnLogout, callService;
    private SQLiteHandler db;
    private SessionManager session;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private String UID, Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            startActivity(new Intent(MyAccount.this, Login.class));
            finish();
        }else {
            final String WelcomMail = mAuth.getCurrentUser().getEmail();
        }
        setContentView(R.layout.activity_my_account);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //if the user is not logged in
        /*Firebase ref = new Firebase("https://buildul-8e6d3.firebaseio.com");
        ref.addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                if (authData != null){
                    return;
                }else{
                    startActivity(new Intent(MyAccount.this, Login.class));
                }
            }
        });*/
        //starting the login activity

        Bundle retrieveData3 = getIntent().getExtras();
        Email = retrieveData3.getString("email");
        UID = retrieveData3.getString("UID");

        username = findViewById(R.id.full_name);
        email = findViewById(R.id.email);
        btnLogout = findViewById(R.id.logout_button);
        callService = (Button) findViewById(R.id.service);
        toOrders = (TextView) findViewById(R.id.orders);

        mAuth = FirebaseAuth.getInstance();
        //getting the current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            email.setText((CharSequence) user.getEmail());
        }
        /*authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user1 = firebaseAuth.getCurrentUser();
                if (user == null){
                    //user auth is changed
                    startActivity(new Intent(MyAccount.this, Login.class));
                    finish();
                }else{
                    email.setText(user.getEmail());
                }
            }
        };*/

        toOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MyAccount.this, MyOrders.class);
                startActivity(i);
            }
        });
        callService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent call = new Intent(Intent.ACTION_CALL);

                call.setData(Uri.parse("tel:07060856279"));
                if (ActivityCompat.checkSelfPermission(MyAccount.this,
                        Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    return;
                }
                startActivity(call);*/

                AlertDialog.Builder alert = new AlertDialog.Builder(MyAccount.this);
                alert.setTitle("Alert")
                        .setIcon(R.drawable.buildul)
                        .setMessage("Call our Customer Care team" +
                                " on 07033838118")
                        .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(MyAccount.this, MainActivity.class);
                                startActivity(i);
                            }
                        });
                alert.show();
            }
        });

        //Logout button click event
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(MyAccount.this);
                alertDialog.setMessage("You will be Signed out");
                alertDialog.setCancelable(true);
                alertDialog.setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                FirebaseAuth.getInstance().signOut();
                                Intent i = new Intent(MyAccount.this, Login.class);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(i);
                            }
                        });
                alertDialog.setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                alertDialog.show();
                

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
        if (id == R.id.cart) {
            Intent i = new Intent(MyAccount.this, Cart.class);
            startActivity(i);
            return true;
        }else if (id == R.id.home){
            Intent i = new Intent(MyAccount.this, MainActivity.class);
            startActivity(i);
        }else if (id == R.id.profile){
            Intent i = new Intent(MyAccount.this, Profile.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.account, menu);
        return true;
    }

    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
