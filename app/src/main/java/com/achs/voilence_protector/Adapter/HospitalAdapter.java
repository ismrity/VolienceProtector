package com.achs.voilence_protector.Adapter;

import android.app.Activity;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.achs.voilence_protector.Activity.HospitalActivity;
import com.achs.voilence_protector.R;
import com.achs.voilence_protector.util.HospitalDistance;
import com.google.gson.Gson;

import java.util.ArrayList;

public class HospitalAdapter extends RecyclerView.Adapter<HospitalAdapter.ViewHolder> {
    private Activity activity;
    private ArrayList<HospitalDistance> hospitalList;
    ArrayList<Float> distancess;

    private Location loc1, loc2;
    private double myLat, myLong;

    private float distanceInKm;


//    public HospitalAdapter(ArrayList<Hospital.Data> hospitalList, HospitalActivity hospitalActivity, double myLat, double myLong) {
//        this.hospitalList = hospitalList;
//        this.activity = hospitalActivity;
//        this.myLat = myLat;
//        this.myLong = myLong;
//
//    }

    public HospitalAdapter(ArrayList<HospitalDistance> data, HospitalActivity hospitalActivity) {

        this.hospitalList = data;
        this.activity = hospitalActivity;
        this.distancess = distancess;


    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.hospital_list_row, viewGroup, false);

        return new ViewHolder(view);
    }


//    ArrayList<Float> distancess = new ArrayList<>();


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Log.d("dasas", new Gson().toJson(distancess));
        viewHolder.txxDistanceValue.setText(String.valueOf(hospitalList.get(position).getDistance()) + " Km");
        viewHolder.txtHospitalName.setText(hospitalList.get(position).getName());
        viewHolder.txtPhoneValue.setText(hospitalList.get(position).getPhone());
    }


    @Override
    public int getItemCount() {
        return hospitalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtHospitalName, txxDistanceValue, txtPhoneValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtHospitalName = itemView.findViewById(R.id.txt_name_of_hospital);
            txxDistanceValue = itemView.findViewById(R.id.txt_distance_value);
            txtPhoneValue = itemView.findViewById(R.id.txt_phone_value2);


        }
    }
}
