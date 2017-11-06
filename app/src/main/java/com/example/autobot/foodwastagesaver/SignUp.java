package com.example.autobot.foodwastagesaver;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {
    EditText etName, etPhone, etEmail,etAdd, etPass;
    Button btnSigntUp;
    TextView txtLogin;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference,databaseReference2;
    LinearLayout donorlayout,volunteerlayout;
    String Address = null,Phone =null,Name=null,Email=null,Pass=null;
    int i=0;
    String keyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Firebase.setAndroidContext(this);
        firebaseAuth = FirebaseAuth.getInstance();
        etName = (EditText)findViewById(R.id.username);
        etPhone = (EditText)findViewById(R.id.phoneno);
        etEmail =(EditText)findViewById(R.id.email);
        etAdd = (EditText)findViewById(R.id.address);
        etPass= (EditText)findViewById(R.id.password);
        donorlayout= (LinearLayout) findViewById(R.id.donorlayout);
       volunteerlayout= (LinearLayout) findViewById(R.id.volunteerlayout);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Donor");
        databaseReference2= FirebaseDatabase.getInstance().getReference().child("Volunteer");


    }

    public void signup(View view) {
        if(i==0)
            Toast.makeText(this, "Select Whether You are Donor Or Volunteer", Toast.LENGTH_SHORT).show();
        else
        registration();

    }

    private void registration() {


        Address = etAdd.getText().toString();
        Phone = etPhone.getText().toString();
        Name = etName.getText().toString();
        Email =etEmail.getText().toString();
        Pass = etPass.getText().toString();


        if ( TextUtils.isEmpty(Address) || TextUtils.isEmpty(Phone) || TextUtils.isEmpty(Name) || TextUtils.isEmpty(Email) || TextUtils.isEmpty(Pass))
            Toast.makeText(this, "Make Sure You have filled up all the fields ", Toast.LENGTH_SHORT).show();
        else {

            firebaseAuth.createUserWithEmailAndPassword(etEmail.getText().toString(), etPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {


                            if (task.isSuccessful()) {

                            keyValue = databaseReference.push().getKey();
                                    if (i == 1) {
                                        Donor donor = new Donor(Name, Phone, Address,Email);
                                        databaseReference.child(keyValue).setValue(donor);
                                    } else if (i == 2) {
                                        Volunter volunter = new Volunter(Name, Phone, Address,keyValue,Email);
                                        databaseReference2.child(keyValue).setValue(volunter);
                                    }
                                    Toast.makeText(SignUp.this, "Registration successful", Toast.LENGTH_LONG).show();
                             moveToHome();
                            }
                            else {
                                Log.e("ERROR", task.getException().toString());
                                Toast.makeText(SignUp.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }
    }

    private void moveToHome() {
        Intent intent=new Intent(SignUp.this,HomeLayout.class);
        intent.putExtra("keyValue",keyValue);
        startActivity(intent);
    }

    public void addonor(View view) {
        i=1;
        volunteerlayout.setVisibility(View.GONE);
    }

    public void addVolunteer(View view) {
        i=2;
        donorlayout.setVisibility(View.GONE);
    }

    public void moveSignIn(View view) {
        Intent intent = new Intent(SignUp.this, MainActivity.class);
        startActivity(intent);
    }
}
