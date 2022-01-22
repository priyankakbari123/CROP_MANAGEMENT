package com.cropmanagement;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class subsidy extends AppCompatActivity {

    BottomNavigationView bottomNav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidy);

        //BOTTOM NAVIGATION
        bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_subsidy);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                CharSequence title = menuitem.getTitle();
                if ("Home".contentEquals(title)) {
                    Intent intent1 = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent1);
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                } else if (title.equals("Add Crop")) {
                    Intent intent2 = new Intent(getApplicationContext(), add_crop.class);
                    startActivity(intent2);
                    finish();
                    overridePendingTransition(0, 0);
                    return true;
                } else if ("Subsidy".contentEquals(title)) {
//                    Intent intent3 = new Intent(getApplicationContext(), subsidy.class);
//                    overridePendingTransition(0, 0);
//                    startActivity(intent3);
                    return true;
                }
                return false;
            }
        });
    }
}