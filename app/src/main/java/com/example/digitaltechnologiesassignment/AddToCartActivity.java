package com.example.digitaltechnologiesassignment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddToCartActivity extends AppCompatActivity implements CartListAdapter.ClickListener{

    private RecyclerView cartListRV, selectedListRV;
    private RecyclerView.Adapter adapter1, adapter2;

    private List<ListItem> cartList, selectedList;
    DatabaseReference reference =  FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_cart);

        cartListRV = findViewById(R.id.cartListRV);
        selectedListRV = findViewById(R.id.selectedCakeListRV);
        cartList = new ArrayList<>();
        selectedList = new ArrayList<>();

        adapter1 = new CartListAdapter(cartList,AddToCartActivity.this,AddToCartActivity.this);
        cartListRV.setAdapter(adapter1);
        ItemTouchHelper.Callback callback = new ItemMoveHelperClass1((ItemMoveHelperClass1.ItemTouchHelperAdapter) adapter1);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(cartListRV);

        reference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference database = reference.child("cart");
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ItemCountInCartList",String.valueOf(dataSnapshot.getChildrenCount()));
                cartList.clear();
                for (DataSnapshot cartSnapshot: dataSnapshot.getChildren()) {
                    ListItem item = cartSnapshot.getValue(ListItem.class);
                    cartList.add(item);
                }
                adapter1.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        adapter2 = new SelectedListAdapter(selectedList,AddToCartActivity.this);
        selectedListRV.setAdapter(adapter2);
        callback = new ItemMoveHelperClass((ItemMoveHelperClass.ItemTouchHelperAdapter) adapter2);
        touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(selectedListRV);

        DatabaseReference database1 = reference.child("selected");
        database1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d("ItemCountInSelectedList",String.valueOf(dataSnapshot.getChildrenCount()));
                selectedList.clear();
                for (DataSnapshot selectedSnapshot: dataSnapshot.getChildren()) {
                    ListItem item = selectedSnapshot.getValue(ListItem.class);
                    selectedList.add(item);
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onDeleteClick(View view, int position) {
        DatabaseReference database = reference.child("cart");
        database.child(cartList.get(position).getKey()).removeValue();
    }

    @Override
    public void onMoveClick(View view, int position) {
        DatabaseReference database1 = reference.child("selected");
        String key = database1.push().getKey();

        String previousKey = cartList.get(position).getKey();
        ListItem item = cartList.get(position);
        item.setKey(key);
        database1.child(key).setValue(item);

        DatabaseReference database = reference.child("cart");
        database.child(previousKey).removeValue();

    }

    @Override
    public void onMoveTouch(int position) {
        DatabaseReference database1 = reference.child("selected");
        String key = database1.push().getKey();

        String previousKey = cartList.get(position).getKey();
        ListItem item = cartList.get(position);
        item.setKey(key);
        database1.child(key).setValue(item);

        DatabaseReference database = reference.child("cart");
        database.child(previousKey).removeValue();
    }
}
