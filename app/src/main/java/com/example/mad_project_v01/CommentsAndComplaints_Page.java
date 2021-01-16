package com.example.mad_project_v01;

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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentsAndComplaints_Page extends AppCompatActivity {

    DatabaseReference DatabaseRef;

    String selected_type,cus_contact,cus_name,cus_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_and_complaints__page);

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_name = getIntent().getExtras().getString("name");
        cus_title = getIntent().getExtras().getString("title");

        //Dropdown
        Spinner txt_selectbox = (Spinner)findViewById(R.id.txt_selectbox);
        ArrayAdapter<String> title_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.CoCoitems));
        title_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txt_selectbox.setAdapter(title_adapter);

        txt_selectbox.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_type = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

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
        final EditText c_mname = (EditText)findViewById(R.id.txt_mname);
        final EditText c_message = (EditText)findViewById(R.id.txt_message);

        Button btn_send = (Button)findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    //Get data
                    final String select_type = selected_type;
                    final String cus_mname = c_mname.getText().toString();
                    String message =  c_message.getText().toString();

                    if(!select_type.trim().isEmpty() && !cus_mname.trim().isEmpty() && !message.trim().isEmpty()){
                        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("commentsandcomplaints");

                        CommentsAndComplaints obj = new CommentsAndComplaints(select_type,cus_mname,message);

                        DatabaseRef.child(cus_contact).setValue(obj);

                        Toast.makeText(CommentsAndComplaints_Page.this,"Send Successful..!",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(CommentsAndComplaints_Page.this,MainMenu_Page.class);
                        intent.putExtra("title",cus_title);
                        intent.putExtra("name",cus_name);
                        intent.putExtra("mobile",cus_contact);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                        finish();
                    }else {
                        Toast.makeText(CommentsAndComplaints_Page.this,"Fill out all field..!",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(CommentsAndComplaints_Page.this,"Error..!",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CommentsAndComplaints_Page.this,MainMenu_Page.class);
        intent.putExtra("title",cus_title);
        intent.putExtra("name",cus_name);
        intent.putExtra("mobile",cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();
    }
}