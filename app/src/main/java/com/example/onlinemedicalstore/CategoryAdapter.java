package com.example.onlinemedicalstore;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder>{
    private List<CategoriesModel> categoriesListData;
    private Context context;
    private String role;

    // RecyclerView recyclerView;
    public CategoryAdapter(List<CategoriesModel> listdata, Context userDashboard, String role) {
        this.categoriesListData = listdata;
        this.context = userDashboard;
        this.role = role;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.category_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final CategoriesModel myListData = categoriesListData.get(position);
        holder.textView.setText(myListData.getName());
        Picasso.get()
                .load(myListData.getImage())
                .into(holder.imageView);

      //  holder.imageView.setImageResource(myListData.getImage());
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (role == "user"){
                    Intent intent = new Intent(context, MedicinesList.class);
                    intent.putExtra("categoryId",myListData.getId());
                    context.startActivity(intent);
                }
              else{
                    Intent intent = new Intent(context, AddNewMedicine.class);
                    intent.putExtra("categoryId",myListData.getId());
                    context.startActivity(intent);

                }
               // Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public int getItemCount() {
        return categoriesListData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView textView;
        public CardView relativeLayout;
        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.cat_img);
            this.textView = (TextView) itemView.findViewById(R.id.cat_name);
           relativeLayout = (CardView)itemView.findViewById(R.id.cat_view);
        }
    }
}

