package com.cropmanagement;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.text.DateFormat;
import java.util.Calendar;

public class home extends AppCompatActivity {
    public String crop_name;
    private FirebaseAuth mFirebaseAuth;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mFirebaseAuth=FirebaseAuth.getInstance();
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
            @Override
            public void onClick(View view) {
                crop_name= (String) spinner.getSelectedItem();

                Calendar cal = Calendar.getInstance();
                String s= (String) dateButton.getText();    //getting selected date into string format
                //NEED TO CONVERT INTO DATE FORMAT AND STORE INTO DATABASE
//                String selectedDate = DateFormat.getDateInstance(DateFormat.SHORT).format(dateButton.getText());
                Toast.makeText(home.this,s,Toast.LENGTH_SHORT).show();
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