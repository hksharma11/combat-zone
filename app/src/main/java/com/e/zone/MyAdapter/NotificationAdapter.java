package com.e.zone.MyAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.e.zone.Model.Notification;
import com.e.zone.R;

import java.util.ArrayList;


public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationHolder> {


    private Context context;
    ArrayList<Notification> data;

    public NotificationAdapter(Context context, ArrayList<Notification> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_view,parent,false);
        NotificationAdapter.NotificationHolder viewholder=new NotificationAdapter.NotificationHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, final int position) {

        holder.message.setText(data.get(position).getMessage());
        holder.date.setText(data.get(position).getDate());




    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder{

        TextView message,date;

        public NotificationHolder(@NonNull View itemView) {
            super(itemView);

           message=itemView.findViewById(R.id.notification_textview_message);
           date=itemView.findViewById(R.id.notification_textview_date);





        }
    }


}
