package com.example.autobot.foodwastagesaver;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class HomeLayout extends AppCompatActivity {
    Button volunteer,donor,home,profile;
    FirebaseAuth Auth;
    String keyValue,keyValue1;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_layout);
        donor= (Button) findViewById(R.id.donorbtn);
        volunteer= (Button) findViewById(R.id.volunteerbtn);
        home= (Button) findViewById(R.id.homebtn);
        profile= (Button) findViewById(R.id.profilebtn);

        keyValue1=getIntent().getStringExtra("keyValue");
        sharedPreferences=getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor=sharedPreferences.edit();
        if(keyValue1!=null){
        editor.putString("sample",keyValue1);
        editor.apply();
        editor.commit();}
        keyValue=sharedPreferences.getString("sample","");
        Auth = FirebaseAuth.getInstance();
        volunteer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeLayout.this, VolunterContent.class);
                startActivity(intent);
            }
        });

        donor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeLayout.this, DonorContent.class);
                startActivity(intent);
            }
        });

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeLayout.this, Home_content.class);
                startActivity(intent);
                //Auth.signOut();
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Auth.signOut();
                Intent intent = new Intent(HomeLayout.this, Request
                        .class);
                intent.putExtra("keyValue",keyValue);
                startActivity(intent);
                //Intent intent = new Intent(HomeLayout.this, MainActivity.class);
                //startActivity(intent);
            }
        });

    }

}
