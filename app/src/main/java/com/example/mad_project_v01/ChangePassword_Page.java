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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ChangePassword_Page extends AppCompatActivity {

    DatabaseReference DatabaseRef;

    String cus_name,cus_contact,Screen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password__page);

        Button btn_back = (Button)findViewById(R.id.btn_back);

        cus_name = getIntent().getStringExtra("name");
        cus_contact = getIntent().getStringExtra("mobile");
        Screen = getIntent().getStringExtra("screen");

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final EditText fpassword = (EditText)findViewById(R.id.txt_password);
        final EditText copassword = (EditText)findViewById(R.id.txt_cpassword);

        //get data
        final String password = fpassword.getText().toString();
        final String cpassword = copassword.getText().toString();

        Button btn_save = (Button)findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(password.equals(cpassword)){
                    DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("users");

                    HashMap hashMap = new HashMap();
                    hashMap.put("password",password);

                    DatabaseRef.child(cus_contact).updateChildren(hashMap).addOnSuccessListener(new OnSuccessListener() {
                        @Override
                        public void onSuccess(Object o) {
                            Toast.makeText(ChangePassword_Page.this,"Your password is successfully update.",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(ChangePassword_Page.this,Signin_Page.class);
                            startActivity(intent);
                            overridePendingTransition(R.anim.static_animation,R.anim.zoom_out);
                            finish();
                        }
                    });

                }else {
                    Toast.makeText(ChangePassword_Page.this,"Check your password",Toast.LENGTH_SHORT).show();
                    fpassword.setText("");
                    copassword.setText("");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(Screen.equals("verify")){
            Intent intent = new Intent(ChangePassword_Page.this,Signin_Page.class);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            finish();
        }else if(Screen.equals("mainmenu")){
            Intent intent = new Intent(ChangePassword_Page.this,MainMenu_Page.class);
            intent.putExtra("name",cus_name);
            intent.putExtra("mobile",cus_contact);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
            finish();
        }
    }
}