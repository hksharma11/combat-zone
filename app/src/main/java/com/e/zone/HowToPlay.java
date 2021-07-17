package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import am.appwise.components.ni.NoInternetDialog;

public class HowToPlay extends AppCompatActivity {
    FirebaseFirestore mydb;
    TextView textView1,textView2,textView3,textView4,textView5,textView6,textView7;
    ImageView imageView1,imageView2,imageView3,imageView4;
    Dialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.how_to_play);
        mydb=FirebaseFirestore.getInstance();
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);

        textView1=findViewById(R.id.howtoplay_textview1);
        textView2=findViewById(R.id.howtoplay_textview2);
        textView3=findViewById(R.id.howtoplay_textview3);
        textView4=findViewById(R.id.howtoplay_textview4);
        textView5=findViewById(R.id.howtoplay_textview5);
        textView6=findViewById(R.id.howtoplay_textview6);
        textView7=findViewById(R.id.howtoplay_textview7);

        imageView1=findViewById(R.id.howtoplay_imageview1);
        imageView2=findViewById(R.id.howtoplay_imageview2);
        imageView3=findViewById(R.id.howtoplay_imageview3);
        imageView4=findViewById(R.id.howtoplay_imageview4);
        loading.show();
        mydb.collection("App_data").document("howtoplay").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                DocumentSnapshot documentSnapshot=task.getResult();
                if (documentSnapshot.exists())
                {
                    if (documentSnapshot.getData().get("display").equals("1"))
                    {
                        textView1.setText(documentSnapshot.getData().get("text1").toString());
                        textView2.setText(documentSnapshot.getData().get("text2").toString());
                        textView3.setText(documentSnapshot.getData().get("text3").toString());
                        textView4.setText(documentSnapshot.getData().get("text4").toString());
                        textView5.setText(documentSnapshot.getData().get("text5").toString());
                        textView6.setText(documentSnapshot.getData().get("text6").toString());
                        textView7.setText(documentSnapshot.getData().get("text7").toString());

                        Picasso.get()
                                .load(documentSnapshot.getData().get("image1").toString())

                                .into(imageView1);
                        Picasso.get()
                                .load(documentSnapshot.getData().get("image2").toString())

                                .into(imageView2);
                        Picasso.get()
                                .load(documentSnapshot.getData().get("image3").toString())

                                .into(imageView3);
                        Picasso.get()
                                .load(documentSnapshot.getData().get("image4").toString())

                                .into(imageView4);

                        loading.dismiss();

                    }
                    else {
                        loading.dismiss();
                    }
                }
            }
        });
        new NoInternetDialog.Builder(this).build();

        findViewById(R.id.howtoplay_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(HowToPlay.this,More.class);
                startActivity(intent);
                finish();
            }
        });


    }
}