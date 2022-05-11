package com.example.onlinemedicalstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private ArrayList<OrderModel> listData;
    private OrderScreen context;
    private OrderAdapter adapter;



    // RecyclerView recyclerView;
    public OrderAdapter(ArrayList<OrderModel> listData, OrderScreen orderList) {
        this.listData = listData;
        this.context = orderList;
        this.adapter = this; //This is an important line, you need this line to keep track the adapter variable

    }

    @Override
    public OrderAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.order_list_layout, parent, false);
        OrderAdapter.ViewHolder viewHolder = new OrderAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(OrderAdapter.ViewHolder holder, int position) {
        final OrderModel ListData = listData.get(position);
        holder.price.setText(ListData.getCartPrice());
      /*  holder.name.setText(ListData.getName());
        holder.quantity.setText(ListData.getQuantity());
        holder.unit.setText(ListData.getUnit());
        holder.itemQuantity.setNumber(ListData.getItemQuantity());*/


    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView price;

        public ViewHolder(View itemView) {
            super(itemView);
            this.price = (TextView) itemView.findViewById(R.id.status_total_price);
        }
    }
}
