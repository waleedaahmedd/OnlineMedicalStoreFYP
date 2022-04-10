package com.example.onlinemedicalstore;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<CartModel> listData;
    private CartScreen context;


    // RecyclerView recyclerView;
    public CartAdapter(ArrayList<CartModel> listData, CartScreen cartList) {
        this.listData = listData;
        this.context = cartList;
    }

    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.cart_medicine_layout, parent, false);
        CartAdapter.ViewHolder viewHolder = new CartAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CartAdapter.ViewHolder holder, int position) {
        final CartModel ListData = listData.get(position);
       // holder.name.setText(ListData.getName());
        holder.price.setText(String.valueOf(ListData.getPrice()));
       /* Picasso.get()
                .load(ListData.getImage())
                .into(holder.imageView);*/
        // holder.imageView.setImageResource(ListData.getImage());
      /*  holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicineDetail.class);
                intent.putExtra("medicineData", ListData);
                context.startActivity(intent);
                // Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });*/
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, price;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.cart_item_image);
            this.name = (TextView) itemView.findViewById(R.id.cart_item_title);
            this.price = (TextView) itemView.findViewById(R.id.cart_item_price);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.cart_item);
        }
    }
}
