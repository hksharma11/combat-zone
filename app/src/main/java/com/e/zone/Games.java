package com.e.zone;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.e.zone.Model.Game;
import com.e.zone.MyAdapter.GameAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

import am.appwise.components.ni.NoInternetDialog;

public class Games extends AppCompatActivity {

    String gamename;

  //  BottomSheetDialog bottomSheetDialog;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Game> gamedata;
    FirebaseFirestore mydb;
    BottomSheetDialog bottomSheetDialog,bottomSheetDialog_winning_breakup;
    String gamename_for_collection;
    TextView toolbar;

    LinearLayout nogame_available;
    Dialog loading;

    private AdView mAdView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.games);
        Intent intent=getIntent();
        gamename=intent.getStringExtra("game");
        gamename_for_collection=intent.getStringExtra("gamename");
        recyclerView=findViewById(R.id.game_recycler);
        gamedata=new ArrayList<>();
        mydb=FirebaseFirestore.getInstance();
        bottomSheetDialog=new BottomSheetDialog(this);
        bottomSheetDialog_winning_breakup=new BottomSheetDialog(this);
        toolbar=findViewById(R.id.games_textview_toolbar);
        nogame_available=findViewById(R.id.games_linearlayout_nogame);
        toolbar.setText(gamename);
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);
        loading.show();
        Log.d("55555","Task1");


        findViewById(R.id.games_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Games.this,Home.class);
                startActivity(intent);
                finish();
            }
        });


        final AlertDialog.Builder builder=new AlertDialog.Builder(Games.this);
        final  View view1=getLayoutInflater().inflate(R.layout.winning_breakup_dialog,null,false);
        builder.setView(view1);
        bottomSheetDialog_winning_breakup.setContentView(view1);


        Log.d("55555","Task2");

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.games_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        new NoInternetDialog.Builder(this).build();


        mydb.collection(gamename_for_collection).whereEqualTo("status","0").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                gamedata.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {

                    final Game data=new Game();

                    try {

                        Log.d("55555","Task3");
                        data.setData1(doc.getData().get("data1").toString());
                        data.setData2(doc.getData().get("data2").toString());
                        data.setData3(doc.getData().get("data3").toString());
                        data.setData4(doc.getData().get("data4").toString());
                        data.setData5(doc.getData().get("data5").toString());
                        data.setData6(doc.getData().get("data6").toString());
                        data.setData7(doc.getData().get("data7").toString());
                        data.setData8(doc.getData().get("data8").toString());
                        data.setData9(doc.getData().get("data9").toString());
                        data.setData10(doc.getData().get("data10").toString());
                        data.setData11(doc.getData().get("data11").toString());
                        data.setData12(doc.getData().get("data12").toString());
                        data.setData13(doc.getData().get("data13").toString());
                        data.setData14(doc.getData().get("data14").toString());
                        data.setData15(doc.getData().get("data15").toString());

                        data.setData16(doc.getData().get("data16").toString());
                        data.setData17(doc.getData().get("data17").toString());
                        data.setData18(doc.getData().get("data18").toString());
                        data.setData19(doc.getData().get("data19").toString());
                        data.setData20(doc.getData().get("data20").toString());
                        data.setData21(doc.getData().get("data21").toString());
                        data.setData22(doc.getData().get("data22").toString());

                        data.setTotal_players(doc.getData().get("total_players").toString());
                        data.setJoined_players(doc.getData().get("joined_players").toString());

                        data.setEntry_fee(doc.getData().get("entry_fee").toString());
                        Log.d("55555","hhhhh");
                        data.setGame_id(doc.getData().get("game_id").toString());

                        data.setGamename(doc.getData().get("gamename").toString());


                        String rule1=doc.getData().get("rule1").toString();
                        rule1=rule1.replaceAll("69","\n");
                        data.setRule1(rule1);


                        String rule2=doc.getData().get("rule2").toString();
                        rule2=rule2.replaceAll("69","\n");
                        data.setRule2(rule2);



                        //winning breakup
                        data.setRow1_rank(doc.getData().get("row1_rank").toString());
                        Log.d("55555","Task4");
                        data.setRow2_rank(doc.getData().get("row2_rank").toString());

                        data.setRow3_rank(doc.getData().get("row3_rank").toString());
                        data.setRow4_rank(doc.getData().get("row4_rank").toString());
                        data.setRow5_rank(doc.getData().get("row5_rank").toString());

                        data.setRow6_rank(doc.getData().get("row6_rank").toString());
                        data.setRow7_rank(doc.getData().get("row7_rank").toString());
                        data.setRow8_rank(doc.getData().get("row8_rank").toString());
                        data.setRow9_rank(doc.getData().get("row9_rank").toString());

                        data.setRow10_rank(doc.getData().get("row10_rank").toString());

                        data.setRow1_price(doc.getData().get("row1_price").toString());
                        Log.d("55555","Task5");
                        data.setRow2_price(doc.getData().get("row2_price").toString());
                        data.setRow3_price(doc.getData().get("row3_price").toString());
                        data.setRow4_price(doc.getData().get("row4_price").toString());

                        data.setRow5_price(doc.getData().get("row5_price").toString());
                        data.setRow6_price(doc.getData().get("row6_price").toString());
                        data.setRow7_price(doc.getData().get("row7_price").toString());
                        data.setRow8_price(doc.getData().get("row8_price").toString());
                        data.setRow9_price(doc.getData().get("row9_price").toString());
                        data.setRow10_price(doc.getData().get("row10_price").toString());

                        Log.d("55555","Task6");


                    }
                    catch (Exception e1)
                    {
                        Toast.makeText(Games.this, "Something went wrongggg", Toast.LENGTH_SHORT).show();

                    }














                    gamedata.add(data);



                }
                loading.dismiss();
                if (gamedata.size()==0)
                {
                    loading.dismiss();
                    nogame_available.setVisibility(View.VISIBLE);
                }
                else {
                    loading.dismiss();
                    nogame_available.setVisibility(View.GONE);
                }


                recyclerView.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(Games.this);
                recyclerView.setLayoutManager(layoutManager);

                GameAdapter myAdapter=new GameAdapter(Games.this,gamedata,loading,bottomSheetDialog_winning_breakup);
                recyclerView.setAdapter(myAdapter);

            }
        });


    }
}