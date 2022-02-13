package com.cropmanagement;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminlogin extends AppCompatActivity {

    EditText userid,password;
    Button loginBtn;
    DatabaseReference ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);

        userid=findViewById(R.id.admin_id);
        password=findViewById(R.id.admin_password);
        loginBtn=findViewById(R.id.adminLogin_Btn);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Id=userid.getText().toString().trim();
                String Pass=password.getText().toString().trim();
                if(!Id.isEmpty() && !Pass.isEmpty()){
                    ref= FirebaseDatabase.getInstance().getReference("Admin");
                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
//                            for(DataSnapshot snapshot1:snapshot.getChildren()){
//                                if()
//                            }
                            if(snapshot.child("userId").exists() && snapshot.child("password").exists()){
                                if(Id.equals(snapshot.child("userId").getValue().toString()) && Pass.equals(snapshot.child("password").getValue().toString())){
                                    Intent AdminHome =new Intent(getApplicationContext(), AdminHome.class);
                                    startActivity(AdminHome);
                                    finish();
                                }else{
                                    Toast.makeText(adminlogin.this, "Please Enter Valid Credentials !", Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(adminlogin.this, "Something Went Wrong !! Plesae Try Again", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }else{
                    Toast.makeText(adminlogin.this, "Please Enter UserId and Password ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}