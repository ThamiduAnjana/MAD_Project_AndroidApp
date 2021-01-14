package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
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
                if(!isConnected(Signin_Page.this)){
                    Toast.makeText(Signin_Page.this,"Please connect to the internet to proceed further!",Toast.LENGTH_LONG).show();
                    showmsg();
                }else {
                    Intent intent = new Intent(Signin_Page.this,Signup_Page.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    finish();
                }
            }
        });

        forget_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isConnected(Signin_Page.this)){
                    Toast.makeText(Signin_Page.this,"Please connect to the internet to proceed further!",Toast.LENGTH_LONG).show();
                    showmsg();
                }else{
                    Intent intent = new Intent(Signin_Page.this,EnterPhoneNumber_Page.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
                    finish();
                }
            }
        });

        cus_contact = (MaskEditText)findViewById(R.id.txt_contactno);
        cus_password = (TextInputEditText)findViewById(R.id.txt_password);

        final ProgressBar progressbar = (ProgressBar)findViewById(R.id.progressbar);

        btn_signin = (Button)findViewById(R.id.btn_Signin);

        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    if(!isConnected(Signin_Page.this)){
                        Toast.makeText(Signin_Page.this,"Please connect to the internet to proceed further!",Toast.LENGTH_LONG).show();
                        showmsg();
                    }else{
                        final String cuscont = cus_contact.getText().toString();
                        final String cuspass = cus_password.getText().toString();

                        if(!cuscont.trim().isEmpty() && !cuspass.trim().isEmpty()){
                            progressbar.setVisibility(View.VISIBLE);
                            btn_signin.setVisibility(View.INVISIBLE);

                            try{
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
                                            intent.putExtra("title",usertitle);
                                            intent.putExtra("name",userfname);
                                            intent.putExtra("mobile",cuscont);
                                            startActivity(intent);
                                            overridePendingTransition(R.anim.zoom_in,R.anim.static_animation);
                                            finish();
                                        }else {
                                            Toast.makeText(Signin_Page.this,"Login Fail..!",Toast.LENGTH_LONG).show();
                                            progressbar.setVisibility(View.INVISIBLE);
                                            btn_signin.setVisibility(View.VISIBLE);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError error) {
                                        Toast.makeText(Signin_Page.this,"Your Not Registered..!",Toast.LENGTH_LONG).show();
                                    }
                                });
                            }catch (Exception e){
                                Toast.makeText(Signin_Page.this,"Your Not Registered..!",Toast.LENGTH_LONG).show();
                            }
                        }else {
                            Toast.makeText(Signin_Page.this,"Fill out the empty fields..!",Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e){
                    Toast.makeText(Signin_Page.this,"Check Fields..!",Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    //Check Internet Connection
    private boolean isConnected(Signin_Page mainActivity) {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo mobileConn = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        if((wifiConn != null && wifiConn.isConnected()) || (mobileConn != null && mobileConn.isConnected())){
            return true;
        }else {
            return false;
        }

    }
    private void showmsg() {

        AlertDialog.Builder builder = new AlertDialog.Builder(Signin_Page.this);
        builder.setMessage("Please connect to the internet to proceed further")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
    }

}