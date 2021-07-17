package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.sql.Ref;
import java.util.HashMap;
import java.util.Random;

import am.appwise.components.ni.NoInternetDialog;

public class Refer extends AppCompatActivity {
    FirebaseFirestore mydb;
    Dialog loading;

    TextView textView_refer_code;
    Button button_get_refer_code,button_refer_now;


    String refer_code="";

    Dialog refer_pay;
    int to_pay_amount;
    int gain_amount;
    String share_link="";


    TextView textView_message,textView_step1,textView_step2,textView_step3,textView_step4,textView_step5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.refer);
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);
        textView_refer_code=findViewById(R.id.refer_textview_refer_code);
        button_get_refer_code=findViewById(R.id.refer_button_get_refer_code);
        mydb=FirebaseFirestore.getInstance();
        Shared_data shared_data=new Shared_data(this);
        final String phone=shared_data.getPhone();
        button_refer_now=findViewById(R.id.refer_button_refer_now);
        refer_pay=new Dialog(this);
        refer_pay.setContentView(R.layout.refer_pay);

        textView_message=findViewById(R.id.refer_textview_message);
        textView_step1=findViewById(R.id.refer_textview_step1);
        textView_step2=findViewById(R.id.refer_textview_step2);
        textView_step3=findViewById(R.id.refer_textview_step3);
        textView_step4=findViewById(R.id.refer_textview_step4);
        textView_step5=findViewById(R.id.refer_textview_step5);


        new NoInternetDialog.Builder(this).build();




        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(Refer.this);
        final View view=getLayoutInflater().inflate(R.layout.refer_pay,null);
        mBuilder.setView(view);
        final AlertDialog pay_dialog=mBuilder.create();

        final TextView textView_amount=refer_pay.findViewById(R.id.refer_pay_textview_amount);
        Button button_pay=refer_pay.findViewById(R.id.refer_pay_button_pay);

        findViewById(R.id.refer_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Refer.this,More.class);
                startActivity(intent);
                finish();
            }
        });




        loading.show();
        mydb.collection("Users").document(phone).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful())
                    {
                        DocumentSnapshot documentSnapshot=task.getResult();
                        if (documentSnapshot.exists())
                        {
                            if (documentSnapshot.getData().get("refer_code").toString().trim().isEmpty())
                            {
                                textView_refer_code.setVisibility(View.GONE);
                                button_get_refer_code.setVisibility(View.VISIBLE);
                                loading.dismiss();
                            }
                            else {
                                button_get_refer_code.setVisibility(View.GONE);
                                textView_refer_code.setText(documentSnapshot.getData().get("refer_code").toString());
                                textView_refer_code.setVisibility(View.VISIBLE);
                                //
                                refer_code=documentSnapshot.getData().get("refer_code").toString();
                                //
                                loading.dismiss();
                            }
                        }
                    }
            }
        });

        mydb.collection("App_data").document("refer").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot=task.getResult();

                    if (documentSnapshot.exists())
                    {
                        textView_amount.setText(documentSnapshot.getData().get("buy_amount").toString());
                        to_pay_amount=Integer.parseInt(documentSnapshot.getData().get("buy_amount").toString());
                        gain_amount=Integer.parseInt(documentSnapshot.getData().get("gain_amount").toString());
                        share_link=documentSnapshot.getData().get("link").toString();

                        textView_message.setText(documentSnapshot.getData().get("message").toString());
                        textView_step1.setText(documentSnapshot.getData().get("step1").toString());
                        textView_step2.setText(documentSnapshot.getData().get("step2").toString());
                        textView_step3.setText(documentSnapshot.getData().get("step3").toString());
                        textView_step4.setText(documentSnapshot.getData().get("step4").toString());
                        textView_step5.setText(documentSnapshot.getData().get("step5").toString());
                    }
                }
            }
        });



        button_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refer_pay.hide();
                loading.show();
                mydb.collection("Users").document(phone).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful())
                        {
                            DocumentSnapshot documentSnapshot =task.getResult();
                            if (documentSnapshot.exists())
                            {
                                final int balance=Integer.parseInt(documentSnapshot.getData().get("Balance").toString());
                                final String referred_by=documentSnapshot.getData().get("referred_by").toString();
                                if (balance>=to_pay_amount)
                                {
                                    String name=documentSnapshot.getData().get("name").toString();
                                    String subname=name.toUpperCase().substring(0,3);
                                    Random r = new Random();
                                    int num1 = r.nextInt(9);
                                    int num2 = r.nextInt(9);
                                    int num3 = r.nextInt(9);
                                    int num4 = r.nextInt(9);
                                    final String code=subname+num1+num2+num3+num4;
                                    textView_refer_code.setText(code);
                                    textView_refer_code.setVisibility(View.VISIBLE);
                                    button_get_refer_code.setVisibility(View.GONE);
                                    mydb.collection("Users").document(phone).update("refer_code",code);

                                    final HashMap<String,Object> user=new HashMap<>();
                                    user.put("phone",phone);
                                    user.put("refer_code",code);
                                    mydb.collection("Refer_code").document(code).set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            final Transaction transaction=new Transaction();
                                            transaction.perform_transaction(phone,"Paid for Refer Code.",to_pay_amount,balance,"-");
                                           //
                                            refer_code=code;
                                            //
                                            if (!referred_by.isEmpty())
                                            {
                                                mydb.collection("Refer_code").document(referred_by).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                            if (task.isSuccessful())
                                                            {
                                                                final DocumentSnapshot doc=task.getResult();
                                                                if (doc.exists())
                                                                {
                                                                    final String referred_by_phone=doc.getData().get("phone").toString();

                                                                    mydb.collection("Users").document(referred_by_phone).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                                        @Override
                                                                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                                DocumentSnapshot document=task.getResult();
                                                                                if (document.exists())
                                                                                {
                                                                                    int referred_by_balance=Integer.parseInt(document.getData().get("Balance").toString());
                                                                                    transaction.perform_transaction(referred_by_phone,"Earn By Referrer",gain_amount,referred_by_balance,"+");
                                                                                    loading.dismiss();
                                                                                }
                                                                        }
                                                                    });
                                                                }
                                                            }
                                                    }
                                                });
                                            }
                                            else {
                                                loading.dismiss();
                                            }



                                        }
                                    });

                                }
                                else {
                                    Toast.makeText(Refer.this, "Insufficient Funds", Toast.LENGTH_SHORT).show();
                                    loading.dismiss();
                                    Intent intent=new Intent(Refer.this,Account.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }
                    }
                });

            }
        });

















        button_refer_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!textView_refer_code.getText().toString().isEmpty())
                {
                    loading.setCancelable(true);
                    Intent intent=new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    String Sharesubject="Combat Zone";
                    String Sharebody="Hey, Install Combat Zone and Register using my referral code - "+refer_code+" and get Rewards. Download the app at "+share_link;
                    intent.putExtra(Intent.EXTRA_SUBJECT,Sharesubject);
                    intent.putExtra(Intent.EXTRA_TEXT,Sharebody);
                    startActivity(Intent.createChooser(intent,"Share Using"));
                }
                else {
                    Toast.makeText(Refer.this, "You have not generated your code", Toast.LENGTH_LONG).show();
                }
            }
        });


        button_get_refer_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refer_pay.show();
            }
        });





    }
}