package com.example.stockinproduct;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Blob;

public class DataBaseStockIn extends SQLiteOpenHelper {

    private Context context;
    private static final int version=1;
    private static final String databaseName="stockin.db";
    private static final String tableName="product";
    private static final String id="_id";
    private static final String name="name";
    private static final String description="description";
    private static final String price="price";
    private static final String quantity="quantity";
    private static final String category="category";

    private static final String image="image";





    public DataBaseStockIn(@Nullable Context context) {
        super(context, databaseName, null, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query="create table "+tableName+
                "("+id+" INTEGER primary key AUTOINCREMENT, "+
                image+" BLOB,"+
                name+" TEXT, "+
                description+" TEXT, "+
                category+" TEXT, "+
                price+" INTEGER, "+
                quantity+" INTEGER );";
        db.execSQL(query);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP table if exists "+tableName);
        onCreate(db);


    }

    void addProduct(String imagelocation,String nameInput,String descriptionInpout,String categoryInput,int priceInput,int quantityInput){
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            Log.d("Tag",imagelocation);
            FileInputStream fs=new FileInputStream(imagelocation);
            byte[] imgbyte=new byte[fs.available()];
            fs.read(imgbyte);
            ContentValues cv = new ContentValues();
            cv.put(image,imgbyte);
            cv.put(name, nameInput);
            cv.put(description, descriptionInpout);
            cv.put(category, categoryInput);
            cv.put(price, priceInput);
            cv.put(quantity, quantityInput);
            long result = db.insert(tableName, null, cv);
            fs.close();
            if (result == -1) {
                // maneha failed
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "Added Successfully", Toast.LENGTH_SHORT).show();
            }
        }
        catch(Exception ex){
            ex.printStackTrace();
            Log.e("AddProduct", "Exception: " + ex.getMessage());
            Toast.makeText(context, "Exception: " + ex.getMessage(), Toast.LENGTH_SHORT).show();
        }


    }
    Cursor readAllData(){
        String query="SELECT * FROM "+tableName;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor =null;
        if(db!=null){
            cursor=db.rawQuery(query,null);
        }
        return cursor;
    }

     Cursor readData(String namein) {
        String query = "SELECT * FROM " + tableName + " WHERE name like ?";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor=null ;
        if (db != null) {
            cursor = db.rawQuery(query, new String[]{ "%" + namein + "%" });
        }
        return cursor;
    }

    void deleteProduct(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete(tableName, "_id=?", new String[]{id});
        if (result == -1) {
            Toast.makeText(context, "Failed to Delete", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "Successfully Deleted", Toast.LENGTH_SHORT).show();
        }
    }
}
