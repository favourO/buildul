package com.example.life.buildul;

import android.app.Application;
import android.content.Intent;
import android.util.Log;

import com.firebase.client.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

public class Buildul extends Application {

    @Override
    public void onCreate(){
        super.onCreate();
        if (!FirebaseApp.getApps(this).isEmpty())
            FirebaseDatabase.getInstance().setPersistenceEnabled(true);

        //FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        //FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        /*if (firebaseUser != null){
            startActivity(new Intent(Buildul.this, MainActivity.class));
        }*/
    }
}
