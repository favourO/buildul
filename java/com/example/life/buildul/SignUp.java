package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.models.ProfileActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUp extends AppCompatActivity {
    private static final String TAG = SignUp.class.getSimpleName();
    private Toolbar toolbar;
    private TextView linkToLogin, linkAgreement;
    private EditText Bpassword, Bemail;
    private Button register;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //if the user is already logged in we will directly start the profile activity

        // errorMsg = (TextView) findViewById(R.id.textinput_error);

        linkAgreement = (TextView) findViewById(R.id.accept);
        linkAgreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(SignUp.this, PrivacyPolicy.class);
                startActivity(i
                );
            }
        });

        linkToLogin = (TextView) findViewById(R.id.login_link);
        linkToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, Login.class);
                startActivity(intent);
                finish();
            }
        });

        //show password
        Bemail = (EditText) findViewById(R.id.email);
        Bpassword = (EditText) findViewById(R.id.password);

        register = (Button) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String email = Bemail.getText().toString().trim();
        final String password = Bpassword.getText().toString().trim();

        //first we will do the validations

        if (TextUtils.isEmpty(email)) {
            Bemail.setError("Please enter your email");
            Bemail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Bemail.setError("Enter a valid email");
            Bemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            Bpassword.setError("Enter a password");
            Bpassword.requestFocus();
            return;
        }

        progressDialog = new ProgressDialog(SignUp.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Registering You... Just in a Moment");
        progressDialog.show();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override

                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            //Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmailAndPassword: success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user !=  null){
                                String email = user.getEmail();
                                String UID = user.getUid();
                                Intent intent = new Intent(SignUp.this, Setup.class);
                                intent.putExtra("email", email);
                                intent.putExtra("UID", UID);
                                Toast.makeText(SignUp.this, "Sign Up Successful, you will be automatically logged in ", Toast.LENGTH_LONG).show();
                                startActivity(intent);
                            }
                            else{
                                Toast.makeText(SignUp.this, "Unable to Obtain User Details... Make sure you have a secured internet connection" +
                                        "", Toast.LENGTH_LONG).show();
                            }
                        } else{
                            //If signin fails display a message to the user
                            progressDialog.dismiss();
                            Log.d(TAG, "createUserWithEmailAndPassword: Failure", task.getException());
                            Toast.makeText(SignUp.this, "User already exist... Make sure you have a secured internet connection", Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.order, menu);
        return true;
    }

}
