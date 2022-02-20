package com.cropmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class AdminHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_home);
    }

    public void RedirectToAddCropEvent(View view) {
        Intent intent=new Intent(getApplicationContext(),add_crop_event_admin.class);
        startActivity(intent);
        finish();
    }

    public void RedirectToLogin(View view) {
        Intent intent=new Intent(getApplicationContext(),authpage1.class);
        startActivity(intent);
        finish();
    }

    public void RedirectToAddSubsidy(View view) {
        Intent intent=new Intent(getApplicationContext(),add_subsidy_admin.class);
        startActivity(intent);
        finish();
    }
}