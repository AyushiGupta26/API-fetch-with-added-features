package com.example.digitaltechnologiesassignment;

import android.content.Context;
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

import java.util.Collections;
import java.util.List;

public class SelectedListAdapter extends RecyclerView.Adapter<SelectedListAdapter.ViewHolder> implements ItemMoveHelperClass.ItemTouchHelperAdapter {

    private List<ListItem> listItems;
    private Context context;

    public SelectedListAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectedListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.selected_cake_list_item,parent,false);
        return new SelectedListAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectedListAdapter.ViewHolder holder, final int position) {
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

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference database = FirebaseDatabase.getInstance().getReference().child("selected");
                database.child(listItems.get(position).getKey()).removeValue();
            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(listItems, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(listItems, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView cakeName;
        public TextView cakeWeight;
        public TextView cakePrice;
        public ImageView img;
        public Button deleteButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            cakeName = itemView.findViewById(R.id.cakeName);
            cakeWeight = itemView.findViewById(R.id.cakeWeight);
            cakePrice = itemView.findViewById(R.id.cakePrice);
            img = itemView.findViewById(R.id.cakeImage);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
