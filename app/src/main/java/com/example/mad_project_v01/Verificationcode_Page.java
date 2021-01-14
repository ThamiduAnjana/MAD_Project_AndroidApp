package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class Verificationcode_Page extends AppCompatActivity {

    EditText input1,input2,input3,input4,input5,input6;

    private String verificationId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificationcode__page);

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Verificationcode_Page.this,Signin_Page.class);
                startActivity(intent);
                finish();
            }
        });

        final String cus_contact = getIntent().getStringExtra("mobile");

        TextView lb_mobile = (TextView)findViewById(R.id.lb_mobile);
        lb_mobile.setText(cus_contact);


        input1 = findViewById(R.id.txt_input1);
        input2 = findViewById(R.id.txt_input2);
        input3 = findViewById(R.id.txt_input3);
        input4 = findViewById(R.id.txt_input4);
        input5 = findViewById(R.id.txt_input5);
        input6 = findViewById(R.id.txt_input6);

        setupOTPInput();

        final ProgressBar progressbar = (ProgressBar)findViewById(R.id.progressbar);
        final Button btn_verify = (Button)findViewById(R.id.btn_verify);

        verificationId = getIntent().getStringExtra("verificationId");

        btn_verify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(input1.getText().toString().trim().isEmpty()
                || input2.getText().toString().trim().isEmpty()
                || input3.getText().toString().trim().isEmpty()
                || input4.getText().toString().trim().isEmpty()
                || input5.getText().toString().trim().isEmpty()
                || input6.getText().toString().trim().isEmpty()){
                    Toast.makeText(Verificationcode_Page.this, "Please enter valid code", Toast.LENGTH_SHORT).show();
                    return;
                }
                String code = input1.getText().toString() +
                        input2.getText().toString() +
                        input3.getText().toString() +
                        input4.getText().toString() +
                        input5.getText().toString() +
                        input6.getText().toString();

                if(verificationId != null){
                    progressbar.setVisibility(View.VISIBLE);
                    btn_verify.setVisibility(View.INVISIBLE);
                    PhoneAuthCredential phoneAuthCredential = PhoneAuthProvider.getCredential(
                        verificationId,code
                    );
                    FirebaseAuth.getInstance().signInWithCredential(phoneAuthCredential)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    progressbar.setVisibility(View.GONE);
                                    btn_verify.setVisibility(View.VISIBLE);
                                    if(task.isSuccessful()){
                                        Intent intent = new Intent(getApplicationContext(),ChangePassword_Page.class);
                                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        intent.putExtra("mobile",cus_contact);
                                        intent.putExtra("screen","verify");
                                        startActivity(intent);
                                        finish();
                                    }else{
                                        Toast.makeText(Verificationcode_Page.this, "The verification code entered was invalid..!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        findViewById(R.id.link_resendotp).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        getIntent().getStringExtra("mobile"),30,
                        TimeUnit.SECONDS,
                        Verificationcode_Page.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks(){

                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                Toast.makeText(Verificationcode_Page.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onCodeSent(@NonNull String newverificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                verificationId = newverificationId;
                                Toast.makeText(Verificationcode_Page.this, "OTP Send", Toast.LENGTH_SHORT).show();
                            }
                        }
                );
            }
        });

    }
    private void setupOTPInput(){
        input1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input2.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input3.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input4.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input5.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        input5.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!s.toString().trim().isEmpty()){
                    input6.requestFocus();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}