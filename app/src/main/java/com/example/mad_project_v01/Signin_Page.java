package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.santalu.maskedittext.MaskEditText;

public class Signin_Page extends AppCompatActivity {

    private DatabaseReference DatabaseRef;

    MaskEditText cus_contact;
    TextInputEditText cus_password;
    Button btn_signin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin__page);

        TextView reg_link = (TextView)findViewById(R.id.Link_Registration);
        TextView forget_link = (TextView)findViewById(R.id.link_ForgetPassword);

        reg_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin_Page.this,Signup_Page.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }
        });

        forget_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Signin_Page.this,EnterPhoneNumber_Page.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                finish();
            }
        });

        cus_contact = (MaskEditText)findViewById(R.id.txt_contactno);
        cus_password = (TextInputEditText)findViewById(R.id.txt_password);

        final ProgressBar progressbar = (ProgressBar)findViewById(R.id.progressbar);

        btn_signin = (Button)findViewById(R.id.btn_Signin);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressbar.setVisibility(View.VISIBLE);
                btn_signin.setVisibility(View.INVISIBLE);

                final String cuscont = cus_contact.getText().toString();
                final String cuspass = cus_password.getText().toString();

                DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("users").child(cuscont);
                DatabaseRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String pass = snapshot.child("password").getValue().toString();
                        String usertitle = snapshot.child("title").getValue().toString();
                        String userfname = snapshot.child("fname").getValue().toString();
                        if(cuspass.equals(pass)){
                            Toast.makeText(Signin_Page.this,"Login Successful..!",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Signin_Page.this,Home_Page.class);
                            intent.putExtra("name","Hi! , "+usertitle+userfname);
                            intent.putExtra("mobile",cuscont);
                            startActivity(intent);
                            overridePendingTransition(R.anim.zoom_in,R.anim.static_animation);
                            finish();
                        }else {
                            Toast.makeText(Signin_Page.this,"Login Fail..!",Toast.LENGTH_LONG).show();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

    }

}