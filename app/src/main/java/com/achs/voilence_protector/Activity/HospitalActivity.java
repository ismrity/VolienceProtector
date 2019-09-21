package com.achs.voilence_protector.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.achs.voilence_protector.Adapter.HospitalAdapter;
import com.achs.voilence_protector.R;
import com.achs.voilence_protector.util.Hospital;
import com.achs.voilence_protector.util.HospitalDistance;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HospitalActivity extends AppCompatActivity implements LocationListener {
    private RecyclerView recyclerView;
    //    List<Address> hospitalList;
    Hospital hospitalData;
    HospitalAdapter mAdapter;
    Geocoder geocoder;
    String bestProvider;
    public double finalDistance = 0;
    ArrayList<HospitalDistance> hospitalDistancesArrayList = new ArrayList<>();
    Location location;

    private ArrayList<Hospital.Data> hospitalList;
    private double myLat, myLong;
    private Location loc1, loc2;
    ArrayList<Float> distancess = new ArrayList<>();
    Location locationC;

    protected LocationManager locationManager;
    protected LocationListener locationListener;

    private float distanceInKm;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hospital);
        recyclerView = findViewById(R.id.recycler_view_hospital);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        sharedPreferences = getSharedPreferences("location", 0);

        myLat = Double.parseDouble(sharedPreferences.getString("latitude", ""));
        myLong = Double.parseDouble(sharedPreferences.getString("longitude", ""));


        hospitalData = new Gson().fromJson(loadJSONFromAsset(), new TypeToken<Hospital>() {
        }.getType());
        getLocation();

    }
    private void getLocation() {
        loc1 = new Location("");

        for (int i = 0; i < hospitalData.getData().size(); i++) {

            loc1.setLatitude(Double.parseDouble(hospitalData.getData().get(i).getLatitude()));
            loc1.setLongitude(Double.parseDouble(hospitalData.getData().get(i).getLongitude()));

            loc2 = new Location("");

            loc2.setLatitude(myLat);
            loc2.setLongitude(myLong);
            Log.e("ASdasd2", String.valueOf(myLat));
            Log.e("ASdasd3", String.valueOf(myLong));

//            distanceInMeters = loc1.distanceTo(loc2);     //in metere
//            distanceInMiles = loc1.distanceTo(loc2)/1609.344;     //in miles
            String dist = null;
            distanceInKm = loc1.distanceTo(loc2) / 1000;      //in kilometers
            dist = getDistance(
                    myLat, myLong,
                    Double.parseDouble(hospitalData.getData().get(i).getLatitude()),
                    Double.parseDouble(hospitalData.getData().get(i).getLongitude()));

            HospitalDistance hospitalDistance = new HospitalDistance();

            hospitalDistance.setDistance(finalDistance);
            hospitalDistance.setName(hospitalData.getData().get(i).getHospital());
            hospitalDistance.setPhone(hospitalData.getData().get(i).getPhone());
            hospitalDistancesArrayList.add(i, hospitalDistance);
            Collections.sort(hospitalDistancesArrayList, new Comparator<HospitalDistance>() {
                @Override
                public int compare(HospitalDistance hospitalDistance, HospitalDistance t1) {
                    return hospitalDistance.getDistance().compareTo(t1.getDistance());                }
            });

            distancess.add(distanceInKm);


        }
        Collections.sort(distancess);
        Log.d("hospitaladapter", new Gson().toJson(distancess));


        mAdapter = new HospitalAdapter(hospitalDistancesArrayList, this);
//        getLatAndLong();
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onLocationChanged(Location location) {
        myLat = location.getLatitude();
        myLong = location.getLongitude();
    }

    @Override
    public void onProviderDisabled(String provider) {
        Log.d("Latitude","disable");
    }

    @Override
    public void onProviderEnabled(String provider) {
        Log.d("Latitude","enable");
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        Log.d("Latitude","status");
    }

    private double meterDistanceBetweenPoints(float lat_source, float lng_source, float lat_destination, float lng_destination) {
        float pk = (float) (180.f / Math.PI);
        float a1 = lat_source / pk;
        float a2 = lng_source / pk;
        float b1 = lat_destination / pk;
        float b2 = lng_destination / pk;

        double t1 = Math.cos(a1) * Math.cos(a2) * Math.cos(b1) * Math.cos(b2);
        double t2 = Math.cos(a1) * Math.sin(a2) * Math.cos(b1) * Math.sin(b2);
        double t3 = Math.sin(a1) * Math.sin(b1);
        double tt = Math.acos(t1 + t2 + t3);

        return 6366000 * tt;
    }


    public String getDistance(double lat_a, double lng_a, double lat_b, double lng_b) {
        // earth radius is in mile
        double earthRadius = 3958.75;
        double latDiff = Math.toRadians(lat_b - lat_a);
        double lngDiff = Math.toRadians(lng_b - lng_a);
        double a = Math.sin(latDiff / 2) * Math.sin(latDiff / 2)
                + Math.cos(Math.toRadians(lat_a))
                * Math.cos(Math.toRadians(lat_b)) * Math.sin(lngDiff / 2)
                * Math.sin(lngDiff / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = earthRadius * c;

        int meterConversion = 1609;
        double kmConvertion = 1.6093;
        // return new Float(distance * meterConversion).floatValue();

        finalDistance = distance * kmConvertion;
        return String.format("%.2f", new Float(distance * kmConvertion).floatValue()) + " km";
        // return String.format("%.2f", distance)+" m";
    }


    public String loadJSONFromAsset() {
        String json = "";
        try {
            InputStream is = getApplicationContext().getAssets().open("hospital.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        Log.d("ASDfdsaf", "Asdfas");
        return json;
    }
}

