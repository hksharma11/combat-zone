package com.e.zone.MyAdapter;

import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.e.zone.Games;
import com.e.zone.Model.Homeview;
import com.e.zone.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class HomeviewAdapter extends RecyclerView.Adapter<HomeviewAdapter.HomeviewHolder> {


    private Context context;
    ArrayList<Homeview> data;

    public HomeviewAdapter(Context context, ArrayList<Homeview> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public HomeviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.homeview,parent,false);
        HomeviewHolder viewholder=new HomeviewHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull HomeviewHolder holder, final int position) {

        holder.gamename.setText(data.get(position).getName());
        //holder.imageView.setImageURI(Uri.parse(data.get(position).getLink()));
        Picasso.get()
                .load(data.get(position).getLink())
                .resize(400,400)
                .centerCrop()
                .into(holder.imageView);


        holder.homeview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("testing","Task");
                Intent intent=new Intent(context, Games.class);
                intent.putExtra("game",data.get(position).getName());
                intent.putExtra("gamename",data.get(position).getGamename());
                context.startActivity(intent);
            }
        });





    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class HomeviewHolder extends RecyclerView.ViewHolder{

        ImageView imageView;
        TextView gamename;
        RelativeLayout homeview;

        public HomeviewHolder(@NonNull View itemView) {
            super(itemView);

            imageView=itemView.findViewById(R.id.homeview_imageview);
            gamename=itemView.findViewById(R.id.homeview_textview_gamaname);
            homeview=itemView.findViewById(R.id.homeview_layout);



        }
    }


}
