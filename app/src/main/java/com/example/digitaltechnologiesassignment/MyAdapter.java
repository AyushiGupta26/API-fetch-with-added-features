package com.example.digitaltechnologiesassignment;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cake_list_item,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

        holder.cakeName.setText(listItem.getName());
        holder.cakePrice.setText(listItem.getPrice());
        holder.cakeWeight.setText(listItem.getWeight());

        if (!listItem.getImg().equals("")){
            String url = "http://kekizadmin.com/uploads/catrgories/" + listItem.getImg();
            Glide.with(context).
                    load(url).
                    into(holder.img);
        }

        holder.addToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database =  FirebaseDatabase.getInstance().getReference();
                database = database.child("cart");
                String key = database.push().getKey();
                listItem.setKey(key);
                database.child(key).setValue(listItem);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cakeName;
        public TextView cakeWeight;
        public TextView cakePrice;
        public ImageView img;
        public Button addToCartButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cakeName = itemView.findViewById(R.id.cakeName);
            cakeWeight = itemView.findViewById(R.id.cakeWeight);
            cakePrice = itemView.findViewById(R.id.cakePrice);
            img = itemView.findViewById(R.id.cakeImage);
            addToCartButton = itemView.findViewById(R.id.addToCart);

        }
    }
}
