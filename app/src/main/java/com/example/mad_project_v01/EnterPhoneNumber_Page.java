package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.FirebaseException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.santalu.maskedittext.MaskEditText;

import java.util.concurrent.TimeUnit;

public class EnterPhoneNumber_Page extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter_phone_number__page);

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        final MaskEditText txt_contactno = (MaskEditText)findViewById(R.id.txt_mobileno);
        final Button btn_getotp = (Button)findViewById(R.id.btn_getotp);

        final ProgressBar progressbar = (ProgressBar)findViewById(R.id.progressbar);

        btn_getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(txt_contactno.getText().toString().trim().isEmpty()){
                    Toast.makeText(EnterPhoneNumber_Page.this,"Enter Mobile",Toast.LENGTH_SHORT).show();
                    return;
                }
                progressbar.setVisibility(View.VISIBLE);
                btn_getotp.setVisibility(View.INVISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        txt_contactno.getText().toString(),30,
                        TimeUnit.SECONDS,
                        EnterPhoneNumber_Page.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressbar.setVisibility(View.GONE);
                                btn_getotp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressbar.setVisibility(View.GONE);
                                btn_getotp.setVisibility(View.VISIBLE);
                                Toast.makeText(EnterPhoneNumber_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressbar.setVisibility(View.GONE);
                                btn_getotp.setVisibility(View.VISIBLE);
                                Intent intent = new Intent(getApplicationContext(),Verificationcode_Page.class);
                                intent.putExtra("mobile",txt_contactno.getText().toString());
                                intent.putExtra("verificationId",verificationId);
                                startActivity(intent);
                                finish();
                            }
                        }
                );
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(EnterPhoneNumber_Page.this,Signin_Page.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
        finish();
    }
}