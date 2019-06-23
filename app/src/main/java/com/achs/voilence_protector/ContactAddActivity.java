package com.achs.voilence_protector;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.achs.voilence_protector.DB.DBHelper;
import com.achs.voilence_protector.DB.DBManager;

import java.util.ArrayList;
import java.util.List;

public class ContactAddActivity extends AppCompatActivity {
    Button btn1, btn2,btn3;//button variables
    EditText editname, editphone;
    TextView result;
    private DBManager dbManager;
    DBHelper dbHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact2);

        dbManager = new DBManager(this);
        dbManager.open();
        final DBHelper dbHelper = new DBHelper(ContactAddActivity.this);

            dbManager.insert("1", "1 desc");

        Toast.makeText(ContactAddActivity.this, String.valueOf(dbManager.getContactCount()), Toast.LENGTH_SHORT).show();


        editname = (EditText) findViewById(R.id.editname);
        editphone = (EditText) findViewById(R.id.editphone);
        result=(TextView)findViewById(R.id.tvResult);
         btn1= (Button) findViewById(R.id.btn1);
         btn2= (Button) findViewById(R.id.btn2);
         btn3=(Button) findViewById(R.id.btn3);
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Toast.makeText(getApplicationContext(),"for viewing data",Toast.LENGTH_SHORT).show();


            }
        });


        btn1.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {
                                        String name = editname.getText().toString();
                                        String phone = editphone.getText().toString();
                                        if(name.equals("")|| phone.equals("")) {
                                            Toast.makeText(getApplicationContext(), "Both Name and Phone no are required", Toast.LENGTH_SHORT).show();
                                        }
                                        else {
                                            dbManager.insert(editname.getText().toString().trim(),editphone.getText().toString().trim());
                                            //dbHelper.addContact(editname.getText().toString().trim(),editphone.getText().toString().trim());
                                            Toast.makeText(ContactAddActivity.this,"The count is: "+String.valueOf(dbHelper.getContactCount()),Toast.LENGTH_LONG);

                                           // Toast.makeText(getApplicationContext(),"It is submitted",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

          btn2.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {//reset button

                  editname.setText("");
                  editphone.setText("");
                  result.setText("");
                  editname.requestFocus();

              }
          });










    }



}