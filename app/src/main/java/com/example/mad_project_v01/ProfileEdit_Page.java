package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskedittext.MaskEditText;

import java.util.HashMap;

public class ProfileEdit_Page extends AppCompatActivity {

    DatabaseReference DatabaseRef;

    private String selected_cus_title,cus_contact,cus_name,cus_title;

    EditText cfname,clname,caddress,ccity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit__page);

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_name = getIntent().getExtras().getString("name");
        cus_title = getIntent().getExtras().getString("title");

        //Dropdown
        final AutoCompleteTextView cus_title = (AutoCompleteTextView)findViewById(R.id.txt_title);
        ImageView cus_title_icon = (ImageView)findViewById(R.id.dropdown_icon);

        cus_title.setThreshold(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,titles);
        cus_title.setAdapter(adapter);
        //get Selected data
        cus_title.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_cus_title = parent.getItemAtPosition(position).toString();
            }
        });

        cus_title_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cus_title.showDropDown();
            }
        });
        //End Dropdown

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Register Customer
//        final AutoCompleteTextView ctitle = (AutoCompleteTextView)findViewById(R.id.txt_title);
        cfname = (EditText)findViewById(R.id.txt_fname);
        clname = (EditText)findViewById(R.id.txt_lname);
        caddress = (EditText)findViewById(R.id.txt_address);
        ccity = (EditText)findViewById(R.id.txt_city);

        Button btn_Update = (Button)findViewById(R.id.btn_Update);

        btn_Update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("users");

                    //Get data
                    final String title = selected_cus_title;
                    final String fname = cfname.getText().toString();
                    String lname =  clname.getText().toString();
                    String address = caddress.getText().toString();
                    String city = ccity.getText().toString();

                    HashMap hashMap = new HashMap();

                    if(!title.trim().isEmpty() && !fname.trim().isEmpty() && !lname.trim().isEmpty() && !address.trim().isEmpty() && !city.trim().isEmpty()){
                        hashMap.put("title",title);
                        hashMap.put("fname",fname);
                        hashMap.put("lname",lname);
                        hashMap.put("address",address);
                        hashMap.put("city",city);

                        DatabaseRef.child(cus_contact).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                            @Override
                            public void onSuccess(Object o) {
                                Toast.makeText(ProfileEdit_Page.this,"Your Data is successfully update.",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ProfileEdit_Page.this,MainMenu_Page.class);
                                intent.putExtra("title",title);
                                intent.putExtra("name",fname);
                                intent.putExtra("mobile",cus_contact);
                                startActivity(intent);
                                overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                                finish();
                            }
                        });
                    }else {
                        Toast.makeText(ProfileEdit_Page.this,"Fill out that you want.",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Toast.makeText(ProfileEdit_Page.this,"Fill out that you want.",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("users").child(cus_contact);
        DatabaseRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    String usertitle = snapshot.child("title").getValue().toString();
                    String userfname = snapshot.child("fname").getValue().toString();
                    String userlname = snapshot.child("lname").getValue().toString();
                    String useraddress = snapshot.child("address").getValue().toString();
                    String usercity = snapshot.child("city").getValue().toString();

                    cfname = (EditText)findViewById(R.id.txt_fname);
                    clname = (EditText)findViewById(R.id.txt_lname);
                    caddress = (EditText)findViewById(R.id.txt_address);
                    ccity = (EditText)findViewById(R.id.txt_city);

                    AutoCompleteTextView cus_title = (AutoCompleteTextView)findViewById(R.id.txt_title);
                    cus_title.setText(usertitle,false);

                    cfname.setText(userfname);
                    clname.setText(userlname);
                    caddress.setText(useraddress);
                    ccity.setText(usercity);

                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private static final String[] titles = new String[]{"Mr.","Mrs.","Miss.","Ms.","Dr.","Rev.","Other"};

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ProfileEdit_Page.this,MainMenu_Page.class);
        intent.putExtra("title",cus_title);
        intent.putExtra("name",cus_name);
        intent.putExtra("mobile",cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();
    }
}