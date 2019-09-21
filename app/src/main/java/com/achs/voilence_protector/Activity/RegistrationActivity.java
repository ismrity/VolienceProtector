package com.achs.voilence_protector.Activity;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.achs.voilence_protector.R;

public class RegistrationActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    EditText edNameResister, edEmail, edPhone;//variables for datatype EditText
    Button btnRegister;
    SharedPreferences prefs;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_registration);


        prefs = getSharedPreferences("REGISTRATION", MODE_PRIVATE);
        if(prefs.getString("Name", "").length()>1){
            Intent in = new Intent(RegistrationActivity.this, DashboardActivity.class);
            startActivity(in);
        }




        edNameResister= findViewById(R.id.ed_name_register);
        edEmail = findViewById(R.id.ed_email);//initialize
        edPhone = findViewById(R.id.ed_phone);
        btnRegister = findViewById(R.id.btn_register);
        getSupportActionBar().setTitle("Registration Form");//for name of topmost part of page

        sharedPreferences = getApplicationContext().getSharedPreferences("Reg", 0);
        editor = sharedPreferences.edit();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edNameResister.getText().toString();
                String email = edEmail.getText().toString();
                String phone = edPhone.getText().toString();
                String vphone = "^[+]?[0-9]{10,13}$";

                if(edNameResister.getText().length() <=0){
                    edNameResister.setError("Field can't be empty");
                }
                else if( edEmail.getText().length()<=0) {
                    if (email.isEmpty()) {
                        edEmail.setError("Field can't be empty");
                    } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                        edEmail.setError("Please enter a valid email address");
                    }

                }
                else if( edPhone.getText().length()<=0) {
                    if (email.isEmpty()) {
                        edPhone.setError("Field can't be empty");
                    } else if ((!phone.matches(vphone))) {
                        edPhone.setError("Invalid phone no ");
                    }

                }
                else {
                    editor = getSharedPreferences("REGISTRATION", MODE_PRIVATE).edit();
                    editor.putString("Name", name);
                    editor.putString("Email", email);
                    editor.putString("Phone", phone);
                    editor.apply();



                            // after saving the value open next activity
                            Intent in = new Intent(RegistrationActivity.this, DashboardActivity.class);
                            startActivity(in);

                        }


                    }
        });


    }

}

