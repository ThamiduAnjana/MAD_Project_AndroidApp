package com.example.mad_project_v01;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Home_Page extends AppCompatActivity {

    private RecyclerView card_viewlist;
    private DatabaseReference DatabaseRef,MyCartcountProRef,MyCartTotProRef;

    String cus_title,cus_name,cus_contact;

    int cal = 0;

    TextView lb_cartprice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);

        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("products");
        DatabaseRef.keepSynced(true);

        card_viewlist = (RecyclerView) findViewById(R.id.card_view);
        card_viewlist.setHasFixedSize(true);
        card_viewlist.setLayoutManager(new LinearLayoutManager(this));

        cus_contact = getIntent().getExtras().getString("mobile");
        cus_title = getIntent().getExtras().getString("title");

        cus_name = getIntent().getExtras().getString("name");
        TextView uName = (TextView)findViewById(R.id.lb_cusname);
        uName.setText("Hi!,"+cus_title+cus_name);

        Button menu = (Button)findViewById(R.id.btn_menu);

        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Page.this,MainMenu_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_to_top);
                finish();

            }
        });

        Button btn_logout = (Button)findViewById(R.id.btn_logout);
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        lb_cartprice = (TextView)findViewById(R.id.lb_cartprice);
        lb_cartprice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Page.this,MyCart_Page.class);
                intent.putExtra("title",cus_title);
                intent.putExtra("name",cus_name);
                intent.putExtra("mobile",cus_contact);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_to_top);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //Load MyCart amount

        MyCartcountProRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart");
        MyCartcountProRef.child(cus_contact).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists()){

                    int count = (int) snapshot.getChildrenCount();
                    String[] data1 = new String[count];
                    int i = 0;
                    for(DataSnapshot d : snapshot.getChildren()){
                        data1[i] = d.getKey();
                        Log.d("tag 1", data1[i]);

                        MyCartTotProRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart").child(cus_contact);
                        MyCartTotProRef.child(data1[i]).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()){
                                    lb_cartprice.setText("Rs.0");
                                    int item_price = Integer.parseInt(snapshot.child("p_price").getValue().toString());
                                    int item_qty = Integer.parseInt(snapshot.child("p_qty").getValue().toString());
                                    int calqty = item_price*item_qty;
                                    cal = cal + calqty;
                                    lb_cartprice.setText("Rs."+cal);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        i++;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });




        FirebaseRecyclerAdapter<ProductCardHelper,ProductCardViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ProductCardHelper,ProductCardViewHolder>
                (ProductCardHelper.class,R.layout.productcard,ProductCardViewHolder.class,DatabaseRef) {

            @Override
            protected void populateViewHolder(final ProductCardViewHolder viewHolder, final ProductCardHelper model, int position) {

                viewHolder.setP_Code(model.getP_code());
                viewHolder.setP_Name(model.getP_name());
                viewHolder.setP_Des(model.getP_des());
                viewHolder.setP_Price(model.getP_price());
                viewHolder.setP_Image(getApplicationContext(),model.getP_image());

                viewHolder.itemView.findViewById(R.id.btn_add).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try{
                            DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("mycart");

                            final ElegantNumberButton txt_qt = (ElegantNumberButton) viewHolder.itemView.findViewById(R.id.txt_qty);
                            final int qty = Integer.parseInt(txt_qt.getNumber());

                            String p_name = model.getP_name();
                            String p_code = model.getP_code();
                            Long p_price = model.getP_price();
                            int p_qty = qty;

                            mycart obj = new mycart(p_name,p_code,p_price,p_qty);

                            DatabaseRef.child(cus_contact).child(p_code).setValue(obj);

                            DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("placeorderproducts");

                            PlaceOrderProducts obj1 = new PlaceOrderProducts(p_name,p_code,p_price,p_qty);
                            DatabaseRef.child(cus_contact).child(p_code).setValue(obj1);

                            Toast.makeText(Home_Page.this,"Add Successful..!",Toast.LENGTH_LONG).show();
                            txt_qt.setNumber("1");

                            Intent intent = new Intent(Home_Page.this,Home_Page.class);
                            intent.putExtra("title",cus_title);
                            intent.putExtra("name",cus_name);
                            intent.putExtra("mobile",cus_contact);
                            startActivity(intent);
                        }catch (Exception e){
                            Toast.makeText(Home_Page.this,"Error.!",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

        };
        card_viewlist.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ProductCardViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public ProductCardViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }
        public void setP_Code(String p_code) {
            TextView pro_code = (TextView) mView.findViewById(R.id.lb_pcode);
            pro_code.setText("ID: "+p_code);
        }
        public void setP_Name(String p_name){
            TextView pro_title = (TextView)mView.findViewById(R.id.lb_title);
            pro_title.setText(p_name);
        }
        public void setP_Des(String p_des){
            TextView pro_des = (TextView)mView.findViewById(R.id.lb_deis);
            pro_des.setText(p_des);
        }
        public void setP_Price(Long p_price){
            TextView pro_Price = (TextView)mView.findViewById(R.id.lb_price);
            String priceString = String.valueOf(p_price);
            pro_Price.setText("Rs."+priceString);
        }
        public void setP_Image(Context ctx,String p_image){
            ImageView pro_image = (ImageView)mView.findViewById(R.id.im_image);
            Picasso.with(ctx).load(p_image).placeholder(R.drawable.progress_animation).error(R.drawable.error_images).into(pro_image);
        }
    }

    @Override
    public void onBackPressed() {
        Toast.makeText(Home_Page.this,"Log out",Toast.LENGTH_LONG).show();
        Intent intent = new Intent(Home_Page.this,Signin_Page.class);
        startActivity(intent);
        overridePendingTransition(R.anim.static_animation,R.anim.zoom_out);
        finish();
    }
}