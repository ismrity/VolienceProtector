package com.achs.voilence_protector;

import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import android.telephony.SmsManager;
import android.location.LocationManager;
import android.location.Location;



import static com.achs.voilence_protector.R.*;
import static com.achs.voilence_protector.R.id.SOS;
import static com.achs.voilence_protector.R.id.e_contacts;
import static com.achs.voilence_protector.R.id.hospital;
import static com.achs.voilence_protector.R.id.laws;
import static com.achs.voilence_protector.R.id.police;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener {//creates onclick method-view.Onclicklistener specially use for multiple buttons
    private CardView Econtacts,Hospital,Police,Laws;
    ImageView image;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_login2);

        Econtacts = (CardView) findViewById(R.id.e_contacts);//initialize
        Hospital = (CardView) findViewById(R.id.hospital);//R is class which having all id's and views of xml
        Police = (CardView) findViewById(R.id.police);
        Laws = (CardView) findViewById(R.id.laws);
        image = (ImageView) findViewById(R.id.SOS);//this paste activity itself which is View.Onclicklistener in  class
        Econtacts.setOnClickListener(this);//for activating button
        Hospital.setOnClickListener(this);
        Police.setOnClickListener(this);
        Laws.setOnClickListener(this);
        image.setOnClickListener(this);

        //laws is variable and setonclick listener is calling it
        getSupportActionBar().setTitle("Safe Click");


    }


    @Override
    public void onClick(View view) {
        //onclick method for button,view is variable,this method handles click
        Intent i;

        switch (view.getId()){//in order to check which button is clicked,view is variable
            case e_contacts :i=new Intent(this, ContactAddActivity.class);startActivity(i);break;
            case hospital:i=new Intent(this,Hospital.class);startActivity(i);break;
            case police :i=new Intent(this,Police.class);startActivity(i);break;
            case laws :i=new Intent(this,Laws.class);startActivity(i);break;
            case SOS:
                /*i=new Intent(Intent.ACTION_SENDTO,Uri.parse("sms:9810127170"));
                i.putExtra("sms_body","Hi there");
                startActivity(i);
*/
                SendMessage("9860349960","Hi, this is test");

        }
        }
    public void SendMessage(String strMobileNo, String strMessage) {
        try {

           SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null);

            Toast.makeText(getApplicationContext(), "Your Message Sent",
                    Toast.LENGTH_LONG).show();
            Uri callUri = Uri.parse("tel:9851219509");
            Intent callIntent = new Intent(Intent.ACTION_CALL,callUri);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            startActivity(callIntent);

        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getMessage().toString(),
                    Toast.LENGTH_LONG).show();
        }
    }

    };








