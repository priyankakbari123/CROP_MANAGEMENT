package com.cropmanagement;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.ktx.Firebase;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class home extends AppCompatActivity {
    public String crop_name,UID,cropId;
    private FirebaseAuth mFirebaseAuth;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    public int area;
    EditText farmArea;
    FirebaseDatabase rootNode;
    DatabaseReference reference,reference1;
    int s_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFirebaseAuth=FirebaseAuth.getInstance();

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

//CREATE OBJECTS OF CROP  --------------------------------------------------------------------------
        crop cotton=new crop();
        cotton.crop_name="Cotton";
        cotton.crop_id="c1_cotton";

        crop wheat=new crop();
        cotton.crop_name="Wheat";
        cotton.crop_id="c2_wheat";

        crop onion=new crop();
        cotton.crop_name="Onion";
        cotton.crop_id="c3_onion";

//------------------------------------------------------------------------------------------------------------------------------------------
        //SPINNER FOR CROP SELECTION
        Spinner spinner=findViewById(R.id.crop_spinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.crops, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

//------------------------------------------------------------------------------------------------------------------------------------------
        //LOGOUT BUTTON ON CLICK
        final Button logoutbtn=findViewById(R.id.logout);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                logout(view);
            }
        });

//------------------------------------------------------------------------------------------------------------------------------------------
        //ONCLICK ON ADD CROP BUTTON
        final Button addcropbtn=findViewById(R.id.addcropbtn);
        addcropbtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {

                sowing_details sd = new sowing_details();

                crop_name = (String) spinner.getSelectedItem();
                if(crop_name.equals("Cotton")){
                    cropId=cotton.getCrop_id(crop_name);
                    sd.crop_id=cropId;
                }

                Calendar cal = Calendar.getInstance();
                String s = (String) dateButton.getText();    //getting selected date into string format

                //LOGIC FOR CONVERTING STRING TO DATE(NEED TO BE FIXED)
//                SimpleDateFormat sdf = new SimpleDateFormat("MMMM D yyyy");
//                try {
//                    Date sowingDate = sdf.parse(s);   //sowingDate contains Sowing Date in Date Format
//
//                    //CONVERT SOWING DATE INTO STRING FOR PRINTING PURPOSE
//                    SimpleDateFormat postFormater = new SimpleDateFormat("dd/MM/yyyy");
//                    String sowing_date_s = postFormater.format(sowingDate);
//                    Toast.makeText(home.this,sowing_date_s,Toast.LENGTH_SHORT).show();
//                } catch (ParseException e) {
//                    Toast.makeText(home.this,"Something went Wrong 72 !",Toast.LENGTH_SHORT).show();
//                    e.printStackTrace();
//                }


//GETTING FARM AREA ----------------------------------------------------------------------------------------
                farmArea = findViewById(R.id.crop_area);
                if(farmArea.getText().toString().equals("")){
                    Toast.makeText(home.this, "Please Enter Area ", Toast.LENGTH_SHORT).show();
                }else{
                    area = Integer.parseInt(farmArea.getText().toString());   //AREA OF FARM
                    sd.Area=area;
                }

                Toast.makeText(home.this, s, Toast.LENGTH_SHORT).show();

//CURRENT USERID ------------------------------------------------------------------------------------------
                FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                UID = currentFirebaseUser.getUid();
                sd.farmer_id=UID;

                SimpleDateFormat sdf = new SimpleDateFormat("MMM DD yyyy");
                Date date = null;
                try {
                    date = sdf.parse(s);
                    sd.sowing_date=date;
                } catch (ParseException e) {
                    e.printStackTrace();
                }

//GENERATE UNIQUE SOWING_DETAILS_ID -----------------------------------------------------------------------
                s_id=s_id+1;
                sd.sowing_id=s_id;
                reference.setValue(s_id);


//MAKE OBJECT OF SOWING_DETAILS CLASS AND STORE DATA INTO DATABASE ----------------------------------------
//                sowing_details sd = new sowing_details(s_id,cropId, UID, date, area);

                //STORE DATA INTO FIREBASE
                reference1=FirebaseDatabase.getInstance().getReference().child("sowing_details").child(Integer.toString(s_id));
                reference1.setValue(sd);

            }
        });

//------------------------------------------------------------------------------------------------------------------------------------------
        //DATE PICKER FOR SHOWING DATE
        initDatePicker();
        dateButton = findViewById(R.id.showing_datePickerBtn);
        dateButton.setText(getTodaysDate());
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
        return getMonthFormat(month) + " " + day + " " + year;
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


}