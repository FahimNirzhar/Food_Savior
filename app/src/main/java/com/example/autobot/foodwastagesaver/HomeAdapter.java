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

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>{

    private List<Req> reqList;
    private Context context;

    public HomeAdapter(List<Req> reqList, Context context) {
        this.reqList = reqList;
        this.context = context;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView name, email, address,msg;
        public MyViewHolder(View view){
            super(view);
            name = (TextView)view.findViewById(R.id.nameh);
            email = (TextView)view.findViewById(R.id.emailh);
            address =(TextView)view.findViewById(R.id.addressh);
            msg= (TextView)view.findViewById(R.id.msgh);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("plain/text");
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { reqList.get(pos).getEmail() });
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Food Savior");
                    intent.putExtra(Intent.EXTRA_TEXT, "");
                    context.startActivity(Intent.createChooser(intent, "Choose Your Email App"));
                }
            });

        }
    }

    public HomeAdapter(List<Req> reqList) {
        this.reqList = reqList;
    }

    @Override
    public HomeAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_list_row,parent,false);
        context = parent.getContext();
        return new HomeAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(HomeAdapter.MyViewHolder holder, final int position) {
        final Req request  = reqList.get(position);
        holder.name.setText(request.getName());
        holder.email.setText(request.getEmail());
        holder.address.setText(request.getAddress());
        holder.msg.setText(request.getMsg());

    }

    @Override
    public int getItemCount() {
        return reqList.size();
    }


}
