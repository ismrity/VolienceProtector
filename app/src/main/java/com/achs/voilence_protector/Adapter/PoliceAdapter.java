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

import com.achs.voilence_protector.Activity.PoliceActivity;
import com.achs.voilence_protector.R;
import com.achs.voilence_protector.util.PoliceDistance;
import com.google.gson.Gson;

import java.util.ArrayList;

public class PoliceAdapter extends RecyclerView.Adapter<PoliceAdapter.ViewHolder> {
    private Activity activity;
    ArrayList<Double> distances;
    ArrayList<PoliceDistance> Pdata;
    ArrayList<String> phones;
    private Location loc1, loc2;
    private double myLati, myLongi;
    private float distanceInKm;

    public PoliceAdapter(ArrayList<PoliceDistance> data, PoliceActivity policeActivity) {

        this.Pdata = data;
        this.activity = policeActivity;
        this.distances = distances;
        this.phones= phones;
    }

    @NonNull
    @Override
    public PoliceAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.police_list_row, viewGroup, false);

        return new PoliceAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PoliceAdapter.ViewHolder viewHolder, int position) {
        Log.d("dasas", new Gson().toJson(distances));
        viewHolder.txtDistanceValue.setText(String.valueOf(Pdata.get(position).getDistance()) + "Km");
        viewHolder.txtPoliceName.setText(Pdata.get(position).getName());
        viewHolder.txtPhoneValue1.setText(Pdata.get(position).getPhone());
    }


    @Override
    public int getItemCount() {
        return Pdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtPoliceName, txtDistanceValue, txtPhoneValue1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPoliceName = itemView.findViewById(R.id.txt_name_of_police);
            txtDistanceValue = itemView.findViewById(R.id.txt_distance_value1);
            txtPhoneValue1 = itemView.findViewById(R.id.txt_phone_value1);
        }
    }

}

