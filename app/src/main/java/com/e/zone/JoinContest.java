package com.e.zone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.e.zone.Model.Game;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Objects;

import am.appwise.components.ni.NoInternetDialog;

public class JoinContest extends AppCompatActivity implements RewardedVideoAdListener {

    TextView textView_balance,textView_topay,rule1,rule2;
    EditText editText_user_gamename;
    Button button_join_contest;
    String game_id;
    String gamename_for_collection;
    FirebaseFirestore mydb;

    int int_balance;
    int int_entry_fee;
    Dialog loading;


    private RewardedVideoAd mRewardedVideoAd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.join_contest);
        final Intent intent=getIntent();
        game_id=intent.getStringExtra("game_id");
        gamename_for_collection=intent.getStringExtra("gamename");
        textView_balance=findViewById(R.id.join_contest_textview_balance);
       textView_topay=findViewById(R.id.join_contest_textview_payamounnt);
       rule1=findViewById(R.id.join_contest_textview_rule1);
       rule2=findViewById(R.id.join_contest_textview_rule2);
       editText_user_gamename=findViewById(R.id.join_contest_edittext_username);
       button_join_contest=findViewById(R.id.join_contest_button_joingame);
       mydb=FirebaseFirestore.getInstance();
       Shared_data shared_data=new Shared_data(this);
       final String phone=shared_data.getPhone();
        textView_topay.setText(intent.getStringExtra("entry_fee"));
        int_entry_fee=Integer.parseInt(Objects.requireNonNull(intent.getStringExtra("entry_fee")));

        loading=new Dialog(JoinContest.this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);

        Log.d("363636","Task1");

       mydb.collection("Users").document(phone).addSnapshotListener(new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               textView_balance.setText(value.getData().get("Balance").toString());
               int_balance=Integer.parseInt(value.getData().get("Balance").toString());

           }
       });


//       mydb.collection("Users").document(phone).collection("MyContest").document(game_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//           @Override
//           public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//               DocumentSnapshot documentSnapshot=task.getResult();
//               if (documentSnapshot.exists())
//               {
//                   button_join_contest.setText("Already Joined");
//               }
//           }
//       });




        MobileAds.initialize(this, "ca-app-pub-7712739497173319~7166270547");
        // Use an activity context to get the rewarded video instance.
        mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
        mRewardedVideoAd.setRewardedVideoAdListener(JoinContest.this);
        loadRewardedVideoAd();
        Log.d("363636","Task2");
        loading.show();
        mydb.collection(gamename_for_collection).document(game_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                if (documentSnapshot.exists())
                {
                    String r1=documentSnapshot.getData().get("rule1").toString();
                    r1=r1.replace("69","\n");
                    rule1.setText(r1);
                    String r2=documentSnapshot.getData().get("rule2").toString();
                    r2=r2.replace("69","\n");
                    rule2.setText(r2);
                    loading.dismiss();
                }
            }
        });


        new NoInternetDialog.Builder(this).build();

       button_join_contest.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (!editText_user_gamename.getText().toString().isEmpty())
               {
                  if (int_balance>=int_entry_fee)
                  {
                      loading.show();
                      Transaction transaction=new Transaction();
                      transaction.perform_transaction(phone,"Joined a Contest",int_entry_fee,int_balance,"-");


                      final String  UUID = OneSignal.getPermissionSubscriptionState().getSubscriptionStatus().getUserId();
                      final HashMap<String,Object> participant_data=new HashMap<>();
                      Long rightnow=Calendar.getInstance().getTimeInMillis();
                      participant_data.put("winning_payment","");
                      participant_data.put("username",editText_user_gamename.getText().toString());
                      participant_data.put("phone",phone);
                      participant_data.put("screenshot","");
                      participant_data.put("notification_id",UUID);
                     participant_data.put("sorting",rightnow);

                      mydb.collection(gamename_for_collection).document(game_id).collection("Participants").document(phone).set(participant_data).addOnCompleteListener(new OnCompleteListener<Void>() {
                          @Override
                          public void onComplete(@NonNull Task<Void> task) {

                                mydb.collection(gamename_for_collection).document(game_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        DocumentSnapshot documentSnapshot=task.getResult();
                                        Long rightnow= Calendar.getInstance().getTimeInMillis();

                                        final HashMap<String,Object> mygame=new HashMap<>();
                                        mygame.put("game_id", documentSnapshot.getData().get("game_id"));
                                        mygame.put("data1", documentSnapshot.getData().get("data1"));
                                        mygame.put("data2", documentSnapshot.getData().get("data2"));
                                        mygame.put("data3", documentSnapshot.getData().get("data3"));
                                        mygame.put("data4", documentSnapshot.getData().get("data4"));
                                        mygame.put("data5", documentSnapshot.getData().get("data5"));
                                        mygame.put("data6", documentSnapshot.getData().get("data6"));
                                        mygame.put("data7", documentSnapshot.getData().get("data7"));
                                        mygame.put("data8", documentSnapshot.getData().get("data8"));
                                        mygame.put("data9", documentSnapshot.getData().get("data9"));
                                        mygame.put("data10", documentSnapshot.getData().get("data10"));
                                        mygame.put("data11", documentSnapshot.getData().get("data11"));
                                        mygame.put("data12", documentSnapshot.getData().get("data12"));
                                        mygame.put("data13", documentSnapshot.getData().get("data13"));
                                        mygame.put("data14", documentSnapshot.getData().get("data14"));
                                        mygame.put("data15", documentSnapshot.getData().get("data15"));
                                        mygame.put("data16", documentSnapshot.getData().get("data16"));
                                        mygame.put("data17", documentSnapshot.getData().get("data17"));
                                        mygame.put("data18", documentSnapshot.getData().get("data18"));
                                        mygame.put("data19", documentSnapshot.getData().get("data19"));
                                        mygame.put("data20", documentSnapshot.getData().get("data20"));
                                        mygame.put("data21", documentSnapshot.getData().get("data21"));
                                        mygame.put("data22", documentSnapshot.getData().get("data22"));
                                        mygame.put("gamename", documentSnapshot.getData().get("gamename"));
                                        mygame.put("sorting",rightnow);

                                        mydb.collection("Users").document(phone).collection("MyContest").document(game_id).set(mygame).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mydb.collection(gamename_for_collection).document(game_id).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                        if (task.isSuccessful())
                                                        {
                                                            DocumentSnapshot doc=task.getResult();
                                                            if (doc.exists())
                                                            {
                                                                String newParticipants=  Integer.toString(Integer.parseInt(doc.getData().get("joined_players").toString())+1);
                                                                mydb.collection(gamename_for_collection).document(game_id).update("joined_players",newParticipants).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {

                                                                        Toast.makeText(JoinContest.this, "Joined Successfully", Toast.LENGTH_LONG).show();
                                                                        Notify notify=new Notify();
                                                                        final String date= Calendar.getInstance().getTime().toString();
                                                                        notify.push(phone,"You have joined a new match.\nYou can check your details in My Contests section of App.",date,UUID);
                                                                       loading.dismiss();
                                                                        if (mRewardedVideoAd.isLoaded()) {
                                                                            mRewardedVideoAd.show();
                                                                        }
                                                                        else
                                                                        {
                                                                            Intent intent1=new Intent(JoinContest.this,MyContest.class);
                                                                            startActivity(intent1);
                                                                            Log.d("363636","Task3");
                                                                            finish();
                                                                        }

                                                                    }
                                                                });
                                                            }
                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                          }
                      });

                  }
                  else {
                      Toast.makeText(JoinContest.this, "Insufficient Funds", Toast.LENGTH_LONG).show();
                      Intent intent1=new Intent(JoinContest.this,Account.class);
                      Log.d("363636","Task4");
                      startActivity(intent1);
                  }

               }
               else{
                   editText_user_gamename.setError("Empty Field");
               }
           }
       });




    }

    @Override
    public void onRewarded(RewardItem reward) {

    }

    @Override
    public void onRewardedVideoAdLeftApplication() {
        Intent intent1=new Intent(JoinContest.this,MyContest.class);
        startActivity(intent1);
        Log.d("363636","Task5");
        finish();
    }

    @Override
    public void onRewardedVideoAdClosed() {
        Intent intent1=new Intent(JoinContest.this,MyContest.class);
        startActivity(intent1);
        Log.d("363636","Task6");
        finish();
    }

    @Override
    public void onRewardedVideoAdFailedToLoad(int errorCode) {

    }

    @Override
    public void onRewardedVideoAdLoaded() {

    }

    @Override
    public void onRewardedVideoAdOpened() {

    }

    @Override
    public void onRewardedVideoStarted() {

    }

    @Override
    public void onRewardedVideoCompleted() {

    }
    private void loadRewardedVideoAd() {
        mRewardedVideoAd.loadAd("ca-app-pub-7712739497173319/9063699780",
                new AdRequest.Builder().build());
    }
    @Override
    public void onResume() {
        mRewardedVideoAd.resume(this);
        super.onResume();
    }

    @Override
    public void onPause() {
        mRewardedVideoAd.pause(this);
        super.onPause();
    }

    @Override
    public void onDestroy() {
        mRewardedVideoAd.destroy(this);
        super.onDestroy();
    }
}