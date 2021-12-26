package com.cropmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class home extends AppCompatActivity {
private FirebaseAuth mFirebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFirebaseAuth=FirebaseAuth.getInstance();


    }
    @Override
    protected  void onStart(){
        super.onStart();
        if(mFirebaseAuth.getCurrentUser()!=null){
            //User is loggedIn
        }else{
            startActivity(new Intent(this,authpage1.class));
            finish();
        }
    }

    public void logout(View view) {
        mFirebaseAuth.signOut();
        startActivity(new Intent(this,authpage1.class));
        finish();
    }
}