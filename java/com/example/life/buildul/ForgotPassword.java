package com.example.life.buildul;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressBar progressBar;
    private EditText enterEmail;
    private Button buttonReset;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mAuth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        enterEmail = (EditText) findViewById(R.id.email_reset);
        buttonReset = (Button) findViewById(R.id.reset);

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = enterEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    enterEmail.setError("Enter a Registered Email");
                    enterEmail.requestFocus();
                }

                progressBar.setVisibility(View.VISIBLE);
                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()){
                                    Toast.makeText(ForgotPassword.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ForgotPassword.this, "Failed to reset password", Toast.LENGTH_SHORT).show();
                                }


                                progressBar.setVisibility(View.GONE);
                            }
                        });
            }
        });
    }
}
