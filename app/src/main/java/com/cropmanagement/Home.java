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
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.Objects;
import java.util.Vector;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNav;
    TextView textView;
    RelativeLayout relativeLayout1;
    DatabaseReference reference;
    int s_id;
    DataSnapshot key;

    @SuppressLint({"SetTextI18n", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        bottomNav=findViewById(bottom_nav);
        bottomNav.setSelectedItemId(nav_home);
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

        //--------------------------------------------------------------------------

        //Create object of showing_details and store details of of same farmer
//        Vector<sowing_details> crops_sowned;    //vector for storing crops of particular farmer
//
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String UID = currentFirebaseUser.getUid();
//        //getting no. of entries of sowing details
        s_id=getMaxSowingId(reference);

        ArrayList<sowing_details> sd_list=new ArrayList<>();//LIST FOR STORING SOWING DETAILS OF CURRENT FARMER
        reference= FirebaseDatabase.getInstance().getReference().child("sowing_details");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for(DataSnapshot snapshot1:snapshot.getChildren()){
//                    sowing_details sd=new sowing_details();
//                    key=snapshot1.getValue(DataSnapshot.class);
//                        key.child("crop_name");
//                    for(DataSnapshot temp:key){
//                        int val=temp.getValue(Integer.class);
//                    }
////                    key=snapshot1.getKey();
////                    reference.child(requireNonNull(snapshot1.getValue(Integer.class)));
//                }
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
                    create_card(sd.sowing_id,getCropName(sd.crop_id),sd.crop_id);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //remaining from here

//        for(int i=1;i<=s_id;i++){
//            reference.child(Integer.toString(i)).child("farmer_id");
//            reference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    currFarmer_id=snapshot.getValue(String.class);
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }


        //Card Creation for CROPS



    }

    //METHOD FOR CREATING CARD for CROPS----------------------------------------------------------------------
    //showing_id_c for button id
    void create_card(int sowing_id_c,String crop_name_c,String cropID){
        //CREATE DYNAMIC CARD
        int RID_bg=getCardBackground(cropID);


        LinearLayout linearLayout=findViewById(R.id.linear_layout);

        RelativeLayout layout = new RelativeLayout(this);
//        layout.setId(123);
        TextView text1 = new TextView(this);
        text1.setText(crop_name_c); //set Crop Name
        text1.setTextColor(Color.WHITE);
        text1.setShadowLayer(2,2,2,Color.BLACK);
        text1.setId(sowing_id_c);

        Button view_schedule_btn=new Button(this);
        view_schedule_btn.setText("VIEW SCHEDULE");
        view_schedule_btn.setTextColor(Color.WHITE);
        view_schedule_btn.setId(sowing_id_c);

        RelativeLayout.LayoutParams layout_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,370);
        RelativeLayout.LayoutParams cropname_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams btn_dim=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout_dimension.addRule(RelativeLayout.BELOW);
        layout_dimension.setMargins(17,50,17,10);

        layout.setBackgroundResource(RID_bg); //change background based on crop name
        layout.setLayoutParams(layout_dimension);
        layout.setPadding(50,20,20,20);
        linearLayout.addView(layout,layout_dimension);

        cropname_dimension.addRule(RelativeLayout.BELOW, layout.getId());
        text1.setLayoutParams(cropname_dimension);
        text1.setPadding(25,25,25,25);
        text1.setTextSize(40);
        layout.addView(text1);

        btn_dim.addRule(RelativeLayout.BELOW,text1.getId());
        view_schedule_btn.setLayoutParams(btn_dim);
        view_schedule_btn.setPadding(20,250,20,20);
        view_schedule_btn.setBackgroundResource(00000000);
        layout.addView(view_schedule_btn);


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


}