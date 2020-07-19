package com.example.buyfresh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter{

    ArrayList<Products> mProducts;
    Context context;
    boolean darkMode;

    // Constructor
    public Adapter(ArrayList<Products> mProducts, Context context, boolean darkMode) {
        this.mProducts = mProducts;
        this.context = context;
        this.darkMode = darkMode;
    }


    // Required methods
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.product_list_item, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        // This method gives data to each item in it based on the 'position' which is the index..?
        ((ViewHolder) holder).weight.setText(mProducts.get(position).getWeight());
        ((ViewHolder) holder).name.setText(mProducts.get(position).getName());
        ((ViewHolder) holder).price.setText(mProducts.get(position).getPrice());
        if (darkMode) {
            ((ViewHolder) holder).image.setImageResource(R.drawable.dark_square);
            ((ViewHolder) holder).weight.setTextColor(Color.WHITE);
            ((ViewHolder) holder).name.setTextColor(Color.WHITE);
            ((ViewHolder) holder).price.setTextColor(Color.WHITE);
        } else {
            ((ViewHolder) holder).image.setImageResource(R.drawable.white_square);
            ((ViewHolder) holder).weight.setTextColor(Color.BLACK);
            ((ViewHolder) holder).name.setTextColor(Color.BLACK);
            ((ViewHolder) holder).price.setTextColor(Color.BLACK);
        }


    }

    @Override
    public int getItemCount() {
        return mProducts.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView price;
        public TextView name;
        public TextView weight;
        public View view;
        public ImageView image;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            name = itemView.findViewById(R.id.product);
            weight = itemView.findViewById(R.id.weight);
            price = itemView.findViewById(R.id.price);
            image = itemView.findViewById(R.id.images);
        }
    }
}
