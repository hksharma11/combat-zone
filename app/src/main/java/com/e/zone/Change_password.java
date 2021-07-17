package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import am.appwise.components.ni.NoInternetDialog;

public class Change_password extends AppCompatActivity {

    EditText editText_current,editText_newpassword,editText_confirmpassword;
    Button button_save;
    Dialog loading;
    FirebaseFirestore mydb;

    String oldpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        loading=new Dialog(this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);
        mydb=FirebaseFirestore.getInstance();
        editText_current=findViewById(R.id.change_password_edittext_current);
        editText_newpassword=findViewById(R.id.change_password_edittext_newpassword);
        editText_confirmpassword=findViewById(R.id.change_password_edittext_confirm);
        button_save=findViewById(R.id.change_password_button_save);

        Shared_data shared_data=new Shared_data(this);
        final String phone=shared_data.getPhone();
        new NoInternetDialog.Builder(this).build();
        loading.show();
        mydb.collection("Users").document(phone).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
               if (task.isSuccessful())
               {
                   DocumentSnapshot documentSnapshot=task.getResult();
                   if (documentSnapshot.exists())
                   {
                       oldpassword=documentSnapshot.getData().get("password").toString();
                       loading.hide();

                   }
               }
            }
        });

        findViewById(R.id.change_password_view_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(Change_password.this,More.class);
                startActivity(intent);
                finish();
            }
        });


        button_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!editText_current.getText().toString().isEmpty()  &&  !editText_newpassword.getText().toString().isEmpty() && !editText_confirmpassword.getText().toString().isEmpty())
                {
                        if (editText_newpassword.getText().toString().equals(editText_confirmpassword.getText().toString()))
                        {
                                if (editText_current.getText().toString().equals(oldpassword))
                                {
                                    loading.show();
                                    mydb.collection("Users").document(phone).update("password",editText_newpassword.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(Change_password.this, "Password changed successfully", Toast.LENGTH_SHORT).show();

                                                loading.hide();
                                            Intent intent=new Intent(Change_password.this,More.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                    });
                                }
                                else {
                                    Toast.makeText(Change_password.this, "Wrong Old Password", Toast.LENGTH_SHORT).show();
                                }


                        }
                        else {
                            Toast.makeText(Change_password.this, "Confirm Password did not match.", Toast.LENGTH_SHORT).show();
                        }
                }else{

                    if (editText_current.getText().toString().isEmpty())
                    {
                        editText_current.setError("Empty Field");
                    }

                    if (editText_newpassword.getText().toString().isEmpty()){
                        editText_newpassword.setError("Empty Field");
                    }

                    if (editText_confirmpassword.getText().toString().isEmpty())
                    {
                        editText_confirmpassword.setError("Empty Field");
                    }



                }


            }
        });


    }
}