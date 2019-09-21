package com.achs.voilence_protector.Activity.EmergencyContactActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.achs.voilence_protector.Adapter.ContactAdapter;
import com.achs.voilence_protector.DB.ContactDBHelper;
import com.achs.voilence_protector.R;
import com.achs.voilence_protector.util.Contact;

import java.util.ArrayList;

public class EmergencyContactActivity extends AppCompatActivity {
    private Button btnAddContact;
    ContactDBHelper contactDBHelper;
    ContactAdapter mAdapter;
    private ArrayList<Contact> contactList = new ArrayList<>();
    Integer sizeOfDB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        contactDBHelper = new ContactDBHelper(this);
        loadFromDb();
        Log.d("size", String.valueOf(contactList.size()));
        sizeOfDB = contactList.size();

        Fragment listFragment = new ListContactFragment();
        loadFragment(listFragment);


        btnAddContact = findViewById(R.id.btn_add_contact);
        btnAddContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (sizeOfDB <= 4){
                    Fragment fragment = new AddContactFragment();
                    loadFragment(fragment);
                }else{
                    Toast.makeText(getApplicationContext(), "Your contact numbers has already reached 5.", Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    private void loadFromDb() {
        try {
            contactList.clear();
            contactList = contactDBHelper.getAllContactString();
            mAdapter = new ContactAdapter(EmergencyContactActivity.this, contactList);
//            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.d("Error==>Db ", e.getLocalizedMessage());
        }
    }



}