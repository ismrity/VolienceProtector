package com.achs.voilence_protector;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    EditText urname, uremail, urphone;//variables for datatype EditText
    Button clk;
    //declare button

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activit_login);
        urname=(EditText) findViewById(R.id.name);
        uremail = (EditText) findViewById(R.id.email);//initialize
        urphone = (EditText) findViewById(R.id.phone);
        clk = (Button) findViewById(R.id.btn);
        getSupportActionBar().setTitle("Registration Form");//for name of topmost part of page

    }

    public void movepage(View v) {//movepage is onclick method for button
        String stname=urname.getText().toString();
        String stemail = uremail.getText().toString();//String(datatype) variable ,inside it store the value that user have tyed
        String stphone = urphone.getText().toString();
        String vphone = "^[+]?[0-9]{10,13}$";

        if(stname.isEmpty()){
            urname.setError("Field can't be empty");
        }

        if (stemail.isEmpty()) {
            uremail.setError("Field can't be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(stemail).matches()) {
            uremail.setError("Please enter a valid email address");
        }

         else if (stphone.isEmpty()) {
            urphone.setError("Field can't be empty");
        }

            else if((!stphone.matches(vphone))){
                urphone.setError("Invalid phone no ");

        }



            else {
            Intent in = new Intent(LoginActivity.this, DashboardActivity.class);
            startActivity(in);


        }


    }

}

