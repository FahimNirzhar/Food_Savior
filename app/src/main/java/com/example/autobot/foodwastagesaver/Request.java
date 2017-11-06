package com.example.autobot.foodwastagesaver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.Sampler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Request extends AppCompatActivity {
    EditText etMsg;
    Button btnPost;
    DatabaseReference databaseReferencereq;
    FirebaseAuth Auth;
    DatabaseReference databaseReference;
    DatabaseReference databaseReference2;
     String msg , Name, Phone,Address,keyValue,keyValue1,Email;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        Name="";
        Email="";
        Address="";
        Auth = FirebaseAuth.getInstance();
        etMsg = (EditText)findViewById(R.id.editText);
        btnPost =(Button)findViewById(R.id.button2);
        sharedPreferences=getPreferences(MODE_PRIVATE);
        keyValue1=getIntent().getStringExtra("keyValue");
        if(keyValue1!=null)
            keyValue=keyValue1;
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Volunteer");
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("Donor");
        databaseReferencereq= FirebaseDatabase.getInstance().getReference().child("Request");
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //FirebaseUser firebaseUser = databaseReference.child(keyc).getDatabase();
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Volunter volunter = snapshot.getValue(Volunter.class);
                       // volunter.getEmail==currentuser.email
                        if(volunter.getKeyValue().equals(keyValue)) {
                            Address = volunter.getAddress();
                            Email = volunter.getEmail();
                            Name = volunter.getName();
                            msg = etMsg.getText().toString();
                            String keyReq = databaseReferencereq.push().getKey();
                            Req req=new Req(Name,Email,Address,msg);
                            databaseReferencereq.child(keyReq).setValue(req);
                            Toast.makeText(Request.this," Your Post has been updated successfully", Toast.LENGTH_SHORT).show();
                            etMsg.getText().clear();
                        }


                    }

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            })  ;


                databaseReference2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Donor donor = snapshot.getValue(Donor.class);
                            FirebaseUser firebaseUser = Auth.getCurrentUser();
                            if(donor.getEmail().equals(firebaseUser.getEmail())) {
                                Address = donor.getAddress();
                                Email = donor.getEmail();
                                Name = donor.getName();
                                msg = etMsg.getText().toString();
                                String keyReq = databaseReferencereq.push().getKey();
                                Req req=new Req(Name,Email,Address,msg);
                                databaseReferencereq.child(keyReq).setValue(req);
                                Toast.makeText(Request.this," Your Post has been updated successfully", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Request.this, Home_content.class);
                                startActivity(intent);
                            }

                        }


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                })  ;
            }
        });


    }
}
