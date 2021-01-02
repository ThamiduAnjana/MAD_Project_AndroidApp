package com.example.mad_project_v01;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class Home_Page extends AppCompatActivity {

    private RecyclerView card_viewlist;
    private DatabaseReference DatabaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home__page);

        DatabaseRef = FirebaseDatabase.getInstance().getReference().child("OnlineKeels").child("products");
        DatabaseRef.keepSynced(true);

        card_viewlist = (RecyclerView) findViewById(R.id.card_view);
        card_viewlist.setHasFixedSize(true);
        card_viewlist.setLayoutManager(new LinearLayoutManager(this));

    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<ProductCard,ProductCardViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ProductCard,ProductCardViewHolder>
                (ProductCard.class,R.layout.productcard,ProductCardViewHolder.class,DatabaseRef) {

            @Override
            protected void populateViewHolder(ProductCardViewHolder viewHolder,ProductCard model,int position) {

                viewHolder.setP_Name(model.getP_name());
                viewHolder.setP_Des(model.getP_des());
                viewHolder.setP_Price(model.getP_price());
                viewHolder.setP_Image(getApplicationContext(),model.getP_image());

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
            pro_Price.setText("Rs."+priceString+".00");
        }
        public void setP_Image(Context ctx,String p_image){
            ImageView pro_image = (ImageView)mView.findViewById(R.id.im_image);
            Picasso.with(ctx).load(p_image).into(pro_image);
        }
    }

}