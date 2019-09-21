package com.achs.voilence_protector.Adapter;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.achs.voilence_protector.R;
import com.achs.voilence_protector.util.Contact;

import java.util.ArrayList;

public class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {

    Activity activity;
    ArrayList<Contact> contactList;

    public ContactAdapter(Activity activity, ArrayList<Contact> contactList) {

        this.activity = activity;
        this.contactList = contactList;
    }


    @NonNull
    @Override
    public ContactAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.contact_list_row, viewGroup, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, final int i) {
        Log.d("asdasd", String.valueOf(contactList.size()));
        viewHolder.txtNameValue.setText(contactList.get(i).getName());
        viewHolder.txtPhoneValue.setText(contactList.get(i).getPhone());

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(activity, UserDetails.class);
//                intent.putExtra("sendData",contactList.get(i).getId());
//                intent.putExtra("sendData",contactList.get(i).getTitle());
//                intent.putExtra("sendData",contactList);
//                activity.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return contactList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtNameValue, txtPhoneValue;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNameValue = itemView.findViewById(R.id.txt_name_value);
            txtPhoneValue = itemView.findViewById(R.id.txt_phone_value);

        }
    }
}
