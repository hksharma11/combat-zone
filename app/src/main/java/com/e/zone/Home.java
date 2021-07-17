package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.e.zone.Model.Homeview;
import com.e.zone.MyAdapter.HomeviewAdapter;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.HashMap;

import javax.annotation.Nullable;

import am.appwise.components.ni.NoInternetDialog;

public class Home extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;
    private AdView mAdView;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Homeview> gamedata;
    FirebaseFirestore mydb;
    Dialog loading;
    LinearLayout nogame_available;

    String android_id;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        Log.d("newtest","on create");

        recyclerView=findViewById(R.id.home_recyclerview);
        gamedata=new ArrayList<>();
        mydb=FirebaseFirestore.getInstance();
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);
        nogame_available=findViewById(R.id.home_linearlayout_nogame);

        loading.show();


                OneSignal.startInit(this)
                .inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
                .unsubscribeWhenNotificationsAreDisabled(true)
                .init();





        bottomNavigationView=findViewById(R.id.home_bottom_nav);






        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);



        new NoInternetDialog.Builder(this).build();





        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){



                    case R.id.mycontest:
                        Intent intent2=new Intent(Home.this, MyContest.class);
                        startActivity(intent2);

                        finish();

                        return true;



                    case R.id.more:
                        Intent intent3=new Intent(Home.this , More.class);
                        startActivity(intent3);
//                        CustomIntent.customType(Dashboard.this,"left-to-right");

                        finish();

                        return true;


                    case R.id.account:
                        Intent intent4=new Intent(Home.this , Account.class);
                        startActivity(intent4);
//                        CustomIntent.customType(Dashboard.this,"left-to-right");
                        finish();

                        return true;


//                    case R.id.lottery:
//                        Intent intent5= new Intent(Dashboard.this,Lottery.class);
//                        startActivity(intent5);
//                        CustomIntent.customType(Dashboard.this,"fadein-to-fadeout");
//                        finish();
//
//                        return true;



                    default:
                        return false;





                }
            }


        });

        Shared_data shared_data =new Shared_data(this);
        final String phone=shared_data.getPhone();
        final String  UUID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
        findViewById(R.id.home_imageview_notification).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(Home.this,Notification.class);
                startActivity(intent);

//                Notify notify=new Notify();
//                notify.push(phone,"This is new message","aaj hai do",UUID);
            }
        });






        try {
            android_id = Settings.Secure.getString(getContentResolver(),
                    Settings.Secure.ANDROID_ID);
        }
        catch (Exception e)
        {

        }

        mydb.collection("Users").document(phone).update("android_id",android_id,"uuid",UUID);


        mydb.collection("Home_Data").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot queryDocumentSnapshots, @Nullable FirebaseFirestoreException e) {

                gamedata.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {

                    final Homeview data=new Homeview();


                        data.setName(doc.getData().get("Name").toString());
                        data.setLink(doc.getData().get("Link").toString());
                        data.setGamename(doc.getData().get("gamename").toString());

                        Log.d("#36323236",doc.getData().get("Link").toString());















                        gamedata.add(data);








                }
                loading.dismiss();

                if (gamedata.size()==0)
                {
                    loading.dismiss();
                    nogame_available.setVisibility(View.VISIBLE);
                }
                recyclerView.setHasFixedSize(true);
                layoutManager=new GridLayoutManager(Home.this,3);
                recyclerView.setLayoutManager(layoutManager);

                HomeviewAdapter myAdapter=new HomeviewAdapter(Home.this,gamedata);
                recyclerView.setAdapter(myAdapter);

            }
        });














    }
    @Override
    protected void onStart() {
        super.onStart();
        Shared_data shared_data=new Shared_data(this);
        final String phone=shared_data.getPhone();
        mydb.collection("Users").document(phone).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e!=null)
                {
                    Toast.makeText(Home.this, e.toString(), Toast.LENGTH_SHORT).show();
                    return;

                }

                if (!android_id.equals(documentSnapshot.getData().get("android_id").toString()))
                {
                    Shared_data shared_data=new Shared_data(Home.this);
                    shared_data.removeUser();

                    FirebaseAuth.getInstance().signOut();
                    Intent intent=new Intent(Home.this,SignIn.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }


            }
        });




    }



}