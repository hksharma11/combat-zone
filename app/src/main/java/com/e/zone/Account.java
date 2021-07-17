package com.e.zone;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.e.zone.Model.Trans;
import com.e.zone.MyAdapter.TransAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import am.appwise.components.ni.NoInternetDialog;

public class Account extends AppCompatActivity {

    FirebaseFirestore mydb;
    TextView textView_balance;

    BottomSheetDialog addmoney_sheet,withdraww_sheet;

    Button dialog_button_addmoney;
    EditText dialog_edittext_amount;
    EditText withdraw_dialog_edittext_amount;
    EditText withdraw_dialog_edittext_paytm_number;
    Button withdraw_dialog_button_withdraw;

    RelativeLayout relativeLayout_addmoney,relativeLayout_withdtaw;
    String OrderId,CustomerId,Price;

    int balance;
    int random;
    Random r;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Trans> recycler_data;

    BottomNavigationView bottomNavigationView;

    Dialog loading;
    String paytm_url,paytm_mid;

    CheckBox checkBox;

    int int_min_withdraw_amount;

    RelativeLayout relativeLayout_showbalance;
    LinearLayout linearLayout_addbuttons;
    TextView display_transaction;

    private InterstitialAd mInterstitialAd;
    TextView term1,term2,term3,term4,term5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-7712739497173319/9255271474");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        textView_balance=findViewById(R.id.account_textview_balance);
        relativeLayout_addmoney=findViewById(R.id.account_relativelayout_addmoney);
        relativeLayout_withdtaw=findViewById(R.id.account_relativelayout_withdraw);
        relativeLayout_showbalance=findViewById(R.id.account_showbalance);
        linearLayout_addbuttons=findViewById(R.id.account_button);
        display_transaction=findViewById(R.id.account_transaction);


        recyclerView=findViewById(R.id.transaction_recycler);
        recycler_data=new ArrayList<>();
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);
        mydb=FirebaseFirestore.getInstance();
        Shared_data shared_data=new Shared_data(this);
        final String phone=shared_data.getPhone();
       addmoney_sheet=new BottomSheetDialog(this);
       withdraww_sheet=new BottomSheetDialog(this);


        final AlertDialog.Builder builder=new AlertDialog.Builder(Account.this);
        final View termview=getLayoutInflater().inflate(R.layout.termandconditions,null);
        builder.setView(termview);
        final AlertDialog termandconditions_dialog=builder.create();

         term1=termview.findViewById(R.id.termandconditions_dialog_term1);
         term2=termview.findViewById(R.id.termandconditions_dialog_term2);
         term3=termview.findViewById(R.id.termandconditions_dialog_term3);
         term4=termview.findViewById(R.id.termandconditions_dialog_term4);
         term5=termview.findViewById(R.id.termandconditions_dialog_term5);



        loading.show();
       mydb.collection("App_data").document("paytm").addSnapshotListener(new EventListener<DocumentSnapshot>() {
           @Override
           public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               if (error!=null)
               {
                   Toast.makeText(Account.this, error.toString(), Toast.LENGTH_SHORT).show();
                   return;
               }

               assert value != null;
               paytm_url=value.getData().get("url").toString();
               paytm_mid=value.getData().get("mid").toString();
               int_min_withdraw_amount=Integer.parseInt(value.getData().get("min_withdraw").toString());

               if (value.getData().get("display").toString().equals("1"))
               {
                   relativeLayout_showbalance.setVisibility(View.VISIBLE);
                   linearLayout_addbuttons.setVisibility(View.VISIBLE);
                   display_transaction.setVisibility(View.VISIBLE);
               }
               if (value.getData().get("display").toString().equals("0"))
               {
                   relativeLayout_showbalance.setVisibility(View.GONE);
                   linearLayout_addbuttons.setVisibility(View.GONE);
                   display_transaction.setVisibility(View.GONE);
               }


               term1.setText(value.getData().get("term1").toString());
               term2.setText(value.getData().get("term2").toString());
               term3.setText(value.getData().get("term3").toString());
               term4.setText(value.getData().get("term4").toString());
               term5.setText(value.getData().get("term5").toString());



               loading.dismiss();



           }
       });

        new NoInternetDialog.Builder(this).build();


        bottomNavigationView=findViewById(R.id.account_bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {



                switch (menuItem.getItemId()){



                    case R.id.mycontest:
                        Intent intent2=new Intent(Account.this, MyContest.class);
                        startActivity(intent2);

                        finish();

                        return true;



                    case R.id.more:
                        Intent intent3=new Intent(Account.this , More.class);
                        startActivity(intent3);


                        finish();

                        return true;


                    case R.id.home:
                        Intent intent4=new Intent(Account.this , Home.class);
                        startActivity(intent4);

                        finish();

                        return true;






                    default:
                        return false;





                }
            }


        });
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       
       



        final AlertDialog.Builder mBuilder=new AlertDialog.Builder(Account.this);
        final View view=getLayoutInflater().inflate(R.layout.addmoney_dialog,null);
        mBuilder.setView(view);
        addmoney_sheet.setContentView(view);
       // final AlertDialog addmoney_dialog=mBuilder.create();


        final AlertDialog.Builder mBuilder2=new AlertDialog.Builder(Account.this);
        final View withdraw_view=getLayoutInflater().inflate(R.layout.withdrawmoney_dialog,null);
        mBuilder2.setView(withdraw_view);
        withdraww_sheet.setContentView(withdraw_view);


        final Dialog message =new Dialog(Account.this);
        message.setContentView(R.layout.withdraw_message);
        message.setCancelable(true);


        withdraw_dialog_button_withdraw=withdraw_view.findViewById(R.id.withdrawmoney_dialog_button_withdraw);
        withdraw_dialog_edittext_amount=withdraw_view.findViewById(R.id.withdrawmoney_dialog_edittext_amount);
        withdraw_dialog_edittext_paytm_number=withdraw_view.findViewById(R.id.withdrawmoney_dialog_edittext_paytm_number);
        checkBox=withdraww_sheet.findViewById(R.id.account_checkbox);

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    checkBox.setError(null);
                }
            }
        });

        withdraw_dialog_button_withdraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!withdraw_dialog_edittext_amount.getText().toString().isEmpty() && checkBox.isChecked() && !withdraw_dialog_edittext_paytm_number.getText().toString().isEmpty() && !(withdraw_dialog_edittext_paytm_number.getText().toString().length() <10))
                {
                        loading.show();
                        int amount=Integer.parseInt(withdraw_dialog_edittext_amount.getText().toString());
                        if (balance>=amount)
                        {
                            if (amount>=int_min_withdraw_amount)
                            {
                                Transaction transaction=new Transaction();
                                transaction.perform_transaction(phone,"Withdrawn from wallet",amount,balance,"-");
                                final Map<String,Object> withdrawdata = new HashMap<>();
                                withdrawdata.put("amount",withdraw_dialog_edittext_amount.getText().toString());
                                withdrawdata.put("paytm",withdraw_dialog_edittext_paytm_number.getText().toString());
                                withdrawdata.put("paid","no");
                                withdrawdata.put("requested_by",phone);
                                Date currentTime = Calendar.getInstance().getTime();
                                final String time=currentTime.toString();

                                withdrawdata.put("time",time);
                                Long rightnow=Calendar.getInstance().getTimeInMillis();
                                withdrawdata.put("id",rightnow);

                                mydb.collection("Withdraws").document(Long.toString(rightnow)).set(withdrawdata).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(Account.this, "Withdraw Request sent.", Toast.LENGTH_SHORT).show();
                                        loading.dismiss();
                                        withdraww_sheet.dismiss();
                                        message.show();
                                        if (mInterstitialAd.isLoaded()) {
                                            mInterstitialAd.show();
                                        }


                                    }
                                });



                            }
                            else {
                                loading.dismiss();
                                Toast.makeText(Account.this, "Minimum withdraw is "+int_min_withdraw_amount, Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                        {
                                loading.dismiss();
                            Toast.makeText(Account.this, "Insufficient funds", Toast.LENGTH_SHORT).show();
                        }
                }
                else{
                    if (withdraw_dialog_edittext_amount.getText().toString().isEmpty())
                    {
                        withdraw_dialog_edittext_amount.setError("Empty Field");
                    }

                    if (!checkBox.isChecked())
                    {
                        checkBox.setError("Accept Terms and Conditions");
                    }

                    if (withdraw_dialog_edittext_paytm_number.getText().toString().isEmpty())
                    {
                        withdraw_dialog_edittext_paytm_number.setError("Empty Field");
                    }

                    if (withdraw_dialog_edittext_paytm_number.getText().toString().length() <10 && withdraw_dialog_edittext_paytm_number.getText().toString().length()>0)
                    {
                        withdraw_dialog_edittext_paytm_number.setError("Invalid Number");
                    }
                }
            }
        });





       dialog_button_addmoney=view.findViewById(R.id.addmoney_dialog_button_addmoney);
       dialog_edittext_amount=view.findViewById(R.id.addmoney_dialog_edittext_amount);
       r=new Random();


        CustomerId=shared_data.getPhone();









        relativeLayout_withdtaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdraww_sheet.show();
            }
        });

        addmoney_sheet.findViewById(R.id.addmoney_dialog_view_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmoney_sheet.dismiss();

            }
        });

        withdraww_sheet.findViewById(R.id.withdrawmoney_dialog_view_close).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                withdraww_sheet.dismiss();
            }
        });
        withdraww_sheet.findViewById(R.id.withdrawmoney_dialog_textview_term).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                termandconditions_dialog.show();
            }
        });









        mydb.collection("Users").document(phone).addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                    if (documentSnapshot.exists())
                    {
                        textView_balance.setText(documentSnapshot.getData().get("Balance").toString());
                        balance=Integer.valueOf(documentSnapshot.getData().get("Balance").toString());
                    }
                    else{
                        Toast.makeText(Account.this, e.toString(), Toast.LENGTH_SHORT).show();
                    }
            }
        });




        relativeLayout_addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addmoney_sheet.show();
            }
        });



//        dialog_button_addmoney.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(Account.this, "Clicked", Toast.LENGTH_SHORT).show();
//            }
//        });


        dialog_button_addmoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!dialog_edittext_amount.getText().toString().isEmpty())
                {
                    loading.show();


                    final String Mid=paytm_mid;
                    String url=paytm_url;

                    final String callBackUrl="https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";



                    random = r.nextInt(1000000000-0+1)+0;





                    Date currentTime = Calendar.getInstance().getTime();
                    String time=currentTime.toString();

                    OrderId=Integer.toString(random);

                    Price= dialog_edittext_amount.getText().toString();

                    RequestQueue requestQueue= Volley.newRequestQueue(Account.this);
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {


                            try {
                                JSONObject jsonObject=new JSONObject(response);
                                if (jsonObject.has("CHECKSUMHASH")){
                                    String CHECKSUMHASH=jsonObject.getString("CHECKSUMHASH");

                                    PaytmPGService paytmPGService=PaytmPGService.getProductionService();


                                    HashMap<String,String> paramMap=new HashMap<String, String>();
                                    paramMap.put("MID",Mid);
                                    paramMap.put("ORDER_ID",OrderId);
                                    paramMap.put("CUST_ID",CustomerId);
                                    paramMap.put("CHANNEL_ID","WAP");
                                    paramMap.put("TXN_AMOUNT",Price);
                                    paramMap.put("WEBSITE","DEFAULT");
                                    paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                                    paramMap.put("CALLBACK_URL",callBackUrl);
                                    paramMap.put("CHECKSUMHASH",CHECKSUMHASH);


                                    PaytmOrder paytmOrder=new PaytmOrder(paramMap);
                                    paytmPGService.initialize(paytmOrder,null);
                                    paytmPGService.startPaymentTransaction(Account.this, true, true, new PaytmPaymentTransactionCallback() {
                                        @Override
                                        public void onTransactionResponse(Bundle inResponse) {
                                            String response=inResponse.getString("STATUS");

                                            if (response.equals("TXN_SUCCESS"))
                                            {
                                                int amount=Integer.valueOf(Price);
                                                Transaction transaction=new Transaction();
                                                transaction.perform_transaction(phone,"Added to Wallet",amount,balance,"+");
                                                addmoney_sheet.hide();
                                                loading.dismiss();
                                                Toast.makeText(Account.this, "Added Successfully", Toast.LENGTH_SHORT).show();
                                                if (mInterstitialAd.isLoaded()) {
                                                    mInterstitialAd.show();
                                                }




                                            }
                                            else {
                                                loading.dismiss();
                                                Toast.makeText(Account.this, "Payment Failed", Toast.LENGTH_SHORT).show();
                                            }


                                        }

                                        @Override
                                        public void networkNotAvailable() {
                                                loading.dismiss();
                                            Toast.makeText(Account.this, "Network Not Available", Toast.LENGTH_LONG).show();

                                        }

                                        @Override
                                        public void clientAuthenticationFailed(String inErrorMessage) {
                                            loading.dismiss();
                                            Toast.makeText(Account.this, inErrorMessage, Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void someUIErrorOccurred(String inErrorMessage) {
                                            loading.dismiss();
                                            Toast.makeText(Account.this, inErrorMessage, Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onErrorLoadingWebPage(int iniErrorCode, String inErrorMessage, String inFailingUrl) {

                                            loading.dismiss();
                                            Toast.makeText(Account.this, inErrorMessage, Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onBackPressedCancelTransaction() {


                                            loading.dismiss();
                                            Toast.makeText(Account.this, "Transaction Cancel", Toast.LENGTH_SHORT).show();

                                        }

                                        @Override
                                        public void onTransactionCancel(String inErrorMessage, Bundle inResponse) {

                                            loading.dismiss();
                                            Toast.makeText(Account.this, inErrorMessage, Toast.LENGTH_SHORT).show();

                                        }
                                    });
                                }
                            }
                            catch (JSONException e)
                            {
                                loading.dismiss();
                                Toast.makeText(Account.this, e.toString(), Toast.LENGTH_SHORT).show();
                            }



                        }
                    },new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error)
                        {
                            loading.dismiss();
                            Log.d("#55555",error.toString());
                            // Toast.makeText(Account.this, "something went wrong.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(Account.this, error.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> paramMap=new HashMap<String, String>();
                            paramMap.put("MID",Mid);
                            paramMap.put("ORDER_ID",OrderId);
                            paramMap.put("CUST_ID",CustomerId);
                            paramMap.put("CHANNEL_ID","WAP");
                            paramMap.put("TXN_AMOUNT",Price);
                            paramMap.put("WEBSITE","DEFAULT");
                            paramMap.put("INDUSTRY_TYPE_ID", "Retail");
                            paramMap.put("CALLBACK_URL",callBackUrl);

                            return paramMap;
                        }
                    };

                    requestQueue.add(stringRequest);

                }
                else {
                    dialog_edittext_amount.setError("Empty Field");
                }




//                final String Mid="zCYxxf19197800557954";
//                String url="https://combatzone.in/paytm/generateChecksum.php";




                //Intent intent=new Intent(Account.this, checksum.class);

                //intent.putExtra("custid",CustomerId);
                // intent.putExtra("orderid",OrderId);

                //intent.putExtra("pCash",Price);

                //startActivity(intent);





            }
        });


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {

                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });


//        mydb.collection("Users").document(phone).collection("Transactions").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//            @RequiresApi(api = Build.VERSION_CODES.N)
//            @Override
//            public void onComplete(@NonNull Task<QuerySnapshot> task) {
//
//
//                    for(QueryDocumentSnapshot doc:task.getResult()){
//
//
//
//                        final Trans data=new Trans();
//
//
//                        data.setMessage(doc.getData().get("message").toString());
//
//
//
//
//                        recycler_data.add(data);
//
//                    }
//
//
//
//                    recyclerView.setHasFixedSize(true);
//                    layoutManager=new LinearLayoutManager(Account.this);
//                    recyclerView.setLayoutManager(layoutManager);
//
//                    TransAdapter myAdapter=new TransAdapter(Account.this,recycler_data);
//                    recyclerView.setAdapter(myAdapter);
//
//
//
//
//                }
//        });


        mydb.collection("Users").document(phone).collection("Transactions").orderBy("transaction_id", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                recycler_data.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {

                    final Trans data=new Trans();




                    data.setMessage(doc.getData().get("message").toString());
                    data.setAmount(doc.getData().get("amount").toString());
                    data.setDate(doc.getData().get("date").toString());
                    data.setPlusminus(doc.getData().get("plusminus").toString());














                    recycler_data.add(data);



                }


                recyclerView.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(Account.this);
                recyclerView.setLayoutManager(layoutManager);

                    TransAdapter myAdapter=new TransAdapter(Account.this,recycler_data);
                    recyclerView.setAdapter(myAdapter);

            }
        });






    }
}