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
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class OrderConfirm_Page extends AppCompatActivity {

    String cus_contact,cus_name,selected_card_type,cus_title,TotalAmount;
    DatabaseReference DatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm__page);

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_name = getIntent().getExtras().getString("name");
        cus_title = getIntent().getExtras().getString("title");
        TotalAmount = getIntent().getExtras().getString("TotalAmount");
//        intent.putExtra("title",cus_title);

        final EditText txt_name = (EditText)findViewById(R.id.txt_name);
        txt_name.setText(cus_name);
        final EditText txt_cardnumber = (EditText)findViewById(R.id.txt_cardnumber);
        final EditText txt_expdate = (EditText)findViewById(R.id.txt_expdate);
        final EditText txt_securitycode = (EditText)findViewById(R.id.txt_securitycode);
        EditText txt_orderid = (EditText)findViewById(R.id.txt_orderid);
        txt_orderid.setText(cus_contact);
        EditText txt_totalamount = (EditText)findViewById(R.id.txt_totalamount);
        txt_totalamount.setText(TotalAmount);

        //Back Button
        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //Dropdown
        final AutoCompleteTextView cardtype = (AutoCompleteTextView)findViewById(R.id.txt_cardtype);
        ImageView cardtype_icon = (ImageView)findViewById(R.id.dropdown_icon);

        cardtype.setThreshold(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,CardType);
        cardtype.setAdapter(adapter);
        //get Selected data
        cardtype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selected_card_type = parent.getItemAtPosition(position).toString();
            }
        });

        cardtype_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardtype.showDropDown();
            }
        });
        //End Dropdown

        //Insert Data
        Button btn_submit = (Button)findViewById(R.id.btn_submit);

        btn_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get data
                String card_type = selected_card_type;
                String name = txt_name.getText().toString();
                String card_number =  txt_cardnumber.getText().toString();
                String exp_date = txt_expdate.getText().toString();
                String security_code = txt_securitycode.getText().toString();
                String order_id = cus_contact;
                String amount = TotalAmount;

                if(!card_type.trim().isEmpty() && !name.trim().isEmpty() && !card_number.trim().isEmpty()
                        && !exp_date.trim().isEmpty() && !security_code.trim().isEmpty() && !order_id.trim().isEmpty() && !amount.trim().isEmpty()){

                    DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("placeorderpayments");

                    PlaceOrderPayments obj = new PlaceOrderPayments(card_type,name,card_number,exp_date,security_code,order_id,amount);

                    DatabaseRef.child(cus_contact).setValue(obj);

                    Toast.makeText(OrderConfirm_Page.this,"Place Order Successful..!",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(OrderConfirm_Page.this,Home_Page.class);
                    intent.putExtra("title",cus_title);
                    intent.putExtra("name",cus_name);
                    intent.putExtra("mobile",cus_contact);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
                    finish();

                }else {
                    Toast.makeText(OrderConfirm_Page.this,"Fill your card information..",Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private static final String[] CardType = new String[]{"VISA","MasterCard"};

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderConfirm_Page.this,MyCart_Page.class);
        intent.putExtra("title",cus_title);
        intent.putExtra("name",cus_name);
        intent.putExtra("mobile",cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();
    }
}