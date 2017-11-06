package com.example.autobot.foodwastagesaver;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Autobot on 8/3/2017.
 */

public class DonorAdapter extends RecyclerView.Adapter<DonorAdapter.MyViewHolder>{

    private List<Donor> donorList;
    private Context context;

    public DonorAdapter(List<Donor> donorList, Context context) {
        this.donorList = donorList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, email, address;
        public MyViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.name2);
            email = (TextView)view.findViewById(R.id.phone2);
            address =(TextView)view.findViewById(R.id.address2);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { donorList.get(pos).getEmail() });
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Food Savior");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    context.startActivity(Intent.createChooser(intent, "Choose Your Email App"));
                }
            });

        }
    }

    public DonorAdapter (List<Donor> donorList){
        this.donorList =donorList;
    }

    @Override
    public DonorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.donor_list_row,parent,false);
        context = parent.getContext();
        return new DonorAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(DonorAdapter.MyViewHolder holder, final int position) {
        final Donor donor = donorList.get(position);
        holder.name.setText(donor.getName());
        holder.email.setText(donor.getEmail());
        holder.address.setText(donor.getAddress());

    }

    @Override
    public int getItemCount() {
        return donorList.size();
    }


}
