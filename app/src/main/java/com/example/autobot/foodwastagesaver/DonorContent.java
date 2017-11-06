package com.example.autobot.foodwastagesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DonorContent extends AppCompatActivity {

    private List<Donor> donorList = new ArrayList<>();
    private List<Donor> donorList1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private DonorAdapter donorAdapter;
    private EditText editText;
    private Donor donors;
    private int length;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_content);
        databaseReference= FirebaseDatabase.getInstance().getReference().child("Donor");



        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        editText= (EditText) findViewById(R.id.search);
        donorAdapter = new DonorAdapter(donorList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        prepareDonorData();
        recyclerView.setAdapter(donorAdapter);
        

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                length =charSequence.toString().length();
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    searchItem(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(editable.toString().length()<length) {
                    donorList.clear();
                    donorList.addAll(donorList1);

                }
                Iterator<Donor> iter = donorList.iterator();

                while (iter.hasNext()) {
                    Donor donor = iter.next();

                    if (!donor.getAddress().toLowerCase().contains(editable.toString()) && !donor.getAddress().toUpperCase().contains(editable.toString()) && !donor.getAddress().contains(editable.toString()))
                        iter.remove();
                }
                donorAdapter.notifyDataSetChanged();
            }
        });
    }
    public void searchItem(String textToSearch)
    {
        /*for (Donor donor:donorList)
        {
            if(!donor.getAddress().contains(textToSearch))
                donorList.remove(donor);
        }*/
        Iterator<Donor> iter = donorList.iterator();

        while (iter.hasNext()) {
            Donor donor = iter.next();

            if (!donor.getAddress().toLowerCase().contains(textToSearch)&&!donor.getAddress().toUpperCase().contains(textToSearch) && !donor.getAddress().contains(textToSearch))
                iter.remove();
        }
        donorAdapter.notifyDataSetChanged();
    }

    public void prepareDonorData() {

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Donor donor = snapshot.getValue(Donor.class);
                    donorList.add(donor);
                    donorList1.add(donor);
                    donorAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
