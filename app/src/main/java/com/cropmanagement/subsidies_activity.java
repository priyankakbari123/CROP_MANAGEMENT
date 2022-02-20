package com.cropmanagement;

import static android.content.Intent.FLAG_ACTIVITY_NO_ANIMATION;

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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class subsidies_activity extends AppCompatActivity {

    BottomNavigationView bottomNav;
    DatabaseReference ref;
    ArrayList<subsidy> subsidies_list=new ArrayList<>();   //FOR STORING ALL SUBSIDIES
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
//                    Intent intent3 = new Intent(getApplicationContext(), subsidies_activity.class);
//                    overridePendingTransition(0, 0);
//                    startActivity(intent3);
                    return true;
                }
                return false;
            }
        });

        ref=FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot ds=snapshot.child("subsidy");
                Iterable<DataSnapshot> Subsidies_all=ds.getChildren();
                for(DataSnapshot subsidy_:Subsidies_all){
                    subsidy s_=subsidy_.getValue(subsidy.class);
                    subsidies_list.add(s_);
                }
//                CREATE CARD FOR subsidies_list
                for(subsidy s:subsidies_list){
                    create_card(s);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //ADDING LOGOUT MENU ----------------------------------------------------------------------------
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.logout_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        FirebaseAuth mFirebaseAuth= FirebaseAuth.getInstance();
        if(item.getItemId()==R.id.logout){
            mFirebaseAuth.signOut();
            startActivity(new Intent(this,authpage1.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    //METHOD FOR CREATING CARD FOR SUBSIDY
    @SuppressLint("ResourceAsColor")
    void create_card(subsidy s){
        int subsidy_id=s.getId();
        String title=s.getTitle();

        LinearLayout linearLayout=findViewById(R.id.linear_layout);

        RelativeLayout layout = new RelativeLayout(this);
//        layout.setId(123);

        TextView text1 = new TextView(this);
        text1.setText(title); //set Crop Name
        text1.setTextColor(Color.BLACK);
        text1.setShadowLayer(2,2,2,Color.BLACK);
        text1.setId(subsidy_id);

        Button view_schedule_btn=new Button(this);
        view_schedule_btn.setText("VIEW DETAILS");
        view_schedule_btn.setTextColor(R.color.greenisblue);
        view_schedule_btn.setId(subsidy_id);

        RelativeLayout.LayoutParams layout_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams subsidyTitle_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams btn_dim=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout_dimension.addRule(RelativeLayout.BELOW);
        layout_dimension.setMargins(17,20,17,10);

        layout.setBackgroundResource(R.drawable.subsidy_cardbg); //change background based on crop name
        layout.setLayoutParams(layout_dimension);
        layout.setPadding(50,20,20,20);
        linearLayout.addView(layout,layout_dimension);

        subsidyTitle_dimension.addRule(RelativeLayout.BELOW, layout.getId());
        text1.setLayoutParams(subsidyTitle_dimension);
        text1.setPadding(25,25,25,25);
        text1.setTextSize(20);
        layout.addView(text1);

        btn_dim.addRule(RelativeLayout.BELOW,text1.getId());
        view_schedule_btn.setLayoutParams(btn_dim);
        view_schedule_btn.setPadding(20,250,20,20);
        view_schedule_btn.setBackgroundResource(00000000);
        layout.addView(view_schedule_btn);

        view_schedule_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), subsidy_details_activity.class);
                intent.putExtra("subsidyId",Integer.toString(s.getId()));
                startActivity(intent);
            }
        });
    }
}