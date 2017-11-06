package com.example.autobot.foodwastagesaver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VolunterContent extends AppCompatActivity {
    private List <Volunter> volunterList = new ArrayList<>();
    private List <Volunter> volunterList1 = new ArrayList<>();
    private RecyclerView recyclerView;
    private VolunterAdapter volunterAdapter;
    private EditText editText;
    private int length;
    private DatabaseReference databaseReference1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunter_content);
        databaseReference1= FirebaseDatabase.getInstance().getReference().child("Volunteer");
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        editText= (EditText) findViewById(R.id.search_vol);
        volunterAdapter = new VolunterAdapter(volunterList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(volunterAdapter);
        prepareVolunterData();
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
                if(editable.toString().length()<length ) {
                    volunterList.clear();
                    volunterList.addAll(volunterList1);
                }
                Iterator<Volunter> iter = volunterList.iterator();

                while (iter.hasNext()) {
                    Volunter volunter = iter.next();

                    if (!volunter.getAddress().toLowerCase().contains(editable.toString())&&!volunter.getAddress().toUpperCase().contains(editable.toString())&&!volunter.getAddress().contains(editable.toString()))
                        iter.remove();
                }
                volunterAdapter.notifyDataSetChanged();
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
        Iterator<Volunter> iter = volunterList.iterator();

        while (iter.hasNext()) {
           Volunter volunter = iter.next();

            if (!volunter.getAddress().toLowerCase().contains(textToSearch)&&!volunter.getAddress().toUpperCase().contains(textToSearch)&&!volunter.getAddress().contains(textToSearch))
                iter.remove();
        }
       volunterAdapter.notifyDataSetChanged();
    }
    public class ClickListener {
    }

    private void prepareVolunterData() {

        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Volunter volunter = snapshot.getValue(Volunter.class);
                    volunterList.add(volunter);
                    volunterList1.add(volunter);
                    volunterAdapter.notifyDataSetChanged();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
