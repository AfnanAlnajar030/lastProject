package com.example.buyfresh;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mReferenceFarm;
    private List<Farmer> farmers = new ArrayList<>();



    public interface DataStatus {
        void DataIsLoaded (List<Farmer> books, List<String> keys);
        void DataIsInserted();
        void DataIsIsUpdated();
        void DataIsDeleted();
    }

    public FirebaseDatabaseHelper(String type) {
        mDatabase = FirebaseDatabase.getInstance();


        mReferenceFarm = mDatabase.getReference(type);


    }

    public void readBooks(final DataStatus dataStatus){
        mReferenceFarm.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                farmers.clear();
                List<String> keys = new ArrayList<>();
                for (DataSnapshot keyNode : snapshot.getChildren()) {
                    keys.add(keyNode.getKey());
                    Farmer farmer = keyNode.getValue(Farmer.class);
                    farmers.add(farmer);
                }
                dataStatus.DataIsLoaded(farmers, keys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
