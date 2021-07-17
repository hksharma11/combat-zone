package com.e.zone;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import am.appwise.components.ni.NoInternetDialog;

public class SignIn extends AppCompatActivity {

    EditText phone,password;
    Button signin;
    TextView signup;
    FirebaseFirestore mydb;

    Dialog loadingBar;
    TextView forgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        phone=findViewById(R.id.signin_edittext_phone);
        password=findViewById(R.id.signin_edittext_password);
        signin=findViewById(R.id.signin_button_signin);
        signup=findViewById(R.id.signin_textview_signup);
        mydb=FirebaseFirestore.getInstance();
        loadingBar=new Dialog(SignIn.this);
        loadingBar.setContentView(R.layout.loadingbar);
        loadingBar.setCancelable(false);
        forgotPassword=findViewById(R.id.sign_up_textview_forgot_password);
        new NoInternetDialog.Builder(this).build();
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, SignUp.class);
                startActivity(intent);
            }
        });


        Shared_data shared_data=new Shared_data(SignIn.this);
        if(shared_data.getPhone()!=""){
            Intent intent= new Intent(SignIn.this,Home.class);
            startActivity(intent);
            finish();
        }

        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(SignIn.this,ForgotPassword.class);
                startActivity(intent);
            }
        });


        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isValidlogin())
                {
                    loadingBar.show();

                    mydb.collection("Users").document(phone.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful())
                            {
                                DocumentSnapshot documentSnapshot=task.getResult();
                                if (documentSnapshot.exists()){
                                    
                                    String pass=documentSnapshot.getData().get("password").toString();
                                    if (pass.equals(password.getText().toString()))
                                    {
                                        Shared_data shared_data=new Shared_data(SignIn.this);
                                        shared_data.setPhone(phone.getText().toString());

                                        Intent intent=new Intent(SignIn.this, Home.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        loadingBar.hide();
                                        Toast.makeText(SignIn.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else {
                                    loadingBar.hide();
                                    Toast.makeText(SignIn.this, "User Not Registered", Toast.LENGTH_SHORT).show();
                                }
                            }
                            else {
                                Toast.makeText(SignIn.this, "Something went wrong!\nTry Again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });







    }
    boolean isValidlogin(){
        boolean result=true;
        if(phone.getText().toString().isEmpty()){
            phone.setError("Empty Field");
            result=false;
        }
        else if (phone.getText().toString().length()<10){

            phone.setError("Invalid Number");
            result=false;
        }
        else {
            phone.setError(null);
        }

        if (password.getText().toString().isEmpty()){
            password.setError("Field Required");
            result=false;
        }
        else {
            password.setError(null);
        }

        return result;
    }
}