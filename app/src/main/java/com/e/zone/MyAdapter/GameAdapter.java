package com.e.zone.MyAdapter;

import android.app.Dialog;
import android.app.Notification;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.e.zone.Account;
import com.e.zone.Games;
import com.e.zone.JoinContest;
import com.e.zone.Model.Game;
import com.e.zone.Model.Homeview;
import com.e.zone.MyContest;
import com.e.zone.R;
import com.e.zone.Shared_data;
import com.e.zone.Transaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.onesignal.OneSignal;
import com.squareup.picasso.Picasso;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;


public class GameAdapter extends RecyclerView.Adapter<GameAdapter.GameHolder> {


    private Context context;
    ArrayList<Game> data;
    View view;
    BottomSheetDialog winning_breakup;
    FirebaseFirestore mydb;
    Dialog loading;






    public GameAdapter(Context context, ArrayList<Game> data,Dialog loading,BottomSheetDialog winning_breakup) {
        this.context = context;
        this.data = data;

        mydb=FirebaseFirestore.getInstance();
        this.loading=loading;
        this.winning_breakup=winning_breakup;

    }

    @NonNull
    @Override
    public GameHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.gamecard,parent,false);
        GameHolder viewholder=new GameHolder(view);

        return viewholder;
    }



    @Override
    public void onBindViewHolder(@NonNull final GameHolder holder, final int position) {
        try{

            Shared_data shared_data=new Shared_data(context);
            String phone=shared_data.getPhone();
            mydb.collection("Users").document(phone).collection("MyContest").document(data.get(position).getGame_id()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if (documentSnapshot.exists())
                    {
                        holder.relativeLayout_joincontest.setVisibility(View.GONE);
                        holder.relativeLayout_joined.setVisibility(View.VISIBLE);
                    }
                    else {
                        holder.relativeLayout_joincontest.setVisibility(View.VISIBLE);
                        holder.relativeLayout_joined.setVisibility(View.GONE);
                    }
                }
            });

            Log.d("testing2","Task1");
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
            Log.d("testing2","Task2");
            holder.data22.setText(data.get(position).getData22());
            //

           //
           // Log.d("testing2","total players ="+data.get(position).getTotal_players());
            int total_player=Integer.valueOf(data.get(position).getTotal_players());
            Log.d("testing2","Task3");
            int joined_player=Integer.valueOf(data.get(position).getJoined_players());
            Log.d("testing2","Task4");
            holder.progressBar.setMax(total_player);
            holder.progressBar.setProgress(joined_player);
            //

            holder.data20.setText((total_player - joined_player) +" "+data.get(position).getData20());
            holder.data21.setText(data.get(position).getJoined_players() +" "+data.get(position).getData21());



            final int entryfees=Integer.valueOf(data.get(position).getEntry_fee());
            Log.d("testing2","Task5");
            holder.relativeLayout_joincontest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, JoinContest.class);
                    intent.putExtra("game_id", data.get(position).getGame_id());
                    intent.putExtra("gamename", data.get(position).getGamename());
                    intent.putExtra("entry_fee", data.get(position).getEntry_fee());

                    context.startActivity(intent);

                }
            });

            holder.relativeLayout_joined.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context,MyContest.class);
                    context.startActivity(intent);

                }
            });

            if(Integer.parseInt(data.get(position).getJoined_players())>=Integer.parseInt(data.get(position).getTotal_players()))
            {
                holder.data22.setText("HOUSEFULL");
                holder.relativeLayout_joincontest.setClickable(false);
            }






            //

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

            holder.close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    winning_breakup.dismiss();
                }
            });


            holder.data15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //Winning Breakup
                    holder.row1_rank.setText(data.get(position).getRow1_rank());
                    holder.row2_rank.setText(data.get(position).getRow2_rank());
                    holder.row3_rank.setText(data.get(position).getRow3_rank());
                    holder.row4_rank.setText(data.get(position).getRow4_rank());
                    holder.row5_rank.setText(data.get(position).getRow5_rank());
                    holder.row6_rank.setText(data.get(position).getRow6_rank());
                    holder.row7_rank.setText(data.get(position).getRow7_rank());
                    holder.row8_rank.setText(data.get(position).getRow8_rank());
                    holder.row9_rank.setText(data.get(position).getRow9_rank());
                    holder.row10_rank.setText(data.get(position).getRow10_rank());


                    holder.row1_price.setText(data.get(position).getRow1_price());
                    holder.row2_price.setText(data.get(position).getRow2_price());
                    holder.row3_price.setText(data.get(position).getRow3_price());
                    holder.row4_price.setText(data.get(position).getRow4_price());
                    holder.row5_price.setText(data.get(position).getRow5_price());
                    holder.row6_price.setText(data.get(position).getRow6_price());
                    holder.row7_price.setText(data.get(position).getRow7_price());
                    holder.row8_price.setText(data.get(position).getRow8_price());
                    holder.row9_price.setText(data.get(position).getRow9_price());
                    holder.row10_price.setText(data.get(position).getRow10_price());
                    holder.pool_prize.setText(data.get(position).getData13());







                    if (!holder.row1_rank.getText().toString().isEmpty() && !holder.row1_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row1.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row1.setVisibility(View.GONE);
                    }


                    if (!holder.row2_rank.getText().toString().isEmpty() && !holder.row2_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row2.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row2.setVisibility(View.GONE);
                    }



                    if (!holder.row3_rank.getText().toString().isEmpty() && !holder.row3_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row3.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row3.setVisibility(View.GONE);
                    }



                    if (!holder.row4_rank.getText().toString().isEmpty() && !holder.row4_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row4.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row4.setVisibility(View.GONE);
                    }



                    if (!holder.row5_rank.getText().toString().isEmpty() && !holder.row5_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row5.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row5.setVisibility(View.GONE);
                    }


                    if (!holder.row6_rank.getText().toString().isEmpty() && !holder.row6_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row6.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row6.setVisibility(View.GONE);
                    }



                    if (!holder.row7_rank.getText().toString().isEmpty() && !holder.row7_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row7.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row7.setVisibility(View.GONE);
                    }



                    if (!holder.row8_rank.getText().toString().isEmpty() && !holder.row8_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row8.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row8.setVisibility(View.GONE);
                    }



                    if (!holder.row9_rank.getText().toString().isEmpty() && !holder.row9_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row9.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row9.setVisibility(View.GONE);
                    }



                    if (!holder.row10_rank.getText().toString().isEmpty() && !holder.row10_price.getText().toString().isEmpty())
                    {
                        holder.relativeLayout_row10.setVisibility(View.VISIBLE);

                    }
                    else {
                        holder.relativeLayout_row10.setVisibility(View.GONE);
                    }





                    winning_breakup.show();
                }
            });



















        }catch (Exception e)
        {
            Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show();
        }




















    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GameHolder extends RecyclerView.ViewHolder{

        RelativeLayout relativeLayout_joincontest;
        TextView data1,data2,data3,data4,data5,data6,data7,data8,data9,data10,data11,data12,data13,data14,data15,data16,data17,data18,data19,data20,data21,data22;




        ProgressBar progressBar;



        LinearLayout linearLayout_winners,linearLayout_perkill;
        View divider_view;


        //Winning Breakup
        RelativeLayout relativeLayout_row1,relativeLayout_row2,relativeLayout_row3,relativeLayout_row4,relativeLayout_row5,
                        relativeLayout_row6,relativeLayout_row7,relativeLayout_row8,relativeLayout_row9,relativeLayout_row10;
        TextView row1_rank,row2_rank,row3_rank,row4_rank,row5_rank,row6_rank,row7_rank,row8_rank,row9_rank,row10_rank;
        TextView row1_price,row2_price,row3_price,row4_price,row5_price,row6_price,row7_price,row8_price,row9_price,row10_price;
        TextView pool_prize;
        RelativeLayout relativeLayout_joined;
        View close;
        public GameHolder(@NonNull View itemView) {
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


            relativeLayout_joincontest=itemView.findViewById(R.id.gamecard_relative_layout_joincontest);
            relativeLayout_joined=itemView.findViewById(R.id.gamecard_relative_layout_joined);



            progressBar=itemView.findViewById(R.id.gamecard_progressbar);


            linearLayout_winners=itemView.findViewById(R.id.gamecard_linearlayout_winner);
            linearLayout_perkill=itemView.findViewById(R.id.gamecard_linearlayout_perkill);
            divider_view=itemView.findViewById(R.id.divider_view);




            relativeLayout_row1=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row1);
            relativeLayout_row2=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row2);
            relativeLayout_row3=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row3);
            relativeLayout_row4=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row4);
            relativeLayout_row5=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row5);
            relativeLayout_row6=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row6);
            relativeLayout_row7=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row7);
            relativeLayout_row8=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row8);
            relativeLayout_row9=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row9);
            relativeLayout_row10=winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row10);


            row1_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row1_rank);
            row2_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row2_rank);
            row3_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row3_rank);
            row4_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row4_rank);
            row5_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row5_rank);
            row6_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row6_rank);
            row7_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row7_rank);
            row8_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row8_rank);
            row9_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row9_rank);
            row10_rank=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row10_rank);


            row1_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row1_price);
            row2_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row2_price);
            row3_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row3_price);
            row4_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row4_price);
            row5_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row5_price);
            row6_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row6_price);
            row7_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row7_price);
            row8_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row8_price);
            row9_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row9_price);
            row10_price=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row10_price);

            pool_prize=winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_pool_prize);
            close=winning_breakup.findViewById(R.id.winning_breakup_dialog_view_close);

        }
    }




}
