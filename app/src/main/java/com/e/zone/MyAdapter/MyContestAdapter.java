package com.e.zone.MyAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.e.zone.ContestDetails;
import com.e.zone.Model.Game;
import com.e.zone.R;

import java.util.ArrayList;


public class MyContestAdapter extends RecyclerView.Adapter<MyContestAdapter.MyContestHolder> {


    private Context context;
    ArrayList<Game> data;

    public MyContestAdapter(Context context, ArrayList<Game> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public MyContestHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gamecard,parent,false);
        MyContestAdapter.MyContestHolder viewholder=new MyContestAdapter.MyContestHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyContestHolder holder, final int position) {

        try{
            holder.gamecard_relative_layout_check_room.setVisibility(View.VISIBLE);
            holder.layout6.setVisibility(View.GONE);
            holder.progressBar.setVisibility(View.GONE);
            holder.data1.setText(data.get(position).getData1());
            holder.data2.setText(data.get(position).getData2());
            holder.data3.setText(data.get(position).getData3());
            holder.data4.setText(data.get(position).getData4());
            holder.data5.setText(data.get(position).getData5());
            holder.data6.setText(data.get(position).getData6());
            holder.data7.setText(data.get(position).getData7());
            holder.data8.setText(data.get(position).getData8());
            holder.data9.setText(data.get(position).getData9());
            holder.data10.setText(data.get(position).getData10());
            holder.data11.setText(data.get(position).getData11());
            holder.data12.setText(data.get(position).getData12());
            holder.data13.setText(data.get(position).getData13());
            holder.data14.setText(data.get(position).getData14());
            holder.data15.setText(data.get(position).getData15());
            holder.data16.setText(data.get(position).getData16());
            holder.data17.setText(data.get(position).getData17());
            holder.data18.setText(data.get(position).getData18());
            holder.data19.setText(data.get(position).getData19());

            holder.data22.setText(data.get(position).getData22());





            if (data.get(position).getData14().isEmpty() && data.get(position).getData15().isEmpty())
            {
                holder.linearLayout_winners.setVisibility(View.GONE);
                holder.divider_view.setVisibility(View.GONE);
            }
            else if(data.get(position).getData16().isEmpty() && data.get(position).getData17().isEmpty())
            {
                holder.linearLayout_perkill.setVisibility(View.GONE);
                holder.divider_view.setVisibility(View.GONE);
            }



        }
        catch (Exception e)
        {
            Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
        }


        holder.gamecard_relative_layout_check_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ContestDetails.class);
                intent.putExtra("game_id",data.get(position).getGame_id());
                intent.putExtra("gamename",data.get(position).getGamename());
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyContestHolder extends RecyclerView.ViewHolder{

        TextView data1,data2,data3,data4,data5,data6,data7,data8,data9,data10,data11,data12,data13,data14,data15,data16,data17,data18,data19,data20,data21,data22;

        ProgressBar progressBar;
        RelativeLayout layout6;
        RelativeLayout gamecard_relative_layout_check_room;


        LinearLayout linearLayout_winners,linearLayout_perkill;
        View divider_view;


        public MyContestHolder(@NonNull View itemView) {
            super(itemView);
            data1=itemView.findViewById(R.id.data1);
            data2=itemView.findViewById(R.id.data2);
            data3=itemView.findViewById(R.id.data3);
            data4=itemView.findViewById(R.id.data4);
            data5=itemView.findViewById(R.id.data5);
            data6=itemView.findViewById(R.id.data6);
            data7=itemView.findViewById(R.id.data7);
            data8=itemView.findViewById(R.id.data8);
            data9=itemView.findViewById(R.id.data9);
            data10=itemView.findViewById(R.id.data10);
            data11=itemView.findViewById(R.id.data11);
            data12=itemView.findViewById(R.id.data12);
            data13=itemView.findViewById(R.id.data13);
            data14=itemView.findViewById(R.id.data14);
            data15=itemView.findViewById(R.id.data15);
            data16=itemView.findViewById(R.id.data16);
            data17=itemView.findViewById(R.id.data17);
            data18=itemView.findViewById(R.id.data18);
            data19=itemView.findViewById(R.id.data19);
            data20=itemView.findViewById(R.id.data20);
            data21=itemView.findViewById(R.id.data21);
            data22=itemView.findViewById(R.id.data22);

            progressBar=itemView.findViewById(R.id.gamecard_progressbar);
            layout6=itemView.findViewById(R.id.layout6);

            gamecard_relative_layout_check_room=itemView.findViewById(R.id.gamecard_relative_layout_joincontest);


            linearLayout_winners=itemView.findViewById(R.id.gamecard_linearlayout_winner);
            linearLayout_perkill=itemView.findViewById(R.id.gamecard_linearlayout_perkill);
            divider_view=itemView.findViewById(R.id.divider_view);

        }
    }


}
