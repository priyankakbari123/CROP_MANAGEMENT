package com.cropmanagement;

import static android.widget.Toast.makeText;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class authpage1 extends AppCompatActivity {

    EditText enternumber;
    Button getotpbutton,admin_login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authpage1);

        enternumber = findViewById(R.id.input_mobile_number);
        getotpbutton = findViewById(R.id.getotp_btn);
        admin_login_btn=findViewById(R.id.adminlogin_btn);
        ProgressBar progressBar = findViewById(R.id.progressbar_sending_otp);

        //When Button is clicked
        getotpbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Check for validations
                if (!enternumber.getText().toString().trim().isEmpty()) {
                    if ((enternumber.getText().toString().trim()).length() == 10) {
                        //if mobile number is not empty and 10 digit then redirect to next page

                        //When button is clicked button will INVISIBLE and progressbar should visible
                        progressBar.setVisibility(View.VISIBLE);
                        getotpbutton.setVisibility(View.INVISIBLE);

                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+91" + enternumber.getText().toString(),
                                60,
                                TimeUnit.SECONDS,
                                authpage1.this,
                                new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                                    @Override
                                    public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onVerificationFailed(@NonNull FirebaseException e) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Toast.makeText(authpage1.this, "Something Went to wrong ! Please Try Again", Toast.LENGTH_SHORT).show();
                                    }

                                    @Override
                                    public void onCodeSent(@NonNull String backendotp, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                        progressBar.setVisibility(View.GONE);
                                        getotpbutton.setVisibility(View.VISIBLE);
                                        Intent intent = new Intent(getApplicationContext(), authpage2.class);
                                        //Add mobile as parameters
                                        intent.putExtra("mobile", enternumber.getText().toString());
                                        intent.putExtra("backendotp",backendotp);   //Add sended otp as parameter for validataion
                                        startActivity(intent);
                                    }
                                }
                        );

//
                    } else {
                        Toast.makeText(authpage1.this, "Please Enter Correct Mobile Number", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(authpage1.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //When Click on Admin Login Button
        admin_login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent adminloginIntent=new Intent(getApplicationContext(),adminlogin.class);
                startActivity(adminloginIntent);
                finish();
            }
        });
    }
}