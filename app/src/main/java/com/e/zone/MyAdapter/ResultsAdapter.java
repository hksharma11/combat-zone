package com.e.zone.MyAdapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.e.zone.Model.Result;
import com.e.zone.Model.Trans;
import com.e.zone.R;

import java.util.ArrayList;


public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ResultsHolder> {


    private Context context;
    ArrayList<Result> data;

    public ResultsAdapter(Context context, ArrayList<Result> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public ResultsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.winnerslist,parent,false);
        ResultsAdapter.ResultsHolder viewholder=new ResultsAdapter.ResultsHolder(view);

        return viewholder;
    }

    @Override
    public void onBindViewHolder(@NonNull ResultsHolder holder, final int position) {

        try {
            holder.textView_winnerList_date.setText(data.get(position).getDate());
            holder.textView_winnerList_gamename.setText(data.get(position).getGamename());

            if (!data.get(position).getRow1_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[1].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[1].setText(data.get(position).getRow1_rank());
                holder.textView_winnerList_name[1].setText(data.get(position).getRow1_name());
                holder.textView_winnerList_price[1].setText(data.get(position).getRow1_price());
            }


            if (!data.get(position).getRow2_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[2].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[2].setText(data.get(position).getRow2_rank());
                holder.textView_winnerList_name[2].setText(data.get(position).getRow2_name());
                holder.textView_winnerList_price[2].setText(data.get(position).getRow2_price());
            }


            if (!data.get(position).getRow3_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[3].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[3].setText(data.get(position).getRow3_rank());
                holder.textView_winnerList_name[3].setText(data.get(position).getRow3_name());
                holder.textView_winnerList_price[3].setText(data.get(position).getRow3_price());
            }


            if (!data.get(position).getRow4_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[4].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[4].setText(data.get(position).getRow4_rank());
                holder.textView_winnerList_name[4].setText(data.get(position).getRow4_name());
                holder.textView_winnerList_price[4].setText(data.get(position).getRow4_price());
            }


            if (!data.get(position).getRow5_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[5].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[5].setText(data.get(position).getRow5_rank());
                holder.textView_winnerList_name[5].setText(data.get(position).getRow5_name());
                holder.textView_winnerList_price[5].setText(data.get(position).getRow5_price());
            }


            if (!data.get(position).getRow6_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[6].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[6].setText(data.get(position).getRow6_rank());
                holder.textView_winnerList_name[6].setText(data.get(position).getRow6_name());
                holder.textView_winnerList_price[6].setText(data.get(position).getRow6_price());
            }


            if (!data.get(position).getRow7_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[7].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[7].setText(data.get(position).getRow7_rank());
                holder.textView_winnerList_name[7].setText(data.get(position).getRow7_name());
                holder.textView_winnerList_price[7].setText(data.get(position).getRow7_price());
            }


            if (!data.get(position).getRow8_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[8].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[8].setText(data.get(position).getRow8_rank());
                holder.textView_winnerList_name[8].setText(data.get(position).getRow8_name());
                holder.textView_winnerList_price[8].setText(data.get(position).getRow8_price());
            }


            if (!data.get(position).getRow9_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[9].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[9].setText(data.get(position).getRow9_rank());
                holder.textView_winnerList_name[9].setText(data.get(position).getRow9_name());
                holder.textView_winnerList_price[9].setText(data.get(position).getRow9_price());
            }


            if (!data.get(position).getRow10_rank().isEmpty())
            {
                holder.relativeLayouts_winnerList_row[10].setVisibility(View.VISIBLE);
                holder.textView_winnerList_rank[10].setText(data.get(position).getRow10_rank());
                holder.textView_winnerList_name[10].setText(data.get(position).getRow10_name());
                holder.textView_winnerList_price[10].setText(data.get(position).getRow10_price());
            }


        }catch (Exception e){
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }


    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ResultsHolder extends RecyclerView.ViewHolder{

            TextView textView_winnerList_gamename;
            TextView textView_winnerList_date;
            TextView[] textView_winnerList_rank = new TextView[11];
             TextView[] textView_winnerList_name = new TextView[11];
             TextView[] textView_winnerList_price = new TextView[11];
             RelativeLayout[] relativeLayouts_winnerList_row = new RelativeLayout[11];

        public ResultsHolder(@NonNull View itemView) {
            super(itemView);

            textView_winnerList_gamename=itemView.findViewById(R.id.winnerslist_textview_gamename);
            textView_winnerList_date=itemView.findViewById(R.id.winnerslist_textview_date_time);



            textView_winnerList_rank[1]=itemView.findViewById(R.id.winnerslist_textview_row1_rank);
            textView_winnerList_rank[2]=itemView.findViewById(R.id.winnerslist_textview_row2_rank);
            textView_winnerList_rank[3]=itemView.findViewById(R.id.winnerslist_textview_row3_rank);
            textView_winnerList_rank[4]=itemView.findViewById(R.id.winnerslist_textview_row4_rank);
            textView_winnerList_rank[5]=itemView.findViewById(R.id.winnerslist_textview_row5_rank);
            textView_winnerList_rank[6]=itemView.findViewById(R.id.winnerslist_textview_row6_rank);
            textView_winnerList_rank[7]=itemView.findViewById(R.id.winnerslist_textview_row7_rank);
            textView_winnerList_rank[8]=itemView.findViewById(R.id.winnerslist_textview_row8_rank);
            textView_winnerList_rank[9]=itemView.findViewById(R.id.winnerslist_textview_row9_rank);
            textView_winnerList_rank[10]=itemView.findViewById(R.id.winnerslist_textview_row10_rank);


            textView_winnerList_name[1]=itemView.findViewById(R.id.winnerslist_textview_row1_name);
            textView_winnerList_name[2]=itemView.findViewById(R.id.winnerslist_textview_row2_name);
            textView_winnerList_name[3]=itemView.findViewById(R.id.winnerslist_textview_row3_name);
            textView_winnerList_name[4]=itemView.findViewById(R.id.winnerslist_textview_row4_name);
            textView_winnerList_name[5]=itemView.findViewById(R.id.winnerslist_textview_row5_name);
            textView_winnerList_name[6]=itemView.findViewById(R.id.winnerslist_textview_row6_name);
            textView_winnerList_name[7]=itemView.findViewById(R.id.winnerslist_textview_row7_name);
            textView_winnerList_name[8]=itemView.findViewById(R.id.winnerslist_textview_row8_name);
            textView_winnerList_name[9]=itemView.findViewById(R.id.winnerslist_textview_row9_name);
            textView_winnerList_name[10]=itemView.findViewById(R.id.winnerslist_textview_row10_name);


            textView_winnerList_price[1]=itemView.findViewById(R.id.winnerslist_textview_row1_price);
            textView_winnerList_price[2]=itemView.findViewById(R.id.winnerslist_textview_row2_price);
            textView_winnerList_price[3]=itemView.findViewById(R.id.winnerslist_textview_row3_price);
            textView_winnerList_price[4]=itemView.findViewById(R.id.winnerslist_textview_row4_price);
            textView_winnerList_price[5]=itemView.findViewById(R.id.winnerslist_textview_row5_price);
            textView_winnerList_price[6]=itemView.findViewById(R.id.winnerslist_textview_row6_price);
            textView_winnerList_price[7]=itemView.findViewById(R.id.winnerslist_textview_row7_price);
            textView_winnerList_price[8]=itemView.findViewById(R.id.winnerslist_textview_row8_price);
            textView_winnerList_price[9]=itemView.findViewById(R.id.winnerslist_textview_row9_price);
            textView_winnerList_price[10]=itemView.findViewById(R.id.winnerslist_textview_row10_price);


            relativeLayouts_winnerList_row[1]=itemView.findViewById(R.id.winnerslist_textview_row1);
            relativeLayouts_winnerList_row[2]=itemView.findViewById(R.id.winnerslist_textview_row2);
            relativeLayouts_winnerList_row[3]=itemView.findViewById(R.id.winnerslist_textview_row3);
            relativeLayouts_winnerList_row[4]=itemView.findViewById(R.id.winnerslist_textview_row4);
            relativeLayouts_winnerList_row[5]=itemView.findViewById(R.id.winnerslist_textview_row5);
            relativeLayouts_winnerList_row[6]=itemView.findViewById(R.id.winnerslist_textview_row6);
            relativeLayouts_winnerList_row[7]=itemView.findViewById(R.id.winnerslist_textview_row7);
            relativeLayouts_winnerList_row[8]=itemView.findViewById(R.id.winnerslist_textview_row8);
            relativeLayouts_winnerList_row[9]=itemView.findViewById(R.id.winnerslist_textview_row9);
            relativeLayouts_winnerList_row[10]=itemView.findViewById(R.id.winnerslist_textview_row10);




        }
    }


}
