package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

import static android.provider.ContactsContract.Intents.Insert.EMAIL;

public class Login extends AppCompatActivity {
    private TextView registerLink, forgotPassword;
    private LoginButton fbLogin;
    private TextView loginStatus;
    CallbackManager callbackManager;
    private EditText UIemail, UIpassword;
    private ProgressDialog progressDialog;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() != null){
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();
        }
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);


        UIemail = (EditText) findViewById(R.id.email);
        UIpassword = (EditText) findViewById(R.id.password);
        registerLink = (TextView) findViewById(R.id.register_link);

        forgotPassword = (TextView) findViewById(R.id.forgot_password);
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });
        loginButton = (Button) findViewById(R.id.login);
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() != null){
                    startActivity(new Intent(Login.this, MyAccount.class));
                    finish();
                }
            }
        };
        registerLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, SignUp.class);
                startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userLogin();
            }
        });
        //fbLogin();

    }

    private void userLogin() {
        //first getting the values
        final String email = UIemail.getText().toString();
        final String password = UIpassword.getText().toString();

        //validating inputs
        if (TextUtils.isEmpty(email)) {
            UIemail.setError("Please enter your username");
            UIemail.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            UIpassword.setError("Please enter your password ");
            UIpassword.requestFocus();
            return;
        }


        progressDialog = new ProgressDialog(Login.this);
        progressDialog.setMessage("Just in a Moment");
        progressDialog.setTitle("Logging You Into Buildul");
        progressDialog.show();

         mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            progressDialog.dismiss();
                            FirebaseUser user = mAuth.getCurrentUser();
                            if (user !=  null){
                                Intent i = new Intent(Login.this, MyAccount.class);
                                Toast.makeText(Login.this, "Login Successful", Toast.LENGTH_LONG).show();
                                startActivity(i);
                            }
                            else{
                                Toast.makeText(Login.this, "Unable to Obtain User Details", Toast.LENGTH_LONG).show();
                            }

                        }else{
                            //If signin fails display a message to the user
                            progressDialog.dismiss();
                            Toast.makeText(Login.this, "User details wrong", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    /*@Override
    protected void onStart(){
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }*/

    /*@Override
    public void onStop(){
        super.onStop();
        if (authStateListener != null){
            mAuth.removeAuthStateListener(authStateListener);
        }
    }*/
    /*public void fbLogin(){
        loginStatus = (TextView) findViewById(R.id.status);

        fbLogin = (LoginButton) findViewById(R.id.facebook_login);
        callbackManager = CallbackManager.Factory.create();
        fbLogin.setReadPermissions(Arrays.asList(EMAIL));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        fbLogin.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                loginStatus.setText("Login Success \n"+
                        loginResult.getAccessToken().getUserId() +
                        "\n" + loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                // App code
                loginStatus.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });


        AccessToken accessToken = AccessToken.getCurrentAccessToken();
        boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
    }
*/
    public void onBackPressed(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }
}
