package com.achs.voilence_protector.Activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import com.achs.voilence_protector.Activity.EmergencyContactActivity.EmergencyContactActivity;
import com.achs.voilence_protector.Adapter.ContactAdapter;
import com.achs.voilence_protector.DB.ContactDBHelper;
import com.achs.voilence_protector.util.Contact;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

import static com.achs.voilence_protector.R.id;
import static com.achs.voilence_protector.R.id.cv_emergency_contacts;
import static com.achs.voilence_protector.R.id.cv_hospital;
import static com.achs.voilence_protector.R.id.cv_laws;
import static com.achs.voilence_protector.R.id.cv_police;
import static com.achs.voilence_protector.R.layout;

public class DashboardActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener{//creates onclick method-view.Onclicklistener specially use for multiple buttons
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager mLocationManager;
    private LocationRequest mLocationRequest;
    SharedPreferences prefs;
    private LocationListener listener;
    private LatLng latLng;
    String locate;
    String latitude,longitude;

    private CardView cvEmergencyContact,cvHospital,cvPolice,cvLaws;
    ImageView imgSOS;
    ContactDBHelper contactDBHelper;
    ContactAdapter mAdapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    ArrayList<Contact> phoneList = new ArrayList<>();
    ArrayList<Contact> userList = new ArrayList<>();
    Integer sizeOfDB;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_dashboard);

        sharedPreferences = getSharedPreferences("location",0);

        contactDBHelper = new ContactDBHelper(this);
        loadFromDb();
        Log.d("size", String.valueOf(contactList.size()));
        sizeOfDB = contactList.size();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mLocationManager = (LocationManager)this.getSystemService(Context.LOCATION_SERVICE);

        checkLocation();

        cvEmergencyContact = findViewById(id.cv_emergency_contacts);//initialize
        cvEmergencyContact.setOnClickListener(this);//for activating button

        cvHospital = findViewById(id.cv_hospital);//R is class which having all id's and views of xml
        cvHospital.setOnClickListener(this);

        cvPolice = findViewById(id.cv_police);
        cvPolice.setOnClickListener(this);

        cvLaws = findViewById(id.cv_laws);
        cvLaws.setOnClickListener(this);


        imgSOS = findViewById(id.img_SOS);//this paste activity itself which is View.Onclicklistener in  class
        imgSOS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneList = contactDBHelper.getPhoneContact();
                userList = contactDBHelper.getUser();

                prefs = getSharedPreferences("REGISTRATION", MODE_PRIVATE);


                //concatination
                for (int i = 0; i <= phoneList.size(); i++){

                    makePhoneCall();
                    SendMessage(phoneList.get(i).getPhone(), "Hello, I am "+ prefs.getString("Name", "")
                            +". I am in danger right now. PLease help me. My location is "+ "https://maps.google.com/?q="+latitude+","+longitude);

                }

            }
        });

        prefs = getSharedPreferences("REGISTRATION", MODE_PRIVATE);
        String name = prefs.getString("Name", "");

        getSupportActionBar().setTitle("Safe Click - Hello "+ name);


    }

    @Override
    public void onClick(View view) {
        //onclick method for button,view is variable,this method handles click
        Intent i;

        switch (view.getId()){//in order to check which button is clicked,view is variable
            case cv_emergency_contacts:
                i=new Intent(this, EmergencyContactActivity.class);
                startActivity(i);
                break;
            case cv_hospital:
                i=new Intent(this, HospitalActivity.class);
                startActivity(i);
                break;
            case cv_police :
                i=new Intent(this, PoliceActivity.class);
                startActivity(i);
                break;
            case cv_laws :
                i=new Intent(this, LawsActivity.class);
                startActivity(i);
                break;
        }
        }
    public void SendMessage(String strMobileNo, String strMessage) {
        try {
                SmsManager smsManager = SmsManager.getDefault();
                smsManager.sendTextMessage(strMobileNo, null, strMessage, null, null);
            Toast.makeText(getApplicationContext(), "Your message has been sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void makePhoneCall(){
        try{
            Uri callUri = Uri.parse("tel:200");
            Intent callIntent = new Intent(Intent.ACTION_CALL,callUri);
            callIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_NO_USER_ACTION);
            startActivity(callIntent);

        }catch (Exception ex){
            Toast.makeText(getApplicationContext(), ex.getLocalizedMessage(),
                    Toast.LENGTH_LONG).show();
        }
    }

    private void loadFromDb() {
        try {
            contactList.clear();
            contactList = contactDBHelper.getAllContactString();
            mAdapter = new ContactAdapter(DashboardActivity.this, contactList);
        } catch (Exception e) {
            Log.d("Error==>Db ", e.getLocalizedMessage());
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        startLocationUpdates();

        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {

            // mLatitudeTextView.setText(String.valueOf(mLocation.getLatitude()));
            //mLongitudeTextView.setText(String.valueOf(mLocation.getLongitude()));
        } else {
            Toast.makeText(this, "Location not Detected", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {
//        Log.i(TAG, "Connection Suspended");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
//        Log.i(TAG, "Connection failed. Error: " + connectionResult.getErrorCode());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }
    @Override
    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//                .setFastestInterval(FASTEST_INTERVAL);mLocationRequest = LocationRequest.create()
//                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
//                .setInterval(UPDATE_INTERVAL)
//                .setFastestInterval(FASTEST_INTERVAL);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
        Log.d("reque", "--->>>>");
    }

    @Override
    public void onLocationChanged(Location location) {



        locate = "Lat: "+Double.toString(location.getLatitude()) + ", Long: " + Double.toString(location.getLongitude());

        latitude = String.valueOf(location.getLatitude());
        longitude = String.valueOf(location.getLongitude());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("latitude", String.valueOf(location.getLatitude()));
        editor.putString("longitude", String.valueOf(location.getLongitude()));

        editor.apply();


        Log.d("location", locate);
//        mLatitudeTextView.setText(String.valueOf(location.getLatitude()));
//        mLongitudeTextView.setText(String.valueOf(location.getLongitude() ));
//        Toast.makeText(this, locate, Toast.LENGTH_SHORT).show();
        // You can now create a LatLng Object for use with maps
        latLng = new LatLng(location.getLatitude(), location.getLongitude());
    }

    private boolean checkLocation() {
        if(!isLocationEnabled())
            showAlert();
        return isLocationEnabled();
    }

    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("Enable Location")
                .setMessage("Your Locations Settings is set to 'Off'.\nPlease Enable Location to " +
                        "use this app")
                .setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                    }
                });
        dialog.show();
    }

    private boolean isLocationEnabled() {
        mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                mLocationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}








