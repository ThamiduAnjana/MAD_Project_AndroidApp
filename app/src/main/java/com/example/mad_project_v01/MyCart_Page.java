package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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

    String cus_contact,cus_name;

    public int No_Of_Pro = 0, Sub_Total = 0;

    TextView txt_subtotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart__page);

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_name = getIntent().getExtras().getString("name");

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
                    txt_itemcount.setText("Rs.0.00");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //MyCart LB Price
        final TextView lb_cartprice = (TextView)findViewById(R.id.lb_cartprice);
        //MyCart Total Count
        txt_subtotal = findViewById(R.id.txt_subtotal);
        CalTotal obj = new CalTotal(Integer.parseInt(txt_subtotal.getText().toString()));
        long Sub_Tot = obj.getTotal();
        //MyCart Call Total
        final TextView txt_total = (TextView)findViewById(R.id.txt_total);
        //MyCart Delivery Chargers
        final TextView txt_deliverycharges = (TextView)findViewById(R.id.txt_deliverycharges);

        int value = (int) Sub_Tot + Integer.parseInt(txt_deliverycharges.getText().toString());
        txt_total.setText(String.valueOf(value));
        lb_cartprice.setText(String.valueOf(value));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<SaveMyCartHelper,ProductListViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<SaveMyCartHelper,ProductListViewHolder>
                (SaveMyCartHelper.class,R.layout.productlist_row, ProductListViewHolder.class,DatabaseRef){

            @Override
            protected void populateViewHolder(ProductListViewHolder productListViewHolder, SaveMyCartHelper model,int position) {

                productListViewHolder.setP_Name(model.getP_name());
                productListViewHolder.setP_Price(model.getP_price());
                productListViewHolder.setP_Qty(model.getP_qty());
                productListViewHolder.setP_Total(model.getP_price(),model.getP_qty());


                txt_subtotal = findViewById(R.id.txt_subtotal);
                MyCartTotProRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart").child(cus_contact);

                MyCartTotProRef.child(model.getP_code()).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(snapshot.exists()){
                            String qty = snapshot.child("p_qty").getValue().toString();
                            String price = snapshot.child("p_price").getValue().toString();
                            Sub_Total = Sub_Total + (Integer.parseInt(qty)*Integer.parseInt(price));
                            txt_subtotal.setText(String.valueOf(Sub_Total));
                        }else{
                            txt_subtotal.setText("Rs.0");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

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
        intent.putExtra("name",cus_name);
        intent.putExtra("mobile",cus_contact);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_from_right,R.anim.slide_to_left);
        finish();
    }
}
