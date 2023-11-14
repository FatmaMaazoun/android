package com.example.stockinproduct;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLData;

public class AddProduct extends AppCompatActivity {

    private TextView quantitynumber,selectImageProdcut;
    private SeekBar seekBar;
    private ImageView productimage1;
    private EditText name,description,category,price;

    private Button addproduct;

    public static final int PICK_IMAGE_ReQUEST=90;

    private Uri imagePath;
    private Bitmap imageToStore;

    private String imagelocation;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        name=findViewById(R.id.name);
        description=findViewById(R.id.description);
        category=findViewById(R.id.category);
        price=findViewById(R.id.price);
        addproduct=findViewById(R.id.addp);
        quantitynumber =(TextView) findViewById(R.id.quatitenumber);
        productimage1=  findViewById(R.id.productimagev);
        //select image
        selectImageProdcut=findViewById(R.id.selectimage);
        selectImageProdcut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                chooseImage();
            }
        });

        //Quantity
        seekBar=findViewById(R.id.quantity);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
                quantitynumber.setText(""+progress);



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        addproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                DataBaseStockIn mydb=new DataBaseStockIn(AddProduct.this);
                mydb.addProduct(
                        imagelocation,
                        name.getText().toString(),
                        description.getText().toString(),
                        category.getText().toString(),
                        Integer.valueOf(price.getText().toString()),
                        Integer.valueOf(quantitynumber.getText().toString()));

            }
        });

    }

    private void chooseImage(){
        try{
            Intent intent=new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,PICK_IMAGE_ReQUEST);

        }
        catch(Exception ex){
            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    @Override
    protected void onActivityResult(int requestCode , int resultCode , @Nullable Intent data) {
  try {
      super.onActivityResult(requestCode, resultCode, data);
      if (requestCode == PICK_IMAGE_ReQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
          imagePath = data.getData();
          imagelocation= getImagePath(imagePath);
          imageToStore = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
          productimage1 .setImageBitmap(imageToStore);
      }
  }
  catch(Exception ex){
      Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_SHORT).show();

  }
    }

    private String getImagePath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String imagePath = cursor.getString(column_index);
        cursor.close();
        return imagePath;
    }

}