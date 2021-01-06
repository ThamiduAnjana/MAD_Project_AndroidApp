package com.example.mad_project_v01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class ShippingDetails_Page extends AppCompatActivity {

    DatabaseReference DatabaseRef;

    String cus_contact,cus_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping_details__page);

        cus_name = getIntent().getExtras().getString("name");
        cus_contact = getIntent().getExtras().getString("mobile");

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final EditText caddress = (EditText)findViewById(R.id.txt_address);

        Button btn_update = (Button)findViewById(R.id.btn_Update);

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address = caddress.getText().toString();
                HashMap hashMap = new HashMap();
                hashMap.put("address",address);

                DatabaseRef.child(cus_contact).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(ShippingDetails_Page.this,"Your Data is successfully update.",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(ShippingDetails_Page.this,MainMenu_Page.class);
                        intent.putExtra("name",cus_name);
                        intent.putExtra("mobile",cus_contact);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        finish();
                    }
                });
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ShippingDetails_Page.this,MainMenu_Page.class);
        intent.putExtra("name",cus_name);
        intent.putExtra("mobile",cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();
    }
}