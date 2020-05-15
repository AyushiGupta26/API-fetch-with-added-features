package com.example.digitaltechnologiesassignment;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder> implements ItemMoveHelperClass1.ItemTouchHelperAdapter{

    private List<ListItem> listItems;
    private Context context;
    private ClickListener listener;

    public CartListAdapter(List<ListItem> listItems, Context context, ClickListener listener) {
        this.listItems = listItems;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_list_item,parent,false);
        return new CartListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CartListAdapter.ViewHolder holder, final int position) {
        ListItem listItem = listItems.get(position);

        holder.cakeName.setText(listItem.getName());
        holder.cakePrice.setText(listItem.getPrice());
        holder.cakeWeight.setText(listItem.getWeight());

        if (!listItem.getImg().equals("")){
            String url = "http://kekizadmin.com/uploads/catrgories/" + listItem.getImg();
            Glide.with(context).
                    load(url).
                    into(holder.img);
        }
        holder.moveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMoveClick(v,position);
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onDeleteClick(v,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public void onItemSwipe(int position) {
        listener.onMoveTouch(position);
    }

    public interface ClickListener {
        void onDeleteClick(View view, int position);
        void onMoveClick(View view, int position);
        void onMoveTouch(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cakeName;
        public TextView cakeWeight;
        public TextView cakePrice;
        public ImageView img;
        public Button moveButton, deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cakeName = itemView.findViewById(R.id.cakeName);
            cakeWeight = itemView.findViewById(R.id.cakeWeight);
            cakePrice = itemView.findViewById(R.id.cakePrice);
            img = itemView.findViewById(R.id.cakeImage);
            moveButton = itemView.findViewById(R.id.moveButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);

        }
    }
}
