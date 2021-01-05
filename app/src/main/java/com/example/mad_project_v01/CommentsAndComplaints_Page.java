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
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CommentsAndComplaints_Page extends AppCompatActivity {

    DatabaseReference DatabaseRef;

    String selected_type,cus_contact,cus_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments_and_complaints__page);

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_name = getIntent().getExtras().getString("name");

        //Dropdown
        final AutoCompleteTextView txt_selectbox = (AutoCompleteTextView)findViewById(R.id.txt_selectbox);
        ImageView cus_title_icon = (ImageView)findViewById(R.id.dropdown_icon);

        txt_selectbox.setThreshold(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,titles);
        txt_selectbox.setAdapter(adapter);
        //get Selected data
        txt_selectbox.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_type = parent.getItemAtPosition(position).toString();
            }
        });

        cus_title_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_selectbox.showDropDown();
            }
        });
        //End Dropdown

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CommentsAndComplaints_Page.this,MainMenu_Page.class);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
            }
        });

        //Register Customer
        final EditText c_mname = (EditText)findViewById(R.id.txt_mname);
        final EditText c_message = (EditText)findViewById(R.id.txt_message);

        Button btn_send = (Button)findViewById(R.id.btn_send);

        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data
                final String select_type = selected_type;
                final String cus_mname = c_mname.getText().toString();
                String message =  c_message.getText().toString();

                DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("commentsandcomplaints");

                CommentsAndComplaints obj = new CommentsAndComplaints(select_type,cus_mname,message);

                DatabaseRef.child(cus_contact).setValue(obj);

                Toast.makeText(CommentsAndComplaints_Page.this,"Send Successful..!",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(CommentsAndComplaints_Page.this,MainMenu_Page.class);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                finish();

            }
        });

    }
    private static final String[] titles = new String[]{"Comments","Complaints"};
}