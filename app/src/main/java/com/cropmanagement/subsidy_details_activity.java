package com.cropmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class subsidy_details_activity extends AppCompatActivity {

    DatabaseReference ref;
    ArrayList<subsidy> subsidies_list=new ArrayList<>();   //FOR STORING ALL SUBSIDIES
    int subsidyId;
    subsidy s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subsidy_details);

        subsidyId=Integer.parseInt(getIntent().getStringExtra("subsidyId"));

        ref= FirebaseDatabase.getInstance().getReference();
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DataSnapshot ds=snapshot.child("subsidy").child(Integer.toString(subsidyId));
                s=ds.getValue(subsidy.class);
                Iterable<DataSnapshot> Subsidies_all=ds.getChildren();
//                for(DataSnapshot subsidy_:Subsidies_all){
//                    subsidy s_=subsidy_.getValue(subsidy.class);
//                    subsidies_list.add(s_);
//                }
//                CREATE CARD FOR subsidies_list
//                for(subsidy s:subsidies_list){
                    create_title_card(s);
                    create_details_card(s);
                    create_procedure_card(s);
//                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    @SuppressLint("ResourceAsColor")
    void create_title_card(subsidy s){
        int subsidy_id=s.getId();
        String title=s.getTitle();

        LinearLayout linearLayout=findViewById(R.id.linear_layout);

        RelativeLayout layout = new RelativeLayout(this);
//        layout.setId(123);

        TextView text1 = new TextView(this);
        text1.setText(title); //set Crop Name
        text1.setTextColor(Color.BLACK);
        Typeface typeface = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.ptserifbold);
        }
        text1.setTypeface(typeface);
        text1.setId(subsidy_id);

        RelativeLayout.LayoutParams layout_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams subsidyTitle_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        RelativeLayout.LayoutParams btn_dim=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout_dimension.addRule(RelativeLayout.BELOW);
        layout_dimension.setMargins(17,20,17,10);

        layout.setBackgroundResource(R.drawable.green_rectanglebox); //change background based on crop name
        layout.setLayoutParams(layout_dimension);
        layout.setPadding(50,20,20,20);
        linearLayout.addView(layout,layout_dimension);

        subsidyTitle_dimension.addRule(RelativeLayout.BELOW, layout.getId());
        text1.setLayoutParams(subsidyTitle_dimension);
        text1.setPadding(25,25,25,25);
        text1.setTextSize(25);
        layout.addView(text1);

    }

    void create_details_card(subsidy s){
        int subsidy_id=s.getId();
        String details=s.getDetails();

        LinearLayout linearLayout=findViewById(R.id.linear_layout);

        RelativeLayout layout = new RelativeLayout(this);
//        layout.setId(123);

        TextView text1 = new TextView(this);
        text1.setText("Details About Subsidy");
        text1.setTextColor(Color.BLACK);
        Typeface typeface = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.apalu);
        }
        text1.setTypeface(typeface);
        text1.setId(subsidy_id);

        TextView text2 = new TextView(this);
        text2.setText(details); //set Crop Name
        text2.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.ptserifregular);
        }
        text2.setTypeface(typeface);
        text2.setId(subsidy_id+1000);

        RelativeLayout.LayoutParams layout_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams details_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams details_t_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout_dimension.addRule(RelativeLayout.BELOW);
        layout_dimension.setMargins(17,20,17,10);

        layout.setBackgroundResource(R.drawable.black_rectanglebox); //change background based on crop name
        layout.setLayoutParams(layout_dimension);
        layout.setPadding(50,20,20,30);
        linearLayout.addView(layout,layout_dimension);

        details_t_dimension.addRule(RelativeLayout.BELOW, layout.getId());
        text1.setLayoutParams(details_t_dimension);
        text1.setPadding(25,25,25,25);
        text1.setTextSize(45);
        layout.addView(text1);

        details_dimension.addRule(RelativeLayout.BELOW, subsidy_id);
        text2.setLayoutParams(details_dimension);
        text2.setPadding(25,25,25,25);
        text2.setTextSize(20);
        layout.addView(text2);

    }

    void create_procedure_card(subsidy s){
        int subsidy_id=s.getId();
        String procedure=s.getProcedure();
        String link=s.getLink();

        LinearLayout linearLayout=findViewById(R.id.linear_layout);

        RelativeLayout layout = new RelativeLayout(this);
//        layout.setId(123);

        TextView text1 = new TextView(this);
        text1.setText("Procedure to Apply");
        text1.setTextColor(Color.BLACK);
        Typeface typeface = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.apalu);
        }
        text1.setTypeface(typeface);
        text1.setId(subsidy_id);

        //Procedure
        TextView text3 = new TextView(this);
        text3.setText(procedure);
        text3.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.ptserifregular);
        }
        text3.setTypeface(typeface);
        text3.setId(subsidy_id+2000);

        //link for apply
        TextView text2 = new TextView(this);
        text2.setTextColor(Color.BLACK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            typeface = getResources().getFont(R.font.ptserifregular);
        }
        text2.setTypeface(typeface);
        text2.setId(subsidy_id+1000);

        RelativeLayout.LayoutParams layout_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams procedure_dimension = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams procedure_t_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        RelativeLayout.LayoutParams link_t_dimension=new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        layout_dimension.addRule(RelativeLayout.BELOW);
        layout_dimension.setMargins(17,20,17,10);
        layout.setBackgroundResource(R.drawable.black_rectanglebox); //change background based on crop name
        layout.setLayoutParams(layout_dimension);
        layout.setPadding(50,20,20,30);
        linearLayout.addView(layout,layout_dimension);

        //Title
        procedure_t_dimension.addRule(RelativeLayout.BELOW, layout.getId());
        text1.setLayoutParams(procedure_t_dimension);
        text1.setPadding(25,25,25,25);
        text1.setTextSize(50);
        layout.addView(text1);

        //Procedure
        link_t_dimension.addRule(RelativeLayout.BELOW,subsidy_id);
        text3.setLayoutParams(link_t_dimension);
        text3.setPadding(25,25,25,25);
        text3.setTextSize(20);
        layout.addView(text3);

        //Link for Apply
        procedure_dimension.addRule(RelativeLayout.BELOW, subsidy_id+2000);
        text2.setLayoutParams(procedure_dimension);
        text2.setPadding(25,25,25,25);
        text2.setTextSize(20);
        SpannableString spannableString=new SpannableString(
                "Apply for Subsidy"
        );
        ClickableSpan span=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(link));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                //set color
                ds.setColor(Color.BLUE);
                ds.setUnderlineText(true);
            }
        };
        spannableString.setSpan(span,0,17, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        text2.setText(spannableString); //Clickable link
        text2.setMovementMethod(LinkMovementMethod.getInstance());
        layout.addView(text2);

    }
}