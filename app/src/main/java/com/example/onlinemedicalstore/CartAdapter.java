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

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private ArrayList<CartMedicineModel> listData;
    private CartScreen context;
    private CartAdapter adapter;
    private CartModel cartModel;



    // RecyclerView recyclerView;
    public CartAdapter(ArrayList<CartMedicineModel> listData, CartScreen cartList, CartModel cartModel) {
        this.listData = listData;
        this.context = cartList;
        this.adapter = this;
        this.cartModel = cartModel;//This is an important line, you need this line to keep track the adapter variable

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
        final CartMedicineModel ListData = listData.get(position);
        holder.name.setText(ListData.getName());
        holder.quantity.setText(ListData.getQuantity());
        holder.unit.setText(ListData.getUnit());
        holder.itemQuantity.setNumber(ListData.getItemQuantity());

        DatabaseReference cartReference = FirebaseDatabase.getInstance().getReference("Carts").child(FirebaseAuth.getInstance().getUid());

        holder.price.setText("Rs " + String.valueOf(ListData.getPrice()));
        Picasso.get()
                .load(ListData.getImage())
                .into(holder.imageView);

        holder.deleteImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        DatabaseReference finalTotalPrice = dataSnapshot.getRef();
                        int newCartPrice = cartModel.getCartPrice() - ListData.getPrice();
                        finalTotalPrice.child("cartPrice").setValue(newCartPrice).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                cartReference.child("Medicines").child(ListData.getMedicineId()).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        listData.remove(ListData);
                                        adapter.notifyDataSetChanged();
                                        /*   if (listData.size() == 1){

                                         *//* ((Activity)context).;*//*                        }*/
                                        // listData.remove(ListData);
                                    }
                                });

                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }
        });

        holder.itemQuantity.setOnClickListener(new ElegantNumberButton.OnClickListener() {
            @Override
            public void onClick(View view) {
                int totalPrice = Integer.parseInt(holder.itemQuantity.getNumber()) * Integer.parseInt(ListData.getOriginalPrice());

                setNewPriceToFirebase(totalPrice);
            }

            private void setNewPriceToFirebase(int totalPrice) {

                cartReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        DatabaseReference numMesasReference = dataSnapshot.child("Medicines").child(ListData.getMedicineId()).getRef();
                        numMesasReference.child("itemQuantity").setValue(holder.itemQuantity.getNumber()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                DatabaseReference finalTotalPrice = dataSnapshot.getRef();
                                int newCartPrice = cartModel.getCartPrice() - ListData.getPrice();
                                int totalPrices = newCartPrice + totalPrice;
                                finalTotalPrice.child("cartPrice").setValue(totalPrices)
                               .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        listData.clear();
                                        numMesasReference.child("price").setValue(totalPrice);
                                    }
                                });
                            }
                        });
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
        });

        // holder.imageView.setImageResource(ListData.getImage());
/*
        holder.relativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, MedicineDetail.class);
                intent.putExtra("medicineData", ListData.getMedicineId());
                context.startActivity(intent);
                // Toast.makeText(view.getContext(),"click on item: "+myListData.getDescription(),Toast.LENGTH_LONG).show();
            }
        });
*/
    }


    @Override
    public int getItemCount() {
        return listData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView, deleteImage;
        public TextView name, price, quantity, unit;
        public RelativeLayout relativeLayout;
        public ElegantNumberButton itemQuantity;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageView = (ImageView) itemView.findViewById(R.id.cart_item_image);
            this.deleteImage = (ImageView) itemView.findViewById(R.id.cart_delete_btn);
            this.name = (TextView) itemView.findViewById(R.id.cart_item_title);
            this.price = (TextView) itemView.findViewById(R.id.cart_item_price);
            this.quantity = (TextView) itemView.findViewById(R.id.cart_item_qty);
            this.unit = (TextView) itemView.findViewById(R.id.cart_item_unit);
            this.itemQuantity = (ElegantNumberButton) itemView.findViewById(R.id.cart_elegant_btn);

            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.cart_item);
        }
    }
}
