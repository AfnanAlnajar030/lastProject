package com.example.buyfresh;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class RecyclerView_Config {
    private Context mContext;
    private FarmerAdapter mFarmersAdapter;
    private List<Farmer> mFarmerList;
    public String type;
    private boolean darkMode;



    public void setConfig (RecyclerView recyclerView, Context context, List<Farmer> mFarmerList, List<String> keys, boolean darkMode){

        mContext = context;
        mFarmersAdapter = new FarmerAdapter(mFarmerList, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mFarmersAdapter);
        this.mFarmerList = mFarmerList;
        this.darkMode = darkMode;
    }


    class FarmerItemView extends RecyclerView.ViewHolder {
        private TextView mName;
        private ImageView view;
        private String key;

        public FarmerItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).
                    inflate(R.layout.farm_list, parent, false));

            mName = itemView.findViewById(R.id.farm_name);
            view = itemView.findViewById(R.id.imageView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent car = new Intent(mContext, PersonalFarmer.class);
                    car.putExtra("which_farmer", mName.getText());
                    car.putExtra("type", type);
                    mContext.startActivity(car);
                }
            });


        }

        public void bind(Farmer farmer, String key){
            mName.setText(farmer.getName());
            this.key = key;
            if (darkMode) {
                view.setImageResource(R.drawable.dark_square);
                mName.setTextColor(Color.WHITE);
            } else {
                view.setImageResource(R.drawable.white_square);
                mName.setTextColor(Color.BLACK);
            }

        }
    }

    class FarmerAdapter extends RecyclerView.Adapter<FarmerItemView>{
        private List<Farmer> mFarmerList;
        private List<String> mKeys;

        public FarmerAdapter(List<Farmer> mFarmerList, List<String> mKeys) {
            this.mFarmerList = mFarmerList;
            this.mKeys = mKeys;
        }

        @NonNull
        @Override
        public FarmerItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new FarmerItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull FarmerItemView holder, final int position) {
            holder.bind(mFarmerList.get(position), mKeys.get(position));



        }

        @Override
        public int getItemCount() {
            return mFarmerList.size();
        }
    }



}
