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

import com.achs.voilence_protector.Adapter.PoliceAdapter;
import com.achs.voilence_protector.R;
import com.achs.voilence_protector.util.Police;
import com.achs.voilence_protector.util.PoliceDistance;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PoliceActivity extends AppCompatActivity implements LocationListener {

    private RecyclerView recyclerView;
    //    List<Address> policeList;
    Police policeData;
    PoliceAdapter mAdapter;
    Geocoder geocoder;
    String bestProvider;
    PoliceDistance pd;
    Location location;

    private ArrayList<Police.Data> policeList;
    private double myLati, myLongi;
    private Location loc1, loc2;
    ArrayList<PoliceDistance> pdArrayList = new ArrayList<>();
    ArrayList<Double> distances = new ArrayList<>();
    ArrayList<String> phones=new ArrayList<>();

    protected LocationManager locationManager;
    protected LocationListener locationListener;
    private double myLat, myLong;
    private float distanceInKm;
    private double distanceInKmD;
    public double finalDistance = 0;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_police);
        recyclerView = findViewById(R.id.recycler_view_police);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        sharedPreferences = getSharedPreferences("location", 0);

        myLati = Double.parseDouble(sharedPreferences.getString("latitude", ""));
        myLongi = Double.parseDouble(sharedPreferences.getString("longitude", ""));

        policeData = new Gson().fromJson(loadJSONFromAsset(), new TypeToken<Police>() {
        }.getType());
        getLocation();

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


    private void getLocation() {

        loc1 = new Location("");

        for (int i = 0; i < policeData.getData().size(); i++) {

            loc1.setLatitude(Double.parseDouble(policeData.getData().get(i).getLatitude()));
            loc1.setLongitude(Double.parseDouble(policeData.getData().get(i).getLongitude()));

            loc2 = new Location("");

            loc2.setLatitude(myLati);
            loc2.setLongitude(myLongi);
            Log.e("ASdasd2", String.valueOf(loc1));
            Log.e("ASdasd3", String.valueOf(loc2));


//            distanceInMeters = loc1.distanceTo(loc2);     //in metere
//            distanceInMiles = loc1.distanceTo(loc2)/1609.344;     //in miles
            String dist = null;
            distanceInKm = loc1.distanceTo(loc2) / 1000;      //in kilometers
            dist = getDistance(
                    myLati,myLongi,
                    Double.parseDouble(policeData.getData().get(i).getLatitude()),
                    Double.parseDouble(policeData.getData().get(i).getLongitude()));
            distanceInKmD = fromDistance(27.7222117,85.2723292, Double.parseDouble(policeData.getData().get(i).getLatitude()),Double.parseDouble(policeData.getData().get(i).getLongitude()));
            Log.e("Asdasd", String.valueOf(distanceInKmD));
            Log.e("asssssssss", dist);
            Log.e("asssssssss", String.valueOf(finalDistance)+policeData.getData().get(i).getPolice());

            pd = new PoliceDistance();
            distances.add(finalDistance);
            pd.setDistance(finalDistance);
            pd.setName(policeData.getData().get(i).getPolice());
            pd.setPhone(policeData.getData().get(i).getPhone());
            pdArrayList.add(i,pd);

            Collections.sort(pdArrayList, new Comparator<PoliceDistance>() {
                @Override
                public int compare(PoliceDistance policeDistance, PoliceDistance t1) {
                    return policeDistance.getDistance().compareTo(t1.getDistance());                }
            });

        }


        Collections.sort(distances);
        Log.d("policeadapter", new Gson().toJson(distances));


        mAdapter = new PoliceAdapter(pdArrayList, this);
//        getLatAndLong();

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

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


    private double fromDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1))
                * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1))
                * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        return (dist);
    }

    private double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }


    private double meterDistanceBetweenPoints(float lat_source, float lng_source, float lat_destination, float lng_destination) {
        float pk = (float) (180.f/Math.PI);

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


    public String loadJSONFromAsset() {
        String json = "";
        try {
            InputStream is = getApplicationContext().getAssets().open("police.json");
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


