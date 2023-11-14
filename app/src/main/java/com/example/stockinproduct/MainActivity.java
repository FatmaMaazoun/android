package com.example.stockinproduct;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Currency;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ImageView addproduct;

    DataBaseStockIn dataBaseStockIn;
    ArrayList<String> names, descriptions, categories;
    ArrayList<byte[]> images;
    ArrayList<Integer> prices,quanity;
     CustomAdapter customAdapter;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyleview);
        addproduct=findViewById(R.id.addproduct);
        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,AddProduct.class);
                startActivity(intent);
            }
        });
    dataBaseStockIn=new DataBaseStockIn(MainActivity.this);
    names=new ArrayList<>();
    descriptions=new ArrayList<>();
    categories=new ArrayList<>();
    quanity=new ArrayList<>();
    prices=new ArrayList<>();

        displayAllData();
        customAdapter=new CustomAdapter(MainActivity.this,names,descriptions,prices);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));



    }

   void displayAllData(){
        Cursor cursor=dataBaseStockIn.readAllData();
        if(cursor.getCount()==0 ){
            Toast.makeText(this, "No Data.", Toast.LENGTH_SHORT).show();
        }else{
            while(cursor.moveToNext()){

               names.add(cursor.getString(2));
                descriptions.add(cursor.getString(3));
                categories.add(cursor.getString(4));
                prices.add(cursor.getInt(5));
                quanity.add(cursor.getInt(6));




            }
        }
    }


}