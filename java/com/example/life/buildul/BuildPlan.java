package com.example.life.buildul;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.life.buildul.app.Feedback;
import com.example.life.buildul.app.ReturnCart;
import com.example.life.buildul.app.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

//import net.sargue.mailgun.Mail;

public class BuildPlan extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private FirebaseDatabase database;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private String UID, Email, nameString, emailString, completeString;
    private EditText nameFeedBack, emailFeedBack, complete;
    private ProgressDialog progressDialog;
    private Button getPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        if (mAuth.getCurrentUser() == null){
            UID = mAuth.getCurrentUser().getUid();
            startActivity(new Intent(BuildPlan.this, Login.class));
            finish();
        }else {
            final String WelcomMail = mAuth.getCurrentUser().getEmail();
        }
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("Feedback");
        setContentView(R.layout.activity_build_plan);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        UID = mAuth.getCurrentUser().getUid();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        getPlan = (Button) findViewById(R.id.Get_plan_now);
        getPlan.setOnClickListener(this);
    }

    public void onClick(View view) {
        //Start progress Dialog
        progressDialog = new ProgressDialog(BuildPlan.this);
        progressDialog.setTitle("Sending feedback");
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        nameFeedBack = (EditText) findViewById(R.id.name_user);
        emailFeedBack = (EditText) findViewById(R.id.email_user);
        complete = (EditText) findViewById(R.id.description_user);

        nameString = nameFeedBack.getText().toString();
        emailString = emailFeedBack.getText().toString();
        completeString = complete.getText().toString();
        if (TextUtils.isEmpty(nameString)){
            nameFeedBack.setError("Enter Your Name");
            nameFeedBack.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(emailString)){
            emailFeedBack.setError("Enter Your Email");
            emailFeedBack.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(completeString)){
            complete.setError("Enter Your Feedback Note");
            complete.requestFocus();
        }

                /*net.sargue.mailgun.Configuration configuration = new net.sargue.mailgun.Configuration()
                        .domain("sandboxb6af4c3db0e642e2b6ab2d22a0707301.mailgun.org")
                        .apiKey("pubkey-93bcba501f7021ee709a0b36f4b83b6b")
                        .from(nameString, emailString);

                Mail.using(configuration)
                        .to("ojiakufavour@gmail.com")
                        .subject("Buildul compliance")
                        .text(completeString)
                        .build()
                        .send();*/
        Fedback(nameString, emailString, completeString);


    }

    private void Fedback(String name, String email, String feedback) {
        //progressDialog.dismiss();
        String key = reference.child("Feedback").push().getKey();
        Feedback control = new Feedback(name, email, feedback);

        Map<String, Object> getCart = control.toMap();

        Map<String, Object> childCart = new HashMap<>();
        //childCart.put("/Carts/"+ key, getCart);
        childCart.put("Feedbacks" + UID + "/BuildPlan" + key, getCart);
        reference.updateChildren(childCart);
        progressDialog.dismiss();
        AlertDialog.Builder alert = new AlertDialog.Builder(BuildPlan.this);
        alert.setTitle("Alert")
                .setIcon(R.drawable.buildul)
                .setMessage("Thank you for interest, Your message will be processed shortly")
                .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent i = new Intent(BuildPlan.this, MainActivity.class);
                        startActivity(i);
                    }
                });
        alert.show();
    }

        public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
}
