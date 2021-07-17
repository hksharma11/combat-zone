package com.e.zone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.e.zone.Model.Game;
import com.e.zone.Model.Result;
import com.e.zone.MyAdapter.NotificationAdapter;
import com.e.zone.MyAdapter.ResultsAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import am.appwise.components.ni.NoInternetDialog;

public class Results extends AppCompatActivity {

    FirebaseFirestore mydb;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Result> recycler_data;
    Dialog loading;

    private InterstitialAd mInterstitialAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        mydb=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.results_recycler);
        recycler_data=new ArrayList<>();
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);
        new NoInternetDialog.Builder(this).build();
        findViewById(R.id.results_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Results.this,More.class);
                startActivity(intent);
                finish();
            }
        });

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7712739497173319/4386088172");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        }, 2000);

        loading.show();
        mydb.collection("WinnersList").orderBy("sorting", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                recycler_data.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {

                   final Result data=new Result();

                    try {
                        data.setDate(doc.getData().get("date").toString());
                        data.setGamename(doc.getData().get("gamename").toString());

                        data.setRow1_rank(doc.getData().get("row1_rank").toString());
                        data.setRow2_rank(doc.getData().get("row2_rank").toString());
                        data.setRow3_rank(doc.getData().get("row3_rank").toString());
                        data.setRow4_rank(doc.getData().get("row4_rank").toString());
                        data.setRow5_rank(doc.getData().get("row5_rank").toString());
                        data.setRow6_rank(doc.getData().get("row6_rank").toString());
                        data.setRow7_rank(doc.getData().get("row7_rank").toString());
                        data.setRow8_rank(doc.getData().get("row8_rank").toString());
                        data.setRow9_rank(doc.getData().get("row9_rank").toString());
                        data.setRow10_rank(doc.getData().get("row10_rank").toString());


                        data.setRow1_name(doc.getData().get("row1_name").toString());
                        data.setRow2_name(doc.getData().get("row2_name").toString());
                        data.setRow3_name(doc.getData().get("row3_name").toString());
                        data.setRow4_name(doc.getData().get("row4_name").toString());
                        data.setRow5_name(doc.getData().get("row5_name").toString());
                        data.setRow6_name(doc.getData().get("row6_name").toString());
                        data.setRow7_name(doc.getData().get("row7_name").toString());
                        data.setRow8_name(doc.getData().get("row8_name").toString());
                        data.setRow9_name(doc.getData().get("row9_name").toString());
                        data.setRow10_name(doc.getData().get("row10_name").toString());



                        data.setRow1_price(doc.getData().get("row1_price").toString());
                        data.setRow2_price(doc.getData().get("row2_price").toString());
                        data.setRow3_price(doc.getData().get("row3_price").toString());
                        data.setRow4_price(doc.getData().get("row4_price").toString());
                        data.setRow5_price(doc.getData().get("row5_price").toString());
                        data.setRow6_price(doc.getData().get("row6_price").toString());
                        data.setRow7_price(doc.getData().get("row7_price").toString());
                        data.setRow8_price(doc.getData().get("row8_price").toString());
                        data.setRow9_price(doc.getData().get("row9_price").toString());
                        data.setRow10_price(doc.getData().get("row10_price").toString());

                    }catch (Exception e1)
                    {
                        Toast.makeText(Results.this, e1.toString(), Toast.LENGTH_SHORT).show();
                    }

















                    recycler_data.add(data);



                }

                loading.dismiss();
                recyclerView.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(Results.this);
                recyclerView.setLayoutManager(layoutManager);

                ResultsAdapter myAdapter=new ResultsAdapter(Results.this,recycler_data);
                recyclerView.setAdapter(myAdapter);

            }
        });


    }
}