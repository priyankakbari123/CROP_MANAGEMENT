package com.cropmanagement;

import static com.cropmanagement.R.id.*;

import static java.util.Objects.*;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.Vector;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNav;
    TextView textView;
    RelativeLayout relativeLayout1;
    DatabaseReference reference;
    private FirebaseAuth mFirebaseAuth;
    int s_id;
    DataSnapshot key;
    ArrayList<sowing_details> sd_list=new ArrayList<>();//LIST FOR STORING SOWING DETAILS OF CURRENT FARMER

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //CHECK IF USER IS LOGGED IN OR NOT
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user==null){
            Intent intent = new Intent(getApplicationContext(), authpage1.class);
            startActivity(intent);
            finish();
        }else{
            //IF USER IS LOGGED IN
            bottomNav=findViewById(bottom_nav);
            bottomNav.setSelectedItemId(nav_home);
            bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                    CharSequence title = menuitem.getTitle();
                    if ("Home".contentEquals(title)) {
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

            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            String UID = currentFirebaseUser.getUid();
//        //getting no. of entries of sowing details
            s_id=getMaxSowingId(reference);


            reference= FirebaseDatabase.getInstance().getReference().child("sowing_details");
            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    DataSnapshot uidSnapshot=snapshot.child(UID);
                    Iterable<DataSnapshot> UIDChildren=uidSnapshot.getChildren();

                    for(DataSnapshot sd:UIDChildren){
                        sowing_details sd_obj=sd.getValue(sowing_details.class);
                        sd_obj.setSowing_id(Integer.parseInt(sd.getKey()));
                        Log.d("CROPID:", sd_obj.crop_id);
                        sd_list.add(sd_obj);
                    }
                    //CREATE CARD FOR EVERY CROP
                    for(sowing_details sd:sd_list){
                        create_card(sd);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }



    }


    //ADDING LOGOUT MENU-------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        mFirebaseAuth=FirebaseAuth.getInstance();
            if(item.getItemId()==R.id.logout){
                mFirebaseAuth.signOut();
                startActivity(new Intent(this,authpage1.class));
                finish();
                return true;
            }
        return super.onOptionsItemSelected(item);
    }

    //METHOD FOR CREATING CARD for CROPS----------------------------------------------------------------------
    //showing_id_c for button id
    void create_card(sowing_details sd){
        int sowing_id_c=sd.sowing_id,RID_bg;
        String crop_name_c=getCropName(sd.crop_id),cropID=sd.crop_id;
        String crop_name_farmName="( "+sd.getFarm_name()+" )";
        //CREATE DYNAMIC CARD
        RID_bg=getCardBackground(cropID);

        LinearLayout linearLayout=findViewById(R.id.linear_layout);

        RelativeLayout layout = new RelativeLayout(this);
//        layout.setId(123);

        TextView text1 = new TextView(this);
        text1.setText(crop_name_c); //set Crop Name
        text1.setTextColor(Color.WHITE);
        text1.setShadowLayer(2,2,2,Color.BLACK);
        text1.setId(sowing_id_c);

        TextView text2 = new TextView(this);
        text2.setText(crop_name_farmName); //set Farm Name
        text2.setTextColor(Color.WHITE);
        text2.setTextSize(12);
        text2.setShadowLayer(2,2,2,Color.BLACK);
        text2.setId(sowing_id_c+100);

        Button view_schedule_btn=new Button(this);
        view_schedule_btn.setText("VIEW SCHEDULE");
        view_schedule_btn.setTextColor(Color.WHITE);
        view_schedule_btn.setId(sowing_id_c);

        RelativeLayout.LayoutParams layout_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,370);
        RelativeLayout.LayoutParams cropname_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams farmname_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams btn_dim=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout_dimension.addRule(RelativeLayout.BELOW);
        layout_dimension.setMargins(17,50,17,10);

        layout.setBackgroundResource(RID_bg); //change background based on crop name
        layout.setLayoutParams(layout_dimension);
        layout.setPadding(50,20,20,20);
        linearLayout.addView(layout,layout_dimension);

        cropname_dimension.addRule(RelativeLayout.BELOW, layout.getId());
        text1.setLayoutParams(cropname_dimension);
        text1.setPadding(25,45,25,25);
        text1.setTextSize(40);
        layout.addView(text1);

        farmname_dimension.addRule(RelativeLayout.RIGHT_OF, sowing_id_c);
        text2.setLayoutParams(cropname_dimension);
        text2.setPadding(30,20,25,25);
        layout.addView(text2);

        btn_dim.addRule(RelativeLayout.BELOW,text1.getId());
        view_schedule_btn.setLayoutParams(btn_dim);
        view_schedule_btn.setPadding(20,250,20,20);
        view_schedule_btn.setBackgroundResource(00000000);
        layout.addView(view_schedule_btn);

        view_schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CreateSchedule(sd);
            }
        });
    }

    //GETTING MAX SOWING_ID ----------------------------------------------------------------------------
    int getMaxSowingId(DatabaseReference reference){
        reference= FirebaseDatabase.getInstance().getReference().child("sowing_details_ID");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                s_id=snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return s_id;
    }

    //GET CROP NAME FROM CROP ID
    String getCropName(String CropID){
        switch (CropID){
            case "c1_cotton":
                return "Cotton";
            case "c2_wheat":
                return "Wheat";
            case "c3_onion":
                return "Onion";
        }
        return "Something Went Wrong";
    }

    //GETTING BACKGROUND ACCORDING TO CROPID
    int getCardBackground(String CropID){
        switch (CropID){
            case "c1_cotton":
                return R.drawable.cotton_bg;
            case "c2_wheat":
                return R.drawable.wheat_bg;
            case "c3_onion":
                return R.drawable.onion_bg;
        }
        return R.drawable.round_bk3;
    }

    //CREATE SCHEDULE for Selected Crop
    void CreateSchedule(sowing_details sd1){
        Intent schedule_intent=new Intent(getApplicationContext(),schedule.class);
        schedule_intent.putExtra("sowing_id", Integer.toString(sd1.sowing_id));
        schedule_intent.putExtra("crop_id", sd1.crop_id);
        startActivity(schedule_intent);
    }


}