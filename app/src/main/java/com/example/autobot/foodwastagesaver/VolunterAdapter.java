package com.example.autobot.foodwastagesaver;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by Autobot on 8/3/2017.
 */

public class VolunterAdapter extends RecyclerView.Adapter<VolunterAdapter.MyViewHolder> {

    private List<Volunter> volunterList;
    private Context context;

    public VolunterAdapter(List<Volunter> volunterList, Context context) {
        this.volunterList = volunterList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, email, address;
        public MyViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.name);
            email = (TextView)view.findViewById(R.id.phone);
            address =(TextView)view.findViewById(R.id.address);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { volunterList.get(pos).getEmail() });
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Food Savior");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    context.startActivity(Intent.createChooser(intent, "Choose Your Email App"));
                }
            });
        }
    }
    public VolunterAdapter (List<Volunter> volunterList){
        this.volunterList = volunterList;
    }


    @Override
    public VolunterAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.volunter_list_row,parent,false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(VolunterAdapter.MyViewHolder holder, int position) {
        Volunter volunter = volunterList.get(position);
        holder.name.setText(volunter.getName());
        holder.email.setText(volunter.getEmail());
        holder.address.setText(volunter.getAddress());

    }

    @Override
    public int getItemCount() {
        return volunterList.size();
    }


}
