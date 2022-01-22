package com.cropmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_crop_event_admin extends AppCompatActivity {
    String crop_name,cropId;
    EditText day,title,details;
    DatabaseReference ref;
    Button add_details_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop_event_admin);

        day=findViewById(R.id.s_day);
        title=findViewById(R.id.s_title);
        details=findViewById(R.id.s_details);
        add_details_btn=findViewById(R.id.addDetailsBtn);

        //SPINNER FOR CROP SELECTION
        Spinner spinner=findViewById(R.id.crop_spinner_admin);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.crops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        //ON CLICK OF ADD DETAILS BUTTON
        add_details_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(!day.getText().toString().isEmpty() && !title.getText().toString().isEmpty() && !details.getText().toString().isEmpty() ){

                    //CREATE OBJECT OF CROP_SCHEDULE CLASS
                    crop_schedule schedule1=new crop_schedule();

                    //ASSIGN CROP ID ACCORDING TO CROP SELECTED
                    crop_name = (String) spinner.getSelectedItem();
                    switch (crop_name){
                        case "Cotton":
                            cropId="c1_cotton";
                            break;
                        case "Wheat":
                            cropId="c2_wheat";
                            break;
                        case "Onion":
                            cropId="c3_onion";
                            break;
                    }
                    schedule1.setCrop_id(cropId);

                    //GETTING INPUT FROM EDITTEXT AND STORED INTO OBJECTS
                    schedule1.setDay(Integer.parseInt(day.getText().toString()));
                    schedule1.setTitle(title.getText().toString());
                    schedule1.setDetails(details.getText().toString());

                    ref= FirebaseDatabase.getInstance().getReference("Schedule").child(cropId).child(Integer.toString(schedule1.getDay()));
                    ref.child("Title").setValue(schedule1.getTitle());
                    ref.child("Details").setValue(schedule1.getDetails());
                    Toast.makeText(add_crop_event_admin.this, "Details Added Successfully", Toast.LENGTH_SHORT).show();

                }else{
                    Toast.makeText(add_crop_event_admin.this, "Please Fill All the Fields ", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }
}