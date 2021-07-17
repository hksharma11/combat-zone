package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import am.appwise.components.ni.NoInternetDialog;

public class Splash extends AppCompatActivity {
    FirebaseFirestore mydb;
    int verCode;

    String playstore="";
    String alternamte_link="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);

        mydb=FirebaseFirestore.getInstance();


        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(Splash.this);
        final View view=getLayoutInflater().inflate(R.layout.update,null);
        mBuilder.setView(view);
        final AlertDialog update=mBuilder.create();

        Button update_now=view.findViewById(R.id.update_button_update_now);


        final AlertDialog.Builder builder=new AlertDialog.Builder(Splash.this);
        final View view2=getLayoutInflater().inflate(R.layout.maintenance_dialog,null);
        mBuilder.setView(view2);
        final AlertDialog maintain=mBuilder.create();
        new NoInternetDialog.Builder(this).build();


        try {
            PackageInfo pInfo = this.getPackageManager().getPackageInfo(getPackageName(), 0);
            //String version = pInfo.versionName;
            verCode= pInfo.versionCode;
            // Log.d("#7007344814",Integer.toString(verCode));
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            //Log.d("#7007344814",e.toString());
        }




        mydb.collection("App_data").document("splash").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot=task.getResult();

                    if (documentSnapshot.exists())
                    {
                            if (!documentSnapshot.getData().get("version").toString().matches(Integer.toString(verCode)))
                            {
                                playstore=documentSnapshot.getData().get("play_store").toString();
                                alternamte_link=documentSnapshot.getData().get("alternate_link").toString();
                                update.setCancelable(false);
                                update.show();
                            }
                            else if(documentSnapshot.getData().get("maintain").toString().matches("1"))
                            {
                                maintain.setCancelable(false);
                                maintain.show();
                            }
                            else{
                                Intent intent=new Intent(Splash.this,SignIn.class);
                                startActivity(intent);
                                finish();
                            }

                    }
                }
            }
        });






        update_now.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(playstore));
                    startActivity(browserIntent);
                }catch (Exception e)
                {
                    Intent goToMarket = new Intent(Intent.ACTION_VIEW)
                            .setData(Uri.parse(alternamte_link));
                    startActivity(goToMarket);
                }





            }
        });



    }
}