package com.example.onlinemedicalstore;

import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.ViewHolder> {

    private ArrayList<MedicinesModel> listdata;
    private MedicinesList context;


    // RecyclerView recyclerView;
    public MedicineAdapter(ArrayList<MedicinesModel> listdata, MedicinesList medicinesList) {
        this.listdata = listdata;
        this.context = medicinesList;
    }

    @Override
    public MedicineAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.medicine_layout, parent, false);
        MedicineAdapter.ViewHolder viewHolder = new MedicineAdapter.ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MedicineAdapter.ViewHolder holder, int position) {
        final MedicinesModel ListData = listdata.get(position);
        holder.name.setText(ListData.getName());
        holder.price.setText(ListData.getPrice());
        Picasso.get()
                .load(ListData.getImage())
                .into(holder.imageView);
        // holder.imageView.setImageResource(ListData.getImage());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicineDetail.class);
                intent.putExtra("medicineData", ListData);
                context.startActivity(intent);
                // Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView name, price;
        public RelativeLayout relativeLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.item_image);
            this.name = (TextView) itemView.findViewById(R.id.item_title);
            this.price = (TextView) itemView.findViewById(R.id.item_newprice);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.item);
        }
    }
}
