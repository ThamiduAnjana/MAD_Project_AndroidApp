package com.example.mad_project_v01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainMenu_Page extends AppCompatActivity {

    String cus_title,cus_name,cus_contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu__page);

        cus_name = getIntent().getExtras().getString("name");
        TextView uName = (TextView)findViewById(R.id.lb_cusname);
        uName.setText(cus_name);

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_title = getIntent().getExtras().getString("title");

        Button backmenu = (Button)findViewById(R.id.btn_backmenu);

        backmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //Edit Profile
        Button btn_editprofie = (Button)findViewById(R.id.btn_editprofie);

        btn_editprofie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu_Page.this,ProfileEdit_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                finish();
            }
        });
        //Shipping Deatils
        Button btn_shippingdeatils = (Button)findViewById(R.id.btn_shippingdeatils);

        btn_shippingdeatils.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu_Page.this,ShippingDetails_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                finish();
            }
        });
        //Password Change
        Button btn_changepassword = (Button)findViewById(R.id.btn_changepassword);

        btn_changepassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu_Page.this,ChangePassword_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("mobile",cus_contact);
                intent.putExtra("screen","mainmenu");
                intent.putExtra("name",cus_name);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                finish();
            }
        });
        //Comments And Comlaints
        Button btn_commcompl = (Button)findViewById(R.id.btn_commcompl);

        btn_commcompl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu_Page.this,CommentsAndComplaints_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                finish();
            }
        });
        //Contact us with Google Map
        Button btn_contactus = (Button)findViewById(R.id.btn_contactus);

        btn_contactus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu_Page.this,ContactUsGMap_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                finish();
            }
        });
        //Log out
        Button btn_logout = (Button)findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainMenu_Page.this,"Log out",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainMenu_Page.this,Signin_Page.class);
                startActivity(intent);
                overridePendingTransition(R.anim.static_animation,R.anim.zoom_out);
                finish();
            }
        });
        //Save Cart
        Button btn_savecart = (Button)findViewById(R.id.btn_savecart);

        btn_savecart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainMenu_Page.this,MyCart_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MainMenu_Page.this,Home_Page.class);
        intent.putExtra("title",cus_title);
        intent.putExtra("name",cus_name);
        intent.putExtra("mobile",cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_top,R.anim.slide_to_bottom);
        finish();
    }
}