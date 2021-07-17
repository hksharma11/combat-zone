package com.e.zone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import am.appwise.components.ni.NoInternetDialog;

public class More extends AppCompatActivity {

    
    BottomNavigationView bottomNavigationView;

    Button button_howtoplay,button_notifcation,button_changepassword,button_contactus,button_refer,button_share,button_results;
    FirebaseFirestore mydb;
    String link="";
    Button rateUs;

    TextView username,balance;
    LinearLayout linearLayout_display_balance;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.more);

        button_howtoplay=findViewById(R.id.more_button_howtoplay);
        button_notifcation=findViewById(R.id.more_button_notification);
        button_changepassword=findViewById(R.id.more_button_changepassword);
        button_contactus=findViewById(R.id.more_button_contactus);
        button_refer=findViewById(R.id.more_button_refer);
        button_share=findViewById(R.id.more_button_share);
        mydb=FirebaseFirestore.getInstance();
        button_results=findViewById(R.id.more_button_results);
        rateUs=findViewById(R.id.more_button_rateus);
        linearLayout_display_balance=findViewById(R.id.more_display_balance);
        username=findViewById(R.id.more_textview_username);
        balance=findViewById(R.id.more_textview_balance);



        button_howtoplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this,HowToPlay.class);
                startActivity(intent);
            }
        });

        button_results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this,Results.class);
                startActivity(intent);
            }
        });

        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivity(new Intent(Intent.ACTION_VIEW,
                            Uri.parse("market://details?id=" + getPackageName())));
                }catch (ActivityNotFoundException e)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));
                }
            }
        });

        mydb.collection("App_data").document("share_link").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if (documentSnapshot.exists())
                    {
                        link=documentSnapshot.getData().get("link").toString();

                    }
                    else {


                    }
                }
                else {

                }
            }
        });

        mydb.collection("App_data").document("display").addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value.getData().get("more_balance").toString().equals("1"))
                {
                    linearLayout_display_balance.setVisibility(View.VISIBLE);
                }

                if (value.getData().get("more_balance").toString().equals("0"))
                {
                    linearLayout_display_balance.setVisibility(View.GONE);
                }

            }
        });




        Shared_data shared_data=new Shared_data(this);
        String phone=shared_data.getPhone();
        mydb.collection("Users").document(phone).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error!=null)
                {
                    Toast.makeText(More.this, error.toString(), Toast.LENGTH_SHORT).show();
                    return;
                }

                balance.setText(value.getData().get("Balance").toString());
                username.setText(value.getData().get("name").toString());



            }
        });



        button_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                String Sharesubject="Combat Zone";
                String Sharebody=link;
                intent.putExtra(Intent.EXTRA_SUBJECT,Sharesubject);
                intent.putExtra(Intent.EXTRA_TEXT,Sharebody);
                startActivity(Intent.createChooser(intent,"Share Using"));
            }
        });

        button_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this,Contact_us.class);
                startActivity(intent);
            }
        });





        bottomNavigationView=findViewById(R.id.more_bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){



                    case R.id.mycontest:
                        Intent intent2=new Intent(More.this, MyContest.class);
                        startActivity(intent2);

                        finish();

                        return true;



                    case R.id.home:
                        Intent intent3=new Intent(More.this , Home.class);
                        startActivity(intent3);
//                        CustomIntent.customType(Dashboard.this,"left-to-right");

                        finish();

                        return true;


                    case R.id.account:
                        Intent intent4=new Intent(More.this , Account.class);
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


        button_notifcation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this,Notification.class);
                startActivity(intent);
            }
        });

        button_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this,Change_password.class);
                startActivity(intent);
            }
        });

        button_refer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(More.this,Refer.class);
                startActivity(intent);
            }
        });










        new NoInternetDialog.Builder(this).build();

        findViewById(R.id.logout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Shared_data shared_data=new Shared_data(More.this);
                shared_data.removeUser();

                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(More.this,SignIn.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);

            }
        });
    }
}