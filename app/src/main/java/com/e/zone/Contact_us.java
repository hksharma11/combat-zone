package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import am.appwise.components.ni.NoInternetDialog;

public class Contact_us extends AppCompatActivity {

    TextView textView_phone,textView_whatsapp,textView_telegram,textView_instagram,textView_email;
    View view_phone,view_whatsapp,view_telegram,view_instagram,view_email;
    EditText editText_subject,editText_description;
    Button button_send;

    int flag=0;
    FirebaseFirestore mydb;

    String phone,whatsapp,telegram,instagram,email;
    Dialog loading;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contact_us);
        mydb=FirebaseFirestore.getInstance();
        textView_phone=findViewById(R.id.contact_us_textview_phone);
        textView_whatsapp=findViewById(R.id.contact_us_textview_whatsapp);
        textView_telegram=findViewById(R.id.contact_us_textview_telegram);
        textView_instagram=findViewById(R.id.contact_us_textview_instagram);
        textView_email=findViewById(R.id.contact_us_textview_email);

        view_phone=findViewById(R.id.contact_us_view_phone);
        view_whatsapp=findViewById(R.id.contact_us_view_whatsapp);
        view_telegram=findViewById(R.id.contact_us_view_telegram);
        view_instagram=findViewById(R.id.contact_us_view_instagram);
        view_email=findViewById(R.id.contact_us_view_email);

        editText_subject=findViewById(R.id.contact_us_edittext_subject);
        editText_description=findViewById(R.id.contact_us_edittext_description);

        button_send=findViewById(R.id.contact_us_button_send);
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        final Shared_data shared_data=new Shared_data(this);


        new NoInternetDialog.Builder(this).build();

        loading.show();
        mydb.collection("App_data").document("contact_us").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful())
                {
                    DocumentSnapshot documentSnapshot=task.getResult();
                    if (documentSnapshot.exists())
                    {
                        phone=documentSnapshot.getData().get("phone").toString();
                        whatsapp=documentSnapshot.getData().get("whatsapp").toString();
                        telegram=documentSnapshot.getData().get("telegram").toString();
                        email=documentSnapshot.getData().get("email").toString();
                        instagram=documentSnapshot.getData().get("instagram").toString();

                        textView_phone.setText(phone);
                        textView_whatsapp.setText(whatsapp);
                        textView_telegram.setText(telegram);
                        textView_email.setText(email);
                        textView_instagram.setText(instagram);



                        loading.hide();
                        flag=1;


                    }
                }
            }
        });

        findViewById(R.id.contact_us_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Contact_us.this,More.class);
                startActivity(intent);
                finish();
            }
        });




        view_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);

            }
        });


        view_whatsapp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri=Uri.parse("smsto:"+whatsapp);
                Intent intent=new Intent(Intent.ACTION_SENDTO,uri);
                intent.setPackage("com.whatsapp");
                startActivity(intent);
            }
        });

        view_telegram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(telegram));
                startActivity(browserIntent);
            }
        });


        view_instagram.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Uri uri = Uri.parse(instagram);
                Intent likeIng = new Intent(Intent.ACTION_VIEW, uri);

                likeIng.setPackage("com.instagram.android");

                try {
                    startActivity(likeIng);
                } catch (ActivityNotFoundException e) {
//                    startActivity(new Intent(Intent.ACTION_VIEW,
//                            Uri.parse("http://instagram.com/_combat_zone_")));
                }
            }
        });


        view_email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (Intent.ACTION_SEND);
                intent.setType("message/rfc822");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                intent.putExtra(Intent.EXTRA_SUBJECT, "");
                intent.setPackage("com.google.android.gm");
                if (intent.resolveActivity(getPackageManager())!=null)
                    startActivity(intent);
                else
                    Toast.makeText(Contact_us.this,"Gmail App is not installed",Toast.LENGTH_SHORT).show();
            }
        });


        button_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!editText_subject.getText().toString().isEmpty() && !editText_description.getText().toString().isEmpty())
                {
                    loading.show();
                    String myphone=shared_data.getPhone();
                    Date c = Calendar.getInstance().getTime();
                    String time=c.toString();
                    long rightNow = Calendar.getInstance().getTimeInMillis();
                    String documentid=Long.toString(rightNow);

                    final HashMap<String, Object> Query = new HashMap<>();
                    Query.put("subject",editText_subject.getText().toString());
                    Query.put("description",editText_description.getText().toString());
                    Query.put("user",myphone);
                    Query.put("time",time);

                    mydb.collection("Query").document(documentid).set(Query).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            editText_subject.setText("");
                            editText_description.setText("");
                            Toast.makeText(Contact_us.this, "Sent Successfully", Toast.LENGTH_SHORT).show();
                            loading.hide();
                        }
                    });



                }
                else {
                    if (editText_description.getText().toString().isEmpty())
                    {
                        editText_description.setError("Empty Field");
                    }
                    if (editText_subject.getText().toString().isEmpty())
                    {
                        editText_subject.setError("Empty Field");
                    }
                }
            }
        });










    }



    public static boolean isAppAvailable(Context context, String appName)
    {
        PackageManager pm = context.getPackageManager();
        try
        {
            pm.getPackageInfo(appName, PackageManager.GET_ACTIVITIES);
            return true;
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return false;
        }
    }





}