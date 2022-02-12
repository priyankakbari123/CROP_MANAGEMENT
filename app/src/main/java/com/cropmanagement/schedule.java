package com.cropmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class schedule extends AppCompatActivity {
    String UID,sid,crop_id;
    DatabaseReference reference,ref1;
    sowing_details sd_obj;  //storing selected crop's sowing details
    ArrayList<crop_schedule> schedule_list=new ArrayList<>();   //For storing schedule details of selected crop
    TextView cropName_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        //GETTING SOWING ID & CROP_ID for SELECTED CROP
        sid=getIntent().getStringExtra("sowing_id");
        crop_id=getIntent().getStringExtra("crop_id");

        //SETTING CROPNAME ON SCHEDULE PAGE
        cropName_tv=findViewById(R.id.crop_schedule_cropName);


        //GETTING CURRENT FARMER'S ID
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        UID = currentFirebaseUser.getUid();


        reference= FirebaseDatabase.getInstance().getReference();
        reference.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //getting Selected Crop's Sowing Details
                DataSnapshot sowingIdSnapshot=snapshot.child("sowing_details").child(UID).child(sid);
                sd_obj=sowingIdSnapshot.getValue(sowing_details.class);

                //setting cropName and Farm Name on Schedule Page
                cropName_tv.setText(getCropName(crop_id)+" ("+sd_obj.getFarm_name()+")");

                //Getting Schedule information for Selected Crop
                DataSnapshot cropSnapshot=snapshot.child("Schedule").child(crop_id);
                Iterable<DataSnapshot> ScheduleDays=cropSnapshot.getChildren();

                for(DataSnapshot schedule:ScheduleDays){
                    crop_schedule s1=schedule.getValue(crop_schedule.class);
                    s1.setDay(Integer.parseInt(schedule.getKey()));
                    schedule_list.add(s1);

                }

                //making schedule and make card for each event
                for(crop_schedule cs:schedule_list){
//                    Log.d("DETAILS: ",cs.Details);
                    Date scheduleDate=addDays(sd_obj.getSowing_date(),cs.day);
                    create_card(scheduleDate,cs);
                }

//                Log.d("LOOP COMPLETED",sd_obj.crop_id);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

    //ADDING DAYS TO SOWING DATE TO MAKE SCHEDULE
    Date addDays(Date sowing_date,int days){
        Calendar cal=Calendar.getInstance();
        cal.setTime(sowing_date);
        cal.add(Calendar.DAY_OF_MONTH,days);
        Date tDate=cal.getTime();
        return tDate;
    }

    //METHOD FOR CREATING CARD for CROPS----------------------------------------------------------------------

    void create_card(Date scheduleDate,crop_schedule cs){
        //Format Date
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

        int text_id=cs.day,RID_bg;
        String date_c,title_c=cs.Title,details_c=cs.getDetails();
        date_c=sdf.format(scheduleDate)+" (Day "+cs.day+")";
        //CREATE DYNAMIC CARD
//        RID_bg=getCardBackground(cropID);

        LinearLayout linearLayout=findViewById(R.id.schedule_ll);

        RelativeLayout layout = new RelativeLayout(this);
//        layout.setId(123);

        TextView text1 = new TextView(this);
        text1.setText(date_c); //set Date
        text1.setTextColor(Color.BLACK);
        text1.setTextSize(22);
//        text1.setShadowLayer(2,2,2,Color.BLACK);
        text1.setId(text_id);

        TextView text2 = new TextView(this);
        text2.setText(title_c); //set Title(Event Title)
        text2.setTextColor(Color.BLACK);
        text2.setTextSize(23);
//        text2.setShadowLayer(2,2,2,Color.BLACK);
        text2.setId(text_id+1000);

        TextView text3 = new TextView(this);
        text3.setText(details_c); //set Detail(Event Details)
        text3.setTextColor(Color.BLACK);
        text3.setTextSize(14);
//        text3.setShadowLayer(2,2,2,Color.BLACK);
        text3.setId(text_id+2000);

        RelativeLayout.LayoutParams layout_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,420);
        RelativeLayout.LayoutParams date_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams title_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams details_dim=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout_dimension.addRule(RelativeLayout.BELOW);
        layout_dimension.setMargins(17,50,17,10);

        layout.setBackgroundResource(R.drawable.template); //change background based on crop name
        layout.setLayoutParams(layout_dimension);
        layout.setPadding(50,20,20,20);
        linearLayout.addView(layout,layout_dimension);

        date_dimension.addRule(RelativeLayout.BELOW, layout.getId());
        text1.setLayoutParams(date_dimension);
        text1.setPadding(25,45,25,25);;
        layout.addView(text1);

        title_dimension.addRule(RelativeLayout.BELOW, text_id);
        text2.setLayoutParams(title_dimension);
        text2.setPadding(25,30,25,0);
        layout.addView(text2);

        details_dim.addRule(RelativeLayout.BELOW, text_id+1000);
        text3.setLayoutParams(details_dim);
        text3.setPadding(25,25,25,0);
        layout.addView(text3);

    }
}