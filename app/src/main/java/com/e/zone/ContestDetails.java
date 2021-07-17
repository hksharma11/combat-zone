package com.e.zone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.type.Color;

import org.w3c.dom.Text;

import java.util.Calendar;

import am.appwise.components.ni.NoInternetDialog;

public class ContestDetails extends AppCompatActivity {
    String game_id;

    TextView data1,data2,data3,data4,data5,data6,data7,data8,data9,data10,data11,data12,data13,data14,data15,data16,data17,data18,data19;

    TextView textView_room_id, textView_room_password,textView_room_slot,textView_room_message;
    LinearLayout linearLayout_room_id,linearLayout_room_password,linearLayout_room_slot;

    FirebaseFirestore mydb;
    String gamename_for_collection;


    BottomSheetDialog bottomSheetDialog_winning_breakup;



    //winning breakup
    RelativeLayout relativeLayout_row1,relativeLayout_row2,relativeLayout_row3,relativeLayout_row4,relativeLayout_row5,
            relativeLayout_row6,relativeLayout_row7,relativeLayout_row8,relativeLayout_row9,relativeLayout_row10;
    TextView row1_rank,row2_rank,row3_rank,row4_rank,row5_rank,row6_rank,row7_rank,row8_rank,row9_rank,row10_rank;
    TextView row1_price,row2_price,row3_price,row4_price,row5_price,row6_price,row7_price,row8_price,row9_price,row10_price;


    RelativeLayout relativeLayout_parent,relativeLayout_upload_screenshot,relativeLayout_review,relativeLayout_winners;
    String status;
    
    //uploading screenshot
    final int image_request=1;
    Uri imagepath;
    String phone;
    StorageReference storageReference;
    Dialog loading;

    //for rule dialog
    TextView textView_rule1,textView_rule2,textView_rule3,textView_rule4,textView_rule5,textView_rule6,textView_rule7,textView_rule8,textView_rule9,textView_rule10;


    private InterstitialAd mInterstitialAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest_details);
        Intent intent=getIntent();
        game_id=intent.getStringExtra("game_id");
        gamename_for_collection=intent.getStringExtra("gamename");
        relativeLayout_parent=findViewById(R.id.contest_details_relative_parent);
        relativeLayout_upload_screenshot=findViewById(R.id.contest_details_relative_upload_screenshot);
        relativeLayout_review=findViewById(R.id.contest_details_relative_review);
        relativeLayout_winners=findViewById(R.id.contest_details_relative_winners);
        mydb=FirebaseFirestore.getInstance();
        bottomSheetDialog_winning_breakup=new BottomSheetDialog(this);
        Shared_data shared_data=new Shared_data(this);
        phone=shared_data.getPhone();
        storageReference= FirebaseStorage.getInstance().getReference("Image");
        loading=new Dialog(ContestDetails.this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7712739497173319/8133761498");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });

       // data1=findViewById(R.id.contest_details_data1);
       // data2=findViewById(R.id.contest_details_data2);
        data3=findViewById(R.id.contest_details_data3);
        data4=findViewById(R.id.contest_details_data4);
        data5=findViewById(R.id.contest_details_data5);
        data6=findViewById(R.id.contest_details_data6);
        data7=findViewById(R.id.contest_details_data7);
        data8=findViewById(R.id.contest_details_data8);
        data9=findViewById(R.id.contest_details_data9);
        data10=findViewById(R.id.contest_details_data10);
        data11=findViewById(R.id.contest_details_data11);
        data12=findViewById(R.id.contest_details_data12);
        data13=findViewById(R.id.contest_details_data13);
        data14=findViewById(R.id.contest_details_data14);
        data15=findViewById(R.id.contest_details_data15);
        data16=findViewById(R.id.contest_details_data16);
        data17=findViewById(R.id.contest_details_data17);
        data18=findViewById(R.id.contest_details_data18);
        data19=findViewById(R.id.contest_details_data19);



        textView_room_id=findViewById(R.id.contest_details_textview_roomid);
        textView_room_password=findViewById(R.id.contest_details_textview_room_password);
        textView_room_slot=findViewById(R.id.contest_details_textview_slot);
        textView_room_message=findViewById(R.id.contest_details_textview_message);

        linearLayout_room_slot=findViewById(R.id.contest_details_linearLayout_slot);
        linearLayout_room_id=findViewById(R.id.contest_details_linearLayout_roomid);
        linearLayout_room_password=findViewById(R.id.contest_details_linearLayout_room_password);


        final AlertDialog.Builder builder=new AlertDialog.Builder(ContestDetails.this);
        final  View view1=getLayoutInflater().inflate(R.layout.winning_breakup_dialog,null,false);
        builder.setView(view1);
        bottomSheetDialog_winning_breakup.setContentView(view1);

        relativeLayout_row1=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row1);
        relativeLayout_row2=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row2);
        relativeLayout_row3=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row3);
        relativeLayout_row4=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row4);
        relativeLayout_row5=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row5);
        relativeLayout_row6=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row6);
        relativeLayout_row7=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row7);
        relativeLayout_row8=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row8);
        relativeLayout_row9=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row9);
        relativeLayout_row10=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_relative_layout_row10);


        row1_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row1_rank);
        row2_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row2_rank);
        row3_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row3_rank);
        row4_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row4_rank);
        row5_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row5_rank);
        row6_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row6_rank);
        row7_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row7_rank);
        row8_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row8_rank);
        row9_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row9_rank);
        row10_rank=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row10_rank);


        row1_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row1_price);
        row2_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row2_price);
        row3_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row3_price);
        row4_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row4_price);
        row5_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row5_price);
        row6_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row6_price);
        row7_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row7_price);
        row8_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row8_price);
        row9_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row9_price);
        row10_price=bottomSheetDialog_winning_breakup.findViewById(R.id.winning_breakup_dialog_textview_row10_price);


        //rules dialog
        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(ContestDetails.this);
        final View view=getLayoutInflater().inflate(R.layout.rules_dialog,null,false);
        mBuilder.setView(view);
        final AlertDialog ruleDialog=mBuilder.create();

        textView_rule1=view.findViewById(R.id.rules_dialog_rule1);
        textView_rule2=view.findViewById(R.id.rules_dialog_rule2);
        textView_rule3=view.findViewById(R.id.rules_dialog_rule3);
        textView_rule4=view.findViewById(R.id.rules_dialog_rule4);
        textView_rule5=view.findViewById(R.id.rules_dialog_rule5);
        textView_rule6=view.findViewById(R.id.rules_dialog_rule6);
        textView_rule7=view.findViewById(R.id.rules_dialog_rule7);
        textView_rule8=view.findViewById(R.id.rules_dialog_rule8);
        textView_rule9=view.findViewById(R.id.rules_dialog_rule9);
        textView_rule10=view.findViewById(R.id.rules_dialog_rule10);

        // winnerslist
        final AlertDialog.Builder b=new AlertDialog.Builder(ContestDetails.this);
        final View v=getLayoutInflater().inflate(R.layout.winnerslist,null,false);
        b.setView(v);
        final AlertDialog winnersList=b.create();

        final TextView textView_winnerList_gamename=v.findViewById(R.id.winnerslist_textview_gamename);
        final TextView textView_winnerList_date=v.findViewById(R.id.winnerslist_textview_date_time);


        final TextView[] textView_winnerList_rank = new TextView[11];
        textView_winnerList_rank[1]=v.findViewById(R.id.winnerslist_textview_row1_rank);
        textView_winnerList_rank[2]=v.findViewById(R.id.winnerslist_textview_row2_rank);
        textView_winnerList_rank[3]=v.findViewById(R.id.winnerslist_textview_row3_rank);
        textView_winnerList_rank[4]=v.findViewById(R.id.winnerslist_textview_row4_rank);
        textView_winnerList_rank[5]=v.findViewById(R.id.winnerslist_textview_row5_rank);
        textView_winnerList_rank[6]=v.findViewById(R.id.winnerslist_textview_row6_rank);
        textView_winnerList_rank[7]=v.findViewById(R.id.winnerslist_textview_row7_rank);
        textView_winnerList_rank[8]=v.findViewById(R.id.winnerslist_textview_row8_rank);
        textView_winnerList_rank[9]=v.findViewById(R.id.winnerslist_textview_row9_rank);
        textView_winnerList_rank[10]=v.findViewById(R.id.winnerslist_textview_row10_rank);

        final TextView[] textView_winnerList_name = new TextView[11];
        textView_winnerList_name[1]=v.findViewById(R.id.winnerslist_textview_row1_name);
        textView_winnerList_name[2]=v.findViewById(R.id.winnerslist_textview_row2_name);
        textView_winnerList_name[3]=v.findViewById(R.id.winnerslist_textview_row3_name);
        textView_winnerList_name[4]=v.findViewById(R.id.winnerslist_textview_row4_name);
        textView_winnerList_name[5]=v.findViewById(R.id.winnerslist_textview_row5_name);
        textView_winnerList_name[6]=v.findViewById(R.id.winnerslist_textview_row6_name);
        textView_winnerList_name[7]=v.findViewById(R.id.winnerslist_textview_row7_name);
        textView_winnerList_name[8]=v.findViewById(R.id.winnerslist_textview_row8_name);
        textView_winnerList_name[9]=v.findViewById(R.id.winnerslist_textview_row9_name);
        textView_winnerList_name[10]=v.findViewById(R.id.winnerslist_textview_row10_name);

        final TextView[] textView_winnerList_price = new TextView[11];
        textView_winnerList_price[1]=v.findViewById(R.id.winnerslist_textview_row1_price);
        textView_winnerList_price[2]=v.findViewById(R.id.winnerslist_textview_row2_price);
        textView_winnerList_price[3]=v.findViewById(R.id.winnerslist_textview_row3_price);
        textView_winnerList_price[4]=v.findViewById(R.id.winnerslist_textview_row4_price);
        textView_winnerList_price[5]=v.findViewById(R.id.winnerslist_textview_row5_price);
        textView_winnerList_price[6]=v.findViewById(R.id.winnerslist_textview_row6_price);
        textView_winnerList_price[7]=v.findViewById(R.id.winnerslist_textview_row7_price);
        textView_winnerList_price[8]=v.findViewById(R.id.winnerslist_textview_row8_price);
        textView_winnerList_price[9]=v.findViewById(R.id.winnerslist_textview_row9_price);
        textView_winnerList_price[10]=v.findViewById(R.id.winnerslist_textview_row10_price);

        final RelativeLayout[] relativeLayouts_winnerList_row = new RelativeLayout[11];
        relativeLayouts_winnerList_row[1]=v.findViewById(R.id.winnerslist_textview_row1);
        relativeLayouts_winnerList_row[2]=v.findViewById(R.id.winnerslist_textview_row2);
        relativeLayouts_winnerList_row[3]=v.findViewById(R.id.winnerslist_textview_row3);
        relativeLayouts_winnerList_row[4]=v.findViewById(R.id.winnerslist_textview_row4);
        relativeLayouts_winnerList_row[5]=v.findViewById(R.id.winnerslist_textview_row5);
        relativeLayouts_winnerList_row[6]=v.findViewById(R.id.winnerslist_textview_row6);
        relativeLayouts_winnerList_row[7]=v.findViewById(R.id.winnerslist_textview_row7);
        relativeLayouts_winnerList_row[8]=v.findViewById(R.id.winnerslist_textview_row8);
        relativeLayouts_winnerList_row[9]=v.findViewById(R.id.winnerslist_textview_row9);
        relativeLayouts_winnerList_row[10]=v.findViewById(R.id.winnerslist_textview_row10);




        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mInterstitialAd.isLoaded()) {
                    mInterstitialAd.show();
                }
            }
        }, 2000);




        data15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


               bottomSheetDialog_winning_breakup.show();

            }
        });


        relativeLayout_upload_screenshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (status.isEmpty())
                {
                    status="1";
                }


                if (status.equals("1"))
                {

                    selectimage();


                }
                else if(status.equals("2")) {
                    Snackbar.make(relativeLayout_parent,"You cannot upload screenshot now. Winners are announced.",Snackbar.LENGTH_LONG).show();
                }
                else{
                    Snackbar.make(relativeLayout_parent,"You can upload a screenshot once game is in progress or review mode.",Snackbar.LENGTH_LONG).show();
                }



            }
        });



        findViewById(R.id.contest_details_rules).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ruleDialog.show();
            }
        });

        relativeLayout_winners.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                winnersList.show();
            }
        });

        mydb.collection(gamename_for_collection).document(game_id).collection("Extra").document("Rules").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {

                    Toast.makeText(ContestDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    return;
                }



                if (!value.getData().get("rule1").toString().isEmpty())
                {
                    textView_rule1.setVisibility(View.VISIBLE);
                    textView_rule1.setText(value.getData().get("rule1").toString());
                }

                if (!value.getData().get("rule2").toString().isEmpty())
                {
                    textView_rule2.setVisibility(View.VISIBLE);
                    textView_rule2.setText(value.getData().get("rule2").toString());
                }

                if (!value.getData().get("rule3").toString().isEmpty())
                {
                    textView_rule3.setVisibility(View.VISIBLE);
                    textView_rule3.setText(value.getData().get("rule3").toString());
                }

                if (!value.getData().get("rule4").toString().isEmpty())
                {
                    textView_rule4.setVisibility(View.VISIBLE);
                    textView_rule4.setText(value.getData().get("rule4").toString());
                }

                if (!value.getData().get("rule5").toString().isEmpty())
                {
                    textView_rule5.setVisibility(View.VISIBLE);
                    textView_rule5.setText(value.getData().get("rule5").toString());
                }

                if (!value.getData().get("rule6").toString().isEmpty())
                {
                    textView_rule6.setVisibility(View.VISIBLE);
                    textView_rule6.setText(value.getData().get("rule6").toString());
                }

                if (!value.getData().get("rule7").toString().isEmpty())
                {
                    textView_rule7.setVisibility(View.VISIBLE);
                    textView_rule7.setText(value.getData().get("rule7").toString());
                }

                if (!value.getData().get("rule8").toString().isEmpty())
                {
                    textView_rule8.setVisibility(View.VISIBLE);
                    textView_rule8.setText(value.getData().get("rule8").toString());
                }

                if (!value.getData().get("rule9").toString().isEmpty())
                {
                    textView_rule9.setVisibility(View.VISIBLE);
                    textView_rule9.setText(value.getData().get("rule9").toString());
                }

                if (!value.getData().get("rule10").toString().isEmpty())
                {
                    textView_rule10.setVisibility(View.VISIBLE);
                    textView_rule10.setText(value.getData().get("rule10").toString());
                }


            }
        });





        new NoInternetDialog.Builder(this).build();

        loading.show();
        mydb.collection(gamename_for_collection).document(game_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {
                    loading.dismiss();
                    Toast.makeText(ContestDetails.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    return;
                }
                status=documentSnapshot.getData().get("status").toString();
                textView_room_id.setText(documentSnapshot.getData().get("room_id").toString());
                textView_room_password.setText(documentSnapshot.getData().get("room_password").toString());
                textView_room_message.setText(documentSnapshot.getData().get("room_message").toString());


               if (status.equals("1"))
               {
                   relativeLayout_winners.setVisibility(View.GONE);
                   relativeLayout_review.setVisibility(View.VISIBLE);
               }
               if (status.equals("2"))
               {
                   relativeLayout_review.setVisibility(View.GONE);
                   relativeLayout_winners.setVisibility(View.VISIBLE);
                   mydb.collection("WinnersList").document(game_id).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                       @Override
                       public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                           if (error!=null)
                           {
                               Toast.makeText(ContestDetails.this, error.toString(), Toast.LENGTH_SHORT).show();
                               return;
                           }


                           for(int i=1;i<11;i++)
                           {
                               assert value != null;
                               if (!value.getData().get("row"+i+"_name").toString().isEmpty() && !value.getData().get("row"+i+"_rank").toString().isEmpty() && !value.getData().get("row"+i+"_price").toString().isEmpty())
                               {
                                   relativeLayouts_winnerList_row[i].setVisibility(View.VISIBLE);
                                   textView_winnerList_rank[i].setText(value.getData().get("row"+i+"_rank").toString());
                                   textView_winnerList_name[i].setText(value.getData().get("row"+i+"_name").toString());
                                   textView_winnerList_price[i].setText(value.getData().get("row"+i+"_price").toString());
                               }
                           }

                           textView_winnerList_gamename.setText(value.getData().get("gamename").toString());
                           textView_winnerList_date.setText(value.getData().get("date").toString());
                       }
                   });
               }
               if (status.equals("0"))
               {
                   relativeLayout_review.setVisibility(View.GONE);
                   relativeLayout_winners.setVisibility(View.GONE);
               }







                if (!documentSnapshot.getData().get("room_slot").toString().isEmpty())
                {
                    textView_room_slot.setText(documentSnapshot.getData().get("room_slot").toString());
                }
                else {
                    linearLayout_room_slot.setVisibility(View.INVISIBLE);
                }

                //data1.setText(documentSnapshot.getData().get("data1").toString());
                //data2.setText(documentSnapshot.getData().get("data2").toString());
                data3.setText(documentSnapshot.getData().get("data3").toString());
                data4.setText(documentSnapshot.getData().get("data4").toString());
                data5.setText(documentSnapshot.getData().get("data5").toString());
                data6.setText(documentSnapshot.getData().get("data6").toString());
                data7.setText(documentSnapshot.getData().get("data7").toString());
                data8.setText(documentSnapshot.getData().get("data8").toString());
                data9.setText(documentSnapshot.getData().get("data9").toString());
                data10.setText(documentSnapshot.getData().get("data10").toString());
                data11.setText(documentSnapshot.getData().get("data11").toString());
                data12.setText(documentSnapshot.getData().get("data12").toString());
                data13.setText(documentSnapshot.getData().get("data13").toString());
                data14.setText(documentSnapshot.getData().get("data14").toString());
                data15.setText(documentSnapshot.getData().get("data15").toString());
                data16.setText(documentSnapshot.getData().get("data16").toString());
                data17.setText(documentSnapshot.getData().get("data17").toString());
                data18.setText(documentSnapshot.getData().get("data18").toString());
                data19.setText(documentSnapshot.getData().get("data19").toString());



                //Winning Breakup
                row1_rank.setText(documentSnapshot.getData().get("row1_rank").toString());
                row2_rank.setText(documentSnapshot.getData().get("row2_rank").toString());
                row3_rank.setText(documentSnapshot.getData().get("row3_rank").toString());
                row4_rank.setText(documentSnapshot.getData().get("row4_rank").toString());
                row5_rank.setText(documentSnapshot.getData().get("row5_rank").toString());
                row6_rank.setText(documentSnapshot.getData().get("row6_rank").toString());
                row7_rank.setText(documentSnapshot.getData().get("row7_rank").toString());
                row8_rank.setText(documentSnapshot.getData().get("row8_rank").toString());
                row9_rank.setText(documentSnapshot.getData().get("row9_rank").toString());
                row10_rank.setText(documentSnapshot.getData().get("row10_rank").toString());
                



                row1_price.setText(documentSnapshot.getData().get("row1_price").toString());
                row2_price.setText(documentSnapshot.getData().get("row2_price").toString());
                row3_price.setText(documentSnapshot.getData().get("row3_price").toString());
                row4_price.setText(documentSnapshot.getData().get("row4_price").toString());
                row5_price.setText(documentSnapshot.getData().get("row5_price").toString());
                row6_price.setText(documentSnapshot.getData().get("row6_price").toString());
                row7_price.setText(documentSnapshot.getData().get("row7_price").toString());
                row8_price.setText(documentSnapshot.getData().get("row8_price").toString());
                row9_price.setText(documentSnapshot.getData().get("row9_price").toString());
                row10_price.setText(documentSnapshot.getData().get("row10_price").toString());


                if (!row1_rank.getText().toString().isEmpty() && !row1_price.getText().toString().isEmpty())
                {
                    relativeLayout_row1.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row1.setVisibility(View.GONE);
                }


                if (!row2_rank.getText().toString().isEmpty() && !row2_price.getText().toString().isEmpty())
                {
                    relativeLayout_row2.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row2.setVisibility(View.GONE);
                }



                if (!row3_rank.getText().toString().isEmpty() && !row3_price.getText().toString().isEmpty())
                {
                    relativeLayout_row3.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row3.setVisibility(View.GONE);
                }



                if (!row4_rank.getText().toString().isEmpty() && !row4_price.getText().toString().isEmpty())
                {
                    relativeLayout_row4.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row4.setVisibility(View.GONE);
                }



                if (!row5_rank.getText().toString().isEmpty() && !row5_price.getText().toString().isEmpty())
                {
                    relativeLayout_row5.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row5.setVisibility(View.GONE);
                }


                if (!row6_rank.getText().toString().isEmpty() && !row6_price.getText().toString().isEmpty())
                {
                    relativeLayout_row6.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row6.setVisibility(View.GONE);
                }



                if (!row7_rank.getText().toString().isEmpty() && !row7_price.getText().toString().isEmpty())
                {
                    relativeLayout_row7.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row7.setVisibility(View.GONE);
                }



                if (!row8_rank.getText().toString().isEmpty() && !row8_price.getText().toString().isEmpty())
                {
                    relativeLayout_row8.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row8.setVisibility(View.GONE);
                }



                if (!row9_rank.getText().toString().isEmpty() && !row9_price.getText().toString().isEmpty())
                {
                    relativeLayout_row9.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row9.setVisibility(View.GONE);
                }



                if (!row10_rank.getText().toString().isEmpty() && !row10_price.getText().toString().isEmpty())
                {
                    relativeLayout_row10.setVisibility(View.VISIBLE);

                }
                else {
                    relativeLayout_row10.setVisibility(View.GONE);
                }
                loading.dismiss();
                
          


            }
        });



    }




    public void selectimage(){
        try {
            Log.d("#252525","task2");
            Intent intent=new Intent();
            Log.d("#252525","task3");
            intent.setType("image/*");
            Log.d("#252525","task4");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            Log.d("#252525","task5");
            startActivityForResult(intent, image_request);
            Log.d("#252525","task6");

        }
        catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @androidx.annotation.Nullable Intent data) {

        try {


            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode==image_request && resultCode==RESULT_OK && data!=null&& data.getData()!=null){

                imagepath=data.getData();

//                Bitmap objectBitmap= MediaStore.Images.Media.getBitmap(getContentResolver(),imagepath);
//
//                screenshot.setImageBitmap(objectBitmap);
                loading.show();
                uploadimage();


            }
        }
        catch (Exception e){

            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }





    public void uploadimage(){
        try {
            if(imagepath!=null){
                long rightNow = Calendar.getInstance().getTimeInMillis();

                String nameofimage=phone+"|"+ rightNow +"."+getExtension(imagepath);
                final StorageReference imageref=storageReference.child(nameofimage);

                UploadTask uploadTask=imageref.putFile(imagepath);
                uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()){
                            loading.dismiss();
                            throw task.getException();
                        }
                        return imageref.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()){



                            String link=task.getResult().toString();


                            mydb.collection(gamename_for_collection).document(game_id).collection("Participants").document(phone).update("screenshot",link).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    loading.dismiss();
                                    Toast.makeText(ContestDetails.this, "Uploaded Successfully", Toast.LENGTH_SHORT).show();


                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    loading.dismiss();
                                    Toast.makeText(ContestDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });


                        }
                        else if(!task.isSuccessful()){
                            loading.dismiss();
                            Toast.makeText(ContestDetails.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
            else {
                loading.dismiss();
                Toast.makeText(this, "Select Image", Toast.LENGTH_SHORT).show();
            }

        }catch (Exception e)
        {
            loading.dismiss();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }



    private  String getExtension(Uri uri){
        try {
            ContentResolver contentResolver=getContentResolver();
            MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }catch (Exception e)
        {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return null;
    }






}