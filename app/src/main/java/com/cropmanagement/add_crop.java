package com.cropmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class add_crop extends AppCompatActivity {
    public String crop_name,UID,cropId,farm_name;
    private FirebaseAuth mFirebaseAuth;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    public int area;
    EditText farmArea,farmName;
    FirebaseDatabase rootNode;
    DatabaseReference reference,reference1,crop_ref;
    BottomNavigationView bottomNav;
    int s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_crop);
        mFirebaseAuth=FirebaseAuth.getInstance();

        crop_ref=FirebaseDatabase.getInstance().getReference().child("crops");

        //BOTTOM NAVIGATION
        bottomNav=findViewById(R.id.bottom_nav);
        bottomNav.setSelectedItemId(R.id.nav_add_crop);
        bottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuitem) {
                CharSequence title = menuitem.getTitle();
                if ("Home".contentEquals(title)) {
                    Intent intent1 = new Intent(getApplicationContext(), Home.class);
                    startActivity(intent1);
                    overridePendingTransition(0, 0);
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


//GETTING MAX SOWING_ID ----------------------------------------------------------------------------
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

//------------------------------------------------------------------------------------------------------------------------------------------
        //SPINNER FOR CROP SELECTION
        Spinner spinner=findViewById(R.id.crop_spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.crops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//------------------------------------------------------------------------------------------------------------------------------------------
//        LOGOUT BUTTON ON CLICK
//        final Button logoutbtn=findViewById(R.id.logout);
//        logoutbtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                logout(view);
//            }
//        });

//------------------------------------------------------------------------------------------------------------------------------------------
        //ONCLICK ON ADD CROP BUTTON
        final Button addcropbtn=findViewById(R.id.addcropbtn);
        addcropbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                sowing_details sd = new sowing_details();


//ASSIGN CROP ID ACCORDING TO CROP SELECTED
                crop_name = (String) spinner.getSelectedItem();
                cropId=GetCropID(crop_name);

//                crop_ref=crop_ref.child(crop_name).child("crop_id");
//                crop_ref.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                        cropId=snapshot.getValue().toString();
//                        Toast.makeText(add_crop.this, snapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
                sd.setCrop_id(cropId);

                Calendar cal = Calendar.getInstance();
                String s = (String) dateButton.getText();    //getting selected date into string format

//                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");


                //LOGIC FOR CONVERTING STRING TO DATE(NEED TO BE FIXED)
//                SimpleDateFormat sdf = new SimpleDateFormat("MMMM D yyyy");
//                try {
//                    Date sowingDate = sdf.parse(s);   //sowingDate contains Sowing Date in Date Format
//
//                    //CONVERT SOWING DATE INTO STRING FOR PRINTING PURPOSE
//                    SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
//                    String sowing_date_s = postFormater.format(sowingDate);
//                    Toast.makeText(add_crop.this,sowing_date_s,Toast.LENGTH_SHORT).show();
//                } catch (ParseException e) {
//                    Toast.makeText(add_crop.this,"Something went Wrong 72 !",Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }


//GETTING FARM AREA ----------------------------------------------------------------------------------------
                farmArea = findViewById(R.id.crop_area);
                if(farmArea.getText().toString().equals("")){
                    Toast.makeText(add_crop.this, "Please Enter Area ", Toast.LENGTH_SHORT).show();
                }else{
                    area = Integer.parseInt(farmArea.getText().toString());   //AREA OF FARM
                    sd.setArea(area);
                }

//GETTING FARM NAME ---------------------------------------------------------------------------------------
                farmName=findViewById(R.id.farm_name);
                if(farmName.getText().toString().equals("")){
                    Toast.makeText(add_crop.this, "Please Enter Farm Name ", Toast.LENGTH_SHORT).show();
                }else{
                    farm_name = farmName.getText().toString();   //AREA OF FARM
                    sd.setFarm_name(farm_name);
                }

//CURRENT USERID ------------------------------------------------------------------------------------------
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                UID = currentFirebaseUser.getUid();
//                sd.farmer_id=UID;

                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = null;
                try {
                    date = sdf.parse(s);
                    sd.sowing_date=date;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//GENERATE UNIQUE SOWING_DETAILS_ID -----------------------------------------------------------------------
                s_id=s_id+1;
                sd.setSowing_id(s_id);
                reference.setValue(s_id);


//MAKE OBJECT OF SOWING_DETAILS CLASS AND STORE DATA INTO DATABASE ----------------------------------------

                //STORE DATA INTO FIREBASE
                reference1=FirebaseDatabase.getInstance().getReference().child("sowing_details").child(UID).child(Integer.toString(sd.sowing_id));
                reference1.setValue(sd);
                Toast.makeText(add_crop.this, "Crop Added Sucessfully", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(getApplicationContext(),Home.class);
                startActivity(intent);
            }
        });

//------------------------------------------------------------------------------------------------------------------------------------------
        //DATE PICKER FOR SHOWING DATE
        initDatePicker();
        dateButton = findViewById(R.id.showing_datePickerBtn);
        dateButton.setText(getTodaysDate());
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
        mFirebaseAuth=FirebaseAuth.getInstance();
        if(item.getItemId()==R.id.logout){
            mFirebaseAuth.signOut();
            startActivity(new Intent(this,authpage1.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//------------------------------------------------------------------------------------------------------------------------------------------
    //DATE PICKER METHODS
    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }
    private String makeDateString(int day, int month, int year)
    {
//        return getMonthFormat(month) + " " + day + " " + year;
        return day+"/"+month+"/"+year;
    }


    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "JAN";
        if(month == 2)
            return "FEB";
        if(month == 3)
            return "MAR";
        if(month == 4)
            return "APR";
        if(month == 5)
            return "MAY";
        if(month == 6)
            return "JUN";
        if(month == 7)
            return "JUL";
        if(month == 8)
            return "AUG";
        if(month == 9)
            return "SEP";
        if(month == 10)
            return "OCT";
        if(month == 11)
            return "NOV";
        if(month == 12)
            return "DEC";

        //default should never happen
        return "JAN";
    }
    public void openDatePicker(View view) {
        datePickerDialog.show();
    }
//------------------------------------------------------------------------------------------------------------------------------------------

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
//GETTING CROPID FROM CROPNAME
    String GetCropID(String CropName){
        switch (CropName){
            case "Cotton":
                return "c1_cotton";
            case "Wheat":
                return "c2_wheat";
            case "Onion":
                return "c3_onion";
        }
        return "Something Went Wrong";
    }


}