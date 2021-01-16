package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskedittext.MaskEditText;

public class Signup_Page extends AppCompatActivity {

    DatabaseReference DatabaseRef;
    String selected_cus_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__page);

        //Dorpdown

        Spinner Spinnertxt_title = (Spinner)findViewById(R.id.txt_title);
        ArrayAdapter<String> title_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.title));
        title_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinnertxt_title.setAdapter(title_adapter);

        Spinnertxt_title.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_cus_title = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //Dorpdown end

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Register Customer
        final EditText cfname = (EditText)findViewById(R.id.txt_fname);
        final EditText clname = (EditText)findViewById(R.id.txt_lname);
        final EditText caddress = (EditText)findViewById(R.id.txt_address);
        final EditText ccity = (EditText)findViewById(R.id.txt_city);
        final MaskEditText cmobile = (MaskEditText)findViewById(R.id.txt_contactno);
        final TextInputEditText cpass = (TextInputEditText)findViewById(R.id.txt_password);
        final TextInputEditText ccpass = (TextInputEditText)findViewById(R.id.txt_cpassword);

        Button btn_signup = (Button)findViewById(R.id.btn_Signup);

        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    //get password
                    final String password = cpass.getText().toString();
                    final String cpassword = ccpass.getText().toString();
                    //Get data
                    String title = selected_cus_title;
                    String fname = cfname.getText().toString();
                    String lname =  clname.getText().toString();
                    String address = caddress.getText().toString();
                    String city = ccity.getText().toString();
                    final String mobile = cmobile.getText().toString();

                    if(!title.trim().isEmpty() && !fname.trim().isEmpty() && !lname.trim().isEmpty() && !address.trim().isEmpty() && !city.trim().isEmpty() && !mobile.trim().isEmpty()){
                        if(cpassword.equals(password)){

                            DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("users");

                            Customer obj = new Customer(title,fname,lname,address,city,mobile,password);

                            DatabaseRef.child(mobile).setValue(obj);

                            Toast.makeText(Signup_Page.this,"Registration Successful..!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Signup_Page.this,Signin_Page.class);
                            startActivity(intent);
                            finish();

                        }else {
                            Toast.makeText(Signup_Page.this,"Again check your Password..! > '"+password+"'",Toast.LENGTH_LONG).show();
                        }
                    }else {
                        Toast.makeText(Signup_Page.this,"Fill out all the details..!",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(Signup_Page.this,"Fill out all the details..!",Toast.LENGTH_LONG).show();
                }
            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Signup_Page.this,Signin_Page.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        finish();
    }
}