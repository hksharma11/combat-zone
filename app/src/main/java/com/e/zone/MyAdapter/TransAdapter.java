package com.e.zone.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.e.zone.Model.Trans;
import com.e.zone.R;

import java.util.ArrayList;


public class TransAdapter extends RecyclerView.Adapter<TransAdapter.TransHolder> {


    private Context context;
    ArrayList<Trans> data;

    public TransAdapter(Context context, ArrayList<Trans> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public TransHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.transaction_view,parent,false);
        TransAdapter.TransHolder viewholder=new TransAdapter.TransHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull TransHolder holder, final int position) {


        holder.message.setText(data.get(position).getMessage());
        holder.amount.setText(data.get(position).getAmount());
        String sign= data.get(position).getPlusminus();

        if (sign.matches("-"))
        {
            holder.plusminus.setTextColor(Color.RED);
            holder.plusminus.setText(sign);
        }
        else {
            holder.plusminus.setText(sign);
            holder.plusminus.setTextColor(Color.GREEN);
        }
        String date= data.get(position).getDate();
        date=date.substring(0,16);
        holder.date.setText(date);



    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class TransHolder extends RecyclerView.ViewHolder{

        TextView plusminus,message,amount,date;

        public TransHolder(@NonNull View itemView) {
            super(itemView);

            message=itemView.findViewById(R.id.transaction_textview_message);
            amount=itemView.findViewById(R.id.transaction_textview_amount);
            plusminus=itemView.findViewById(R.id.transaction_textview_plusminus);
            date=itemView.findViewById(R.id.transaction_textview_date);





        }
    }


}
