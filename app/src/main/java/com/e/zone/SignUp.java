package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

import am.appwise.components.ni.NoInternetDialog;

public class SignUp extends AppCompatActivity {

    EditText editText_name, editText_phone, editText_email,editText_password;
    TextView textView_register,textView_haveaccount;

    ConnectionDetector connectionDetector;
    FirebaseFirestore mydb;
    Dialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        textView_register= findViewById(R.id.register_textview_register);
        textView_haveaccount=findViewById(R.id.register_textview_haveaccount);
        editText_name=findViewById(R.id.register_edittext_name);
        editText_email=findViewById(R.id.register_edittext_email);
        editText_phone=findViewById(R.id.register_edittext_phone);
        editText_password=findViewById(R.id.register_edittext_password);
        loading=new Dialog(SignUp.this);
        loading.setContentView(R.layout.loadingbar);
        loading.setCancelable(false);


        connectionDetector =new ConnectionDetector(this);
        mydb=FirebaseFirestore.getInstance();



        textView_haveaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });




        new NoInternetDialog.Builder(this).build();

        textView_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(validRegistration())
                {
                    if (connectionDetector.isConnectingToInternet())
                    {
                        loading.show();
                        mydb.collection("Users").document(editText_phone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {

                                if (task.isSuccessful())
                                {
                                    DocumentSnapshot doc=task.getResult();

                                    if (!doc.exists())
                                    {
                                        final HashMap<String, Object> User = new HashMap<>();
                                        User.put("name",editText_name.getText().toString());

                                        User.put("phone",editText_phone.getText().toString());
                                        User.put("password",editText_password.getText().toString());
                                        User.put("Balance","0");
                                        User.put("refer_code","");
                                        User.put("referred_by",editText_email.getText().toString());


                                        String phone_with_code="+91"+editText_phone.getText().toString().trim();

                                        Intent intent=new Intent(SignUp.this, Otp.class);
                                        intent.putExtra("phone_with_code",phone_with_code);
                                        intent.putExtra("phone",editText_phone.getText().toString().trim());
                                        intent.putExtra("User_data",User);
                                        loading.dismiss();
                                        startActivity(intent);

                                    }
                                    else{
                                        loading.dismiss();
                                        Toast.makeText(SignUp.this, "User Already Registered", Toast.LENGTH_SHORT).show();
                                    }
                                }

                            }
                        });
                    }
                    else
                    {
                        Toast.makeText(SignUp.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(SignUp.this, "Check Entered Details", Toast.LENGTH_SHORT).show();
                }



            }
        });








    }


    boolean validRegistration(){
        boolean result=true;


        if(editText_name.getText().toString().isEmpty()){

            editText_name.setError("Empty Field");
            result=false;

        }
        else if(!editText_name.getText().toString().matches(("^[a-zA-Z\\s]*$"))){
            editText_name.setError("Invalid Name");
            result=false;
        }
        else {
            editText_name.setError(null);
        }



        if(editText_phone.getText().toString().isEmpty()){
            editText_phone.setError("Empty Field");
            result=false;
        }
        else if (editText_phone.getText().toString().length()<10){

            editText_phone.setError("Invalid Number");
            result=false;
        }
        else {
            editText_phone.setError(null);
        }



        if (editText_password.getText().toString().isEmpty()){
            editText_password.setError("Field Required");
            result=false;
        }
        else {
            editText_password.setError(null);
        }



        return result;
    }


}