package com.cropmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNav;
    TextView textView;
    RelativeLayout relativeLayout1;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_home);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                CharSequence title = menuitem.getTitle();
                if ("Home".contentEquals(title)) {
//                    Intent intent1 = new Intent(getApplicationContext(), Home.class);
//                    startActivity(intent1);
//                    overridePendingTransition(0, 0);
                    return true;
                } else if ("Add Crop".contentEquals(title)) {
                    Intent intent2 = new Intent(getApplicationContext(), add_crop.class);
                    startActivity(intent2);
                    overridePendingTransition(0, 0);
                    return true;
                } else if ("Subsidy".contentEquals(title)) {
                    Intent intent3 = new Intent(getApplicationContext(), subsidy.class);
                    startActivity(intent3);
                    overridePendingTransition(0, 0);
                    return true;
                }
                return false;
            }
        });

        //change text of card
//        textView=findViewById(R.id.tv_crop_name);
//        textView.setText("Onion");
//        //change background of card
//        relativeLayout1=findViewById(R.id.rel_layout1);
//        relativeLayout1.setBackgroundResource(R.drawable.round_bk5);


        //-------------------------------------------------------------------------
        // Creating a new RelativeLayout
//        RelativeLayout relativeLayout = new RelativeLayout(this);
//
//        // Defining the RelativeLayout layout parameters.
//        // In this case I want to fill its parent
//        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.FILL_PARENT,
//                RelativeLayout.LayoutParams.FILL_PARENT);
//
//        // Creating a new TextView
//        TextView tv = new TextView(this);
//        tv.setText("Test");
//
//        // Defining the layout parameters of the TextView
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lp.addRule(RelativeLayout.CENTER_IN_PARENT);
//
//        // Setting the parameters on the TextView
//        tv.setLayoutParams(lp);
//
//        // Adding the TextView to the RelativeLayout as a child
//        relativeLayout.addView(tv);
//
//        // Setting the RelativeLayout as our content view
//        setContentView(relativeLayout, rlp);



//        -----------------------------------------------------------------------
        //Adding Img to Relative layout
//        ImageView iv = new ImageView(this);
//        iv.setImageResource(R.drawable.round_bk2);
//        RelativeLayout rl = (RelativeLayout) findViewById(R.id.rel_layout1);
//        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//                RelativeLayout.LayoutParams.WRAP_CONTENT,
//                RelativeLayout.LayoutParams.WRAP_CONTENT);
//        lp.addRule(RelativeLayout.FOCUSABLE);
//        lp.addRule(RelativeLayout.BELOW, R.id.tv_crop_name);
//        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//        rl.addView(iv, lp);

        //Create object of showing_details and store details of of same farmer
        

    }

}