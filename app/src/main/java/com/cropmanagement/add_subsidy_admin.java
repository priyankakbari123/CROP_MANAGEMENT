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

public class add_subsidy_admin extends AppCompatActivity {
    EditText title,detail,procedure;
    Button addSubsidy;
    DatabaseReference reference;
    int subsidyId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subsidy_admin);

        title=findViewById(R.id.subsidy_title);
        detail=findViewById(R.id.subsidy_details);
        procedure=findViewById(R.id.subsidy_procedure);
        addSubsidy=findViewById(R.id.addSubsidy);

        //GETTING MAX SOWING_ID ----------------------------------------------------------------------------
        reference= FirebaseDatabase.getInstance().getReference().child("subsidy_id");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                subsidyId=snapshot.getValue(Integer.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        addSubsidy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!title.getText().toString().isEmpty() && !detail.getText().toString().isEmpty() && !procedure.getText().toString().isEmpty()){

                    //GETTING INPUT FROM EDITTEXT AND STORED INTO OBJECTS
                    subsidy s=new subsidy();
                    s.setId(subsidyId);
                    s.setTitle(title.getText().toString());
                    s.setDetails(detail.getText().toString());
                    s.setProcedure(procedure.getText().toString());

                    //ADD SUBSIDY DETAILS INTO DATABASE
                    reference= FirebaseDatabase.getInstance().getReference("subsidy").child(Integer.toString(subsidyId));
                    reference.setValue(s);
                    reference= FirebaseDatabase.getInstance().getReference().child("subsidy_id");
                    reference.setValue(subsidyId+1);

                    Toast.makeText(add_subsidy_admin.this, "Subsidy Added Successfully !", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(add_subsidy_admin.this, "Please Fill All the Fields", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void RedirectAdminHome(View view) {
        Intent intent=new Intent(getApplicationContext(),AdminHome.class);
        startActivity(intent);
        finish();
    }
}