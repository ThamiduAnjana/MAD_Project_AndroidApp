package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MyCart_Page extends AppCompatActivity {

    RecyclerView savemycarttable;
    private DatabaseReference DatabaseRef,mycartNoOfProRef,MyCartTotProRef;

    private String cus_title,cus_contact,cus_name,selected_payment_method;

    public int No_Of_Pro = 0, Sub_Total = 0;

    TextView txt_subtotal,txt_maintotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart__page);

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_name = getIntent().getExtras().getString("name");
        cus_title = getIntent().getExtras().getString("title");

        //Dorpdown

        Spinner txt_paymethod = (Spinner)findViewById(R.id.txt_paymethod);
        ArrayAdapter<String> title_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.Paymentmethod));
        title_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        txt_paymethod.setAdapter(title_adapter);

        txt_paymethod.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selected_payment_method = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //End Dropdown

        //Order Confirm
        Button btn_confirm = (Button)findViewById(R.id.btn_confirm);


        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    switch (selected_payment_method){
                        case "":
                            Toast.makeText(MyCart_Page.this,"Please select payment method.. ",Toast.LENGTH_LONG).show();
                            break;
                        case "Cash On Delivery":
//                        Intent intent = new Intent(MyCart_Page.this,OrderConfirm_Page.class);
//                        intent.putExtra("title",cus_title);
//                        intent.putExtra("name",cus_name);
//                        intent.putExtra("mobile",cus_contact);
//                        intent.putExtra("TotalAmount",txt_maintotal.getText());
//                        startActivity(intent);
//                        overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
//                        finish();
                            break;
                        case "Credit Cards":
                            Intent intent = new Intent(MyCart_Page.this,OrderConfirm_Page.class);
                            intent.putExtra("title",cus_title);
                            intent.putExtra("name",cus_name);
                            intent.putExtra("mobile",cus_contact);
                            intent.putExtra("TotalAmount",txt_maintotal.getText());
                            startActivity(intent);
                            overridePendingTransition(R.anim.slide_from_left,R.anim.slide_to_right);
                            finish();
                            break;
                        default:Toast.makeText(MyCart_Page.this,"Please select payment method.. ",Toast.LENGTH_LONG).show();
                    }
                }catch (Exception e){
                    Toast.makeText(MyCart_Page.this,"Error.. ",Toast.LENGTH_LONG).show();
                }
            }
        });

        Button btn_back = (Button)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart").child(cus_contact);
        DatabaseRef.keepSynced(true);

        savemycarttable = (RecyclerView) findViewById(R.id.savemycarttable);
        savemycarttable.setHasFixedSize(true);
        savemycarttable.setLayoutManager(new LinearLayoutManager(this));

        //MyCart Number of Products
        final TextView txt_itemcount = findViewById(R.id.txt_itemcount);
        mycartNoOfProRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart");

        mycartNoOfProRef.child(cus_contact).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    No_Of_Pro = (int) snapshot.getChildrenCount();
                    txt_itemcount.setText(Integer.toString(No_Of_Pro));
                }else {
                    txt_itemcount.setText("0");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<SaveMyCartHelper,ProductListViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SaveMyCartHelper,ProductListViewHolder>
                (SaveMyCartHelper.class,R.layout.productlist_row, ProductListViewHolder.class,DatabaseRef){

            @Override
            protected void populateViewHolder(ProductListViewHolder productListViewHolder, final SaveMyCartHelper model, int position) {

                productListViewHolder.setP_Name(model.getP_name());
                productListViewHolder.setP_Price(model.getP_price());
                productListViewHolder.setP_Qty(model.getP_qty());
                productListViewHolder.setP_Total(model.getP_price(),model.getP_qty());

                //Main Total
                txt_maintotal = (TextView)findViewById(R.id.txt_maintotal);
                //MyCart LB Price
                final TextView lb_cartprice = (TextView)findViewById(R.id.lb_cartprice);
                //MyCart Delivery Charges
                final TextView txt_deliverycharges = (TextView)findViewById(R.id.txt_deliverycharges);
                final int deliverycharges = Integer.parseInt(txt_deliverycharges.getText().toString());
                //Sub Total
                txt_subtotal = findViewById(R.id.txt_subtotal);
                MyCartTotProRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart").child(cus_contact);

                MyCartTotProRef.child(model.getP_code()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String qty = snapshot.child("p_qty").getValue().toString();
                            String price = snapshot.child("p_price").getValue().toString();
                            Sub_Total = Sub_Total + (Integer.parseInt(qty)*Integer.parseInt(price));
                            Log.d("Array S",String.valueOf(Sub_Total));
                            txt_subtotal.setText(String.valueOf(Sub_Total));
                        }else{
                            txt_subtotal.setText("Rs.0");
                        }
                        txt_maintotal.setText(String.valueOf(Sub_Total + deliverycharges));
                        lb_cartprice.setText("Rs." + String.valueOf(Sub_Total + deliverycharges));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                productListViewHolder.itemView.findViewById(R.id.btn_remove).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try{
                            DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart").child(cus_contact);

                            DatabaseRef.child(model.getP_code()).removeValue();

                            DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("placeorderproducts").child(cus_contact);

                            DatabaseRef.child(model.getP_code()).removeValue();

                            Intent intent = new Intent(MyCart_Page.this,MyCart_Page.class);
                            intent.putExtra("title",cus_title);
                            intent.putExtra("name",cus_name);
                            intent.putExtra("mobile",cus_contact);
                            startActivity(intent);
                        }catch (Exception e){

                        }
                    }
                });


            }
        };
        savemycarttable.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductListViewHolder extends RecyclerView.ViewHolder {
        View mView;

        public ProductListViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
        }
        public void setP_Name(String p_name) {
            TextView pro_code = (TextView) mView.findViewById(R.id.txt_product);
            pro_code.setText(p_name);
        }
        public void setP_Price(Long p_price){
            TextView pro_price = (TextView)mView.findViewById(R.id.txt_price);
            pro_price.setText(p_price.toString());
        }
        public void setP_Qty(int p_qty){
            TextView pro_qty = (TextView)mView.findViewById(R.id.txt_qty);
            pro_qty.setText(String.valueOf(p_qty));
        }
        public void setP_Total(Long p_price,int p_qty){
            TextView pro_total = (TextView)mView.findViewById(R.id.txt_total);
            String totalString = String.valueOf((Integer.parseInt(String.valueOf(p_price)))*p_qty);
            pro_total.setText(totalString);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyCart_Page.this,MainMenu_Page.class);
        intent.putExtra("title",cus_title);
        intent.putExtra("name",cus_name);
        intent.putExtra("mobile",cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();
    }
}
