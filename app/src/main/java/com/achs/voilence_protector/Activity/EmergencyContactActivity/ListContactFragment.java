package com.achs.voilence_protector.Activity.EmergencyContactActivity;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.achs.voilence_protector.Adapter.ContactAdapter;
import com.achs.voilence_protector.DB.ContactDBHelper;
import com.achs.voilence_protector.R;
import com.achs.voilence_protector.util.Contact;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListContactFragment extends Fragment {

    ContactDBHelper contactDbHelper;
    RecyclerView recyclerView;
    private ArrayList<Contact> contactList = new ArrayList<>();
    ContactAdapter mAdapter;

    public ListContactFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_all_contact, container, false);
        contactDbHelper = new ContactDBHelper(getActivity());
        recyclerView = view.findViewById(R.id.recycler_view);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        loadFromDb();

        return view;
        // Inflate the layout for this fragment
    }

    private void loadFromDb() {
        try {
            contactList.clear();
            contactList = contactDbHelper.getAllContactString();
            mAdapter = new ContactAdapter(getActivity(), contactList);
            recyclerView.setAdapter(mAdapter);
        } catch (Exception e) {
            Log.d("Error==>Db ", e.getLocalizedMessage());
        }
    }

}
