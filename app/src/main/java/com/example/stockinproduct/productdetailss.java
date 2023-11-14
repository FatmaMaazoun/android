package com.example.stockinproduct;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class productdetailss extends AppCompatActivity {
    DataBaseStockIn dataBaseStockIn;

    TextView name1,decription1,category,price,quantity;
    ImageView image1,delete1;

    ImageButton button;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetailss);

        name1=findViewById(R.id.name);
        decription1=findViewById(R.id.description);
        price=findViewById(R.id.price);
        category=findViewById(R.id.category);
        image1=findViewById(R.id.imageproduct);
        quantity=findViewById(R.id.quantity);
        button=findViewById(R.id.imageButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                confirmDialog();

            }
        });



        // Retrieve the passed ID
        Intent intent = getIntent();
        String itemId = intent.getStringExtra("name");
        if(itemId!=null)
        { Log.e("affichage",intent.getStringExtra("name"));
            dispalyData(itemId);}


    }
    void dispalyData(String name){
        DataBaseStockIn mydb=new DataBaseStockIn(productdetailss.this);
        Cursor cursor=mydb.readData(name);
        if (cursor != null && cursor.moveToFirst()) {
            id=cursor.getString(0);
            name1.setText(cursor.getString(2));
            decription1.setText(cursor.getString(3));
            category.setText(cursor.getString(4));
            price.setText(String.valueOf(cursor.getInt(5))+" DT");
            byte[] imageByteArray = cursor.getBlob(1);
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageByteArray, 0, imageByteArray.length);
            image1.setImageBitmap(bitmap);
           int quantitynumber =cursor.getInt(6);
           if(quantitynumber>0){
               quantity.setText("Available in stock");
               quantity.setTextColor(getResources().getColor(R.color.availableColor)); // Set the color for available stock

           }
           else{
               quantity.setText("out of stock");
               quantity.setTextColor(getResources().getColor(R.color.outOfStockColor)); // Set the color for out of stock

           }


        }

// Make sure to close the cursor when you're done with it
        if (cursor != null) {
            cursor.close();
        }




    }
    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete "+ name1.getText().toString()+" ?.");
        builder.setMessage("Are you sure you want to delete it ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                DataBaseStockIn mydb=new DataBaseStockIn(productdetailss.this);
                mydb.deleteProduct(id);
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}