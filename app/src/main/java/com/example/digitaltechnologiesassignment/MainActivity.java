package com.example.digitaltechnologiesassignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private Button goToCartButton;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        goToCartButton = findViewById(R.id.goToCartButton);
        goToCartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),AddToCartActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.cakeListRV);
        listItems = new ArrayList<>();
        ItemViewModel model = new ViewModelProvider(this).get(ItemViewModel.class);

        model.getString().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                try {
                    JSONObject object = new JSONObject(s);
                    JSONArray array = object.getJSONArray("data");
                    for (int i = 0; i < array.length(); i++) {
                        JSONObject o = array.getJSONObject(i);

                        String cakeName = o.getString("cake_name");
                        JSONArray arr = o.getJSONArray("w_l_p");
                        for (int j=0; j< arr.length();j++){
                            JSONObject wlp = arr.getJSONObject(j);
                            String img = wlp.getString("pictures");
                            int start=img.indexOf(":")+2, last=img.indexOf(".jpg")+4;
                            if(start!=-1 && last!=-1 && start<last-1) {
                                img = img.substring(start,last);
                                ListItem item = new ListItem(
                                        cakeName,
                                        wlp.getString("price"),
                                        wlp.getString("weight"),
                                        img
                                );
                                listItems.add(item);
                            }
                        }
                    }
                    adapter = new MyAdapter(listItems,MainActivity.this);
                    recyclerView.setAdapter(adapter);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

        });
    }
}
