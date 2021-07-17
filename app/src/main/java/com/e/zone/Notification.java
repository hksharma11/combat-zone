package com.e.zone;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.e.zone.Model.Trans;
import com.e.zone.MyAdapter.NotificationAdapter;
import com.e.zone.MyAdapter.TransAdapter;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import am.appwise.components.ni.NoInternetDialog;

public class Notification extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<com.e.zone.Model.Notification> recycler_data;
    FirebaseFirestore mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);
        mydb=FirebaseFirestore.getInstance();
        recyclerView=findViewById(R.id.notification_recycler);
        recycler_data=new ArrayList<>();




        Shared_data shared_data=new Shared_data(this);
        String phone=shared_data.getPhone();

        findViewById(R.id.notification_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Notification.this,More.class);
                startActivity(intent);
                finish();
            }
        });

        new NoInternetDialog.Builder(this).build();
        mydb.collection("Users").document(phone).collection("Notifications").orderBy("id", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@javax.annotation.Nullable QuerySnapshot queryDocumentSnapshots, @javax.annotation.Nullable FirebaseFirestoreException e) {

                recycler_data.clear();
                for (QueryDocumentSnapshot doc: queryDocumentSnapshots)
                {

                    final com.e.zone.Model.Notification data=new com.e.zone.Model.Notification();




                    data.setMessage(doc.getData().get("message").toString());

                    data.setDate(doc.getData().get("date").toString());















                    recycler_data.add(data);



                }


                recyclerView.setHasFixedSize(true);
                layoutManager=new LinearLayoutManager(Notification.this);
                recyclerView.setLayoutManager(layoutManager);

                NotificationAdapter myAdapter=new NotificationAdapter(Notification.this,recycler_data);
                recyclerView.setAdapter(myAdapter);

            }
        });
    }
}