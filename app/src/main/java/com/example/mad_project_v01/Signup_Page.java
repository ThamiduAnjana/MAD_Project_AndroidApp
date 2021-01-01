package com.example.mad_project_v01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;

public class Signup_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup__page);

        //Dropdown
        final AutoCompleteTextView cus_title = (AutoCompleteTextView)findViewById(R.id.txt_title);
        ImageView cus_title_icon = (ImageView)findViewById(R.id.dropdown_icon);

        cus_title.setThreshold(0);

        ArrayAdapter<String>adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,titles);
        cus_title.setAdapter(adapter);

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
                Intent intent = new Intent(Signup_Page.this,Signin_Page.class);
                startActivity(intent);
            }
        });

    }
    private static final String[] titles = new String[]{"Mr.","Mrs.","Miss","Ms.","Dr.","Rev.","Other"};
}