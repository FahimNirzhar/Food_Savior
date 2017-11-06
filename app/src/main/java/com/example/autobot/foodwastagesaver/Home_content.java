package com.example.autobot.foodwastagesaver;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Home_content extends AppCompatActivity {
    private DatabaseReference databaseReferencereq;
    private List<Req> requestlist = new ArrayList<>();
    private RecyclerView recyclerView;
    private HomeAdapter homeAdapter;
     private FirebaseAuth Auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_content);
        databaseReferencereq= FirebaseDatabase.getInstance().getReference().child("Request");
        Auth = FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        prepareHomeData();
        homeAdapter =new HomeAdapter(requestlist);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(homeAdapter);
    }

    private void prepareHomeData() {
        databaseReferencereq.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Req req = snapshot.getValue(Req.class);
                    requestlist.add(req);
                    homeAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void signout(View view) {
        Auth.signOut();
        Intent SuccessIntent = new Intent(Home_content.this, MainActivity.class);
        startActivity(SuccessIntent);
        finish();
    }
}
