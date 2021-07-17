package com.e.zone;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.HashMap;

public class Transaction {

    FirebaseFirestore mydb=FirebaseFirestore.getInstance();

    public void  perform_transaction(final String user_id, final String message, final int amount, int balance, final String plusminus)
    {
        final long rightNow = Calendar.getInstance().getTimeInMillis();
        final String transaction_id=Long.toString(rightNow);
        final String date=Calendar.getInstance().getTime().toString();
        if (plusminus.equals("+"))
        {
            mydb.collection("Users").document(user_id).update("Balance",Integer.toString(balance+amount)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    final HashMap<String,Object> trans= new HashMap<>();
                    trans.put("transaction_id",rightNow);
                    trans.put("message",message);
                    trans.put("date",date);
                    trans.put("amount",amount);
                    trans.put("plusminus",plusminus);

                    mydb.collection("Users").document(user_id).collection("Transactions").document(transaction_id).set(trans).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });


                }
            });
        }
        if (plusminus.equals("-"))
        {
            mydb.collection("Users").document(user_id).update("Balance",Integer.toString(balance-amount)).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    final HashMap<String,Object> trans= new HashMap<>();
                    trans.put("transaction_id",rightNow);
                    trans.put("message",message);
                    trans.put("date",date);
                    trans.put("amount",amount);
                    trans.put("plusminus",plusminus);

                    mydb.collection("Users").document(user_id).collection("Transactions").document(transaction_id).set(trans).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                        }
                    });


                }
            });

        }


    }


}
