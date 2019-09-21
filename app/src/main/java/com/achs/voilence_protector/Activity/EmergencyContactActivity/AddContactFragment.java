package com.achs.voilence_protector.Activity.EmergencyContactActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.achs.voilence_protector.DB.ContactDBHelper;
import com.achs.voilence_protector.R;


public class AddContactFragment extends Fragment {
    Button btnSubmit, btnReset, btnView;//button variables
    EditText edName, edPhone;
    Integer sizeOfDB;
//    RecyclerView recyclerView;
    
    private ContactDBHelper contactDbHelper;
   

    public AddContactFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_contact, container, false);
        contactDbHelper = new ContactDBHelper(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        contactDbHelper.close();

        edName = view.findViewById(R.id.ed_name);
        edPhone = view.findViewById(R.id.ed_phone);


        btnSubmit= view.findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString().trim();
                String phone = edPhone.getText().toString().trim();
                if(name.equals("")|| phone.equals("")) {
                    Toast.makeText(getActivity(), "Both Name and Phone no are required", Toast.LENGTH_SHORT).show();
                }
                else {
                    long val = contactDbHelper.addContact(name, phone);
                    if (val > 0){
                        Toast.makeText(getContext(),"Contact is submitted",Toast.LENGTH_LONG).show();
                    }else {
                        Toast.makeText(getActivity(),"Contact submission error",Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        btnReset= view.findViewById(R.id.btn_reset);
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//reset button

                edName.setText("");
                edPhone.setText("");

            }
        });

        btnView = view.findViewById(R.id.btn_view);
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {//reset button
                Intent i = new Intent(getContext(), EmergencyContactActivity.class);
                startActivity(i);

            }
        });
    }

   


}
