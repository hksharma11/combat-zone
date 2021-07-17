package com.e.zone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.zone.Model.Game;
import com.e.zone.MyAdapter.MyContestAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import am.appwise.components.ni.NoInternetDialog;

public class MyContest extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Game> recycler_data;
    FirebaseFirestore mydb;
    
    BottomNavigationView bottomNavigationView;
    Dialog loading;
    private AdView mAdView;
    LinearLayout nogame_available;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_contest);
        nogame_available=findViewById(R.id.my_contest_linearlayout_nogame);
        recyclerView=findViewById(R.id.mycontes_recycler);
        recycler_data=new ArrayList<>();
        mydb=FirebaseFirestore.getInstance();
        
        Shared_data shared_data=new Shared_data(this);
        String phone=shared_data.getPhone();

        Log.d("#7007","Task1");
        loading=new Dialog(MyContest.this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.my_contest_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        bottomNavigationView=findViewById(R.id.Mycontest_bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){







                    case R.id.more:
                        Intent intent3=new Intent(MyContest.this , More.class);
                        startActivity(intent3);
//                        CustomIntent.customType(Dashboard.this,"left-to-right");

                        finish();

                        return true;


                    case R.id.account:
                        Intent intent4=new Intent(MyContest.this , Account.class);
                        startActivity(intent4);
//                        CustomIntent.customType(Dashboard.this,"left-to-right");
                        finish();

                        return true;


                    case R.id.home:
                        Intent intent5= new Intent(MyContest.this,Home.class);
                        startActivity(intent5);

                        finish();

                        return true;



                    default:
                        return false;





                }
            }


        });















        new NoInternetDialog.Builder(this).build();

        loading.show();
        
        
        mydb.collection("Users").document(phone).collection("MyContest").orderBy("sorting", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException error) {

                recycler_data.clear();
                for (QueryDocumentSnapshot doc:queryDocumentSnapshots)
                {
                    Log.d("#7007","Task2");

                    final Game data=new Game();

                    try {
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
//                        data.setData20(doc.getData().get("data20").toString());
//                        data.setData21(doc.getData().get("data21").toString());
                        data.setData22("Check Room ID & Password");
                        data.setGame_id(doc.getData().get("game_id").toString());
                        data.setGamename(doc.getData().get("gamename").toString());


                    }
                    catch (Exception e1)
                    {
                        Log.d("#7007","error");
                        Toast.makeText(MyContest.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                        Log.d("#7007", e1.getLocalizedMessage());

                    }














                    recycler_data.add(data);




                }
                if (recycler_data.size()==0)
                {
                    loading.dismiss();
                    nogame_available.setVisibility(View.VISIBLE);
                }
                else {
                    loading.dismiss();
                    nogame_available.setVisibility(View.GONE);
                }
                loading.dismiss();

                recyclerView.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(MyContest.this);
                recyclerView.setLayoutManager(layoutManager);

                MyContestAdapter myAdapter=new MyContestAdapter(MyContest.this,recycler_data);
                recyclerView.setAdapter(myAdapter);
                
                
                
            }
        });


      

    }
}